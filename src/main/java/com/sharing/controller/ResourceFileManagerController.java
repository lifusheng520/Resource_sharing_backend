package com.sharing.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.MyPage;
import com.sharing.pojo.UserResource;
import com.sharing.service.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资源文件管理接口
 *
 * @author 李福生
 * @date 2022-3-16
 * @time 下午 06:15
 */

@RestController
@RequestMapping("/resource")
public class ResourceFileManagerController {

    @Value("${files.resource.upload.root.path}")
    private String uploadRootPath;

    @Value("${files.resource.host.root.url}")
    private String hostRootUrl;

    @Autowired
    private UserResourceService resourceService;

    /**
     * 资源文件上传业务接口
     * 文件磁盘绝对路径 = 上传根路径 + 文件的科目类别 + 文件的UUid
     *
     * @param file        上传的二进制文件流
     * @param id          上传用户的id
     * @param discipline  资源所属的科目
     * @param description 资源简介描述
     * @return 上传执行的状态json字符串
     */
    @PostMapping("/upload/{id}/{discipline}/{description}")
    public String uploadFile(@RequestBody MultipartFile file, @PathVariable int id, @PathVariable String discipline, @PathVariable String description) {
        if (discipline == null || "".equals(discipline) || id == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, "id,discipline");

        // 获取文件信息
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 磁盘文件夹 = 上传根路径 + 文件类别
        String folder = this.uploadRootPath + File.separator + discipline;
        File diskPath = new File(folder);
        if (!diskPath.exists())
            diskPath.mkdirs();

        // 文件唯一标识id
        String fileId = IdUtil.fastUUID();
        String fileName = fileId + StrUtil.DOT + type;
        // 文件磁盘绝对路径 = 上传根路径 + 文件的科目类别 + 文件的UUid
        File distFile = new File(folder + File.separator + fileName);
        try {
            file.transferTo(distFile);
        } catch (IOException e) {
//            e.printStackTrace();
            return ResultFormatUtil.format(ResponseCode.EXCEPTION_IO, originalFilename);
        }

        // 生成文件的md5
        String md5 = SecureUtil.md5(distFile);
        // 到数据库中查询文件是否存在
        List<UserResource> resourceByMd5 = this.resourceService.getUserResourceByMd5(md5);
        // 设置资源信息
        UserResource userResource = new UserResource();
        userResource.setUser_id(id);
        userResource.setOrigin_name(originalFilename);
        userResource.setType(type);
        userResource.setSize((long) Math.ceil(size / 1024.00));      //  size以字节B为单位，将其转换为KB
        userResource.setDiscipline(discipline);
        userResource.setUpload_time(new Date());
        userResource.setDownloads(0);
        userResource.setFavorite_number(0);
        userResource.setEnabled(1);
        userResource.setIsDeleted(0);
        userResource.setMd5(md5);
        userResource.setDescription(description);

        // 如果查询到了文件信息（不为null），说明本次上传的是重复文件，则数据中的disk_name = 查询的disk_name
        boolean delete;
        if (resourceByMd5 != null && resourceByMd5.size() > 0) {
            userResource.setDisk_name(resourceByMd5.get(0).getDisk_name());
            // 删除重复文件
            delete = distFile.delete();
        } else {
            userResource.setDisk_name(fileName);
        }

        //  将资源文件记录添加到数据库中
        int i = this.resourceService.addUserResource(userResource);
        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.RESOURCE_UPLOAD_SUCCESS, originalFilename);
        else
            return ResultFormatUtil.format(ResponseCode.RESOURCE_UPLOAD_FAIL, originalFilename);
    }

    /**
     * 通过字符串获取合法的页码
     *
     * @param number 页码字符串
     * @param type   需要转换的类型（page、size）
     * @return 如果type为page：字符串正常将该字符串转换为int返回，否则返回 1。如果type为size，返回正常的页面大小，默认返回10
     */
    int getValidPage(String number, String type) {
        Integer value;
        if ("page".equals(type)) {
            if (number == null || "".equals(number))
                return 1;
            value = Integer.valueOf(number);
            if (value < 1)
                return 1;
        } else {
            if (number == null || "".equals(number))
                return 10;
            value = Integer.valueOf(number);
            if (value < 1)
                return 10;
        }
        return value;
    }

    /**
     * 获取用户资源列表处理接口
     *
     * @param params 参数集
     * @return 返回一个用户资源对象列表json
     */
    @PostMapping("/getInfoList")
    public String getResourcePageInfo(@RequestBody Map<String, String> params) {
        // 获取页面参数
        String user_id = params.get("user_id");
        String pageSize = params.get("pageSize");
        String currentPage = params.get("currentPage");
        String totalPage = params.get("total");

        // 获取分页数据
        int page = this.getValidPage(currentPage, "page");
        int size = this.getValidPage(pageSize, "size");

        int total = Integer.valueOf(totalPage);

        if (total < 0)
            total = this.resourceService.getUserResourceNumber(Integer.valueOf(user_id));

        List<UserResource> pageList = this.resourceService.getUserResourceByPage(Integer.valueOf(user_id), (page - 1) * size, size);
        MyPage<UserResource> resourcePage = new MyPage<>();
        resourcePage.setTotal(total);
        resourcePage.setPageList(pageList);
        resourcePage.setPageSize(size);
        resourcePage.setCurrentPage(page);

        if (pageList == null || pageList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.NULL_RESOURCE, user_id);
        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_LIST_SUCCESS, resourcePage);
    }

    /**
     * 通过关键字搜索资源
     *
     * @param params 搜索参数集：user_id：用户id，key：搜索关键字
     * @return 返回搜索结果的json字符串
     */
    @PostMapping("/search")
    public String searchUserResource(@RequestBody Map<String, String> params) {
        // 获取页面参数
        String user_id = params.get("user_id");
        String pageSize = params.get("pageSize");
        String currentPage = params.get("currentPage");
        String totalPage = params.get("total");
        String key = params.get("searcher");

        // 获取分页数据
        int size = this.getValidPage(pageSize, "size");
        int page = this.getValidPage(currentPage, "page");
        int total = Integer.valueOf(totalPage);

        if (total < 0)
            total = this.resourceService.getUserResourceNumbersByCondition(Integer.valueOf(user_id), key);

        // 到数据库中查询资源
        List<UserResource> searchList = this.resourceService.getUserResourceBySearch(Integer.valueOf(user_id), key, (page - 1) * size, size);
        MyPage<UserResource> resourcePage = new MyPage<>();
        resourcePage.setTotal(total);
        resourcePage.setPageList(searchList);
        resourcePage.setPageSize(size);
        resourcePage.setCurrentPage(page);

        if (searchList == null || searchList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.RESOURCE_NOT_SEARCH, resourcePage);
        return ResultFormatUtil.format(ResponseCode.RESOURCE_SEARCH_SUCCESS, resourcePage);
    }

    /**
     * 将用户的资源记录删除标记置为1，
     *
     * @param resourceList
     * @return
     */
    @PostMapping("/delete")
    public String deleteUserResource(@RequestBody List<UserResource> resourceList) {
        if (resourceList == null || resourceList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, resourceList);

        // 假删除
        int i = this.resourceService.deleteUserResourceByList(resourceList);
        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.DELETE_RESOURCE_SUCCESS, resourceList);
        return ResultFormatUtil.format(ResponseCode.DELETE_RESOURCE_FAIL, resourceList);
    }

    /**
     * 获取资源信息，然后更新用户资源信息
     *
     * @param params 用户资源信息参数
     * @return 更新结果json字符串
     */
    @PostMapping("/updateInfo")
    public String updateUserResourceInfo(@RequestBody Map<String, String> params) {
        System.out.println(params);
        if (params == null || params.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, "resource info");
        String id = params.get("id");
        String origin_name = params.get("origin_name");
        String enabled = params.get("enabled");
        String discipline = params.get("discipline");
        String description = params.get("description");

        UserResource resource = new UserResource();
        resource.setId(Integer.valueOf(id));
        resource.setOrigin_name(origin_name);
        resource.setEnabled(Integer.valueOf(enabled));
        resource.setDiscipline(discipline);
        resource.setDescription(description);

        int i = this.resourceService.updateUserResourceInfo(resource);
        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.UPDATE_RESOURCE_SUCCESS, resource);
        return ResultFormatUtil.format(ResponseCode.UPDATE_RESOURCE_FAIL, resource);
    }

    /**
     * 用户资源下载接口
     *
     * @param diskName   资源磁盘名称
     * @param id         资源的id
     * @param discipline 资源类别
     * @param response   响应头
     */
    @GetMapping("/download/{diskName}/{id}/{discipline}")
    public void downloadUserResource(@PathVariable String diskName, @PathVariable int id, @PathVariable String discipline, HttpServletResponse response) throws IOException {
        if (id == 0 || diskName == null || "".equals(diskName)) return;
        else if (discipline == null || "".equals(discipline)) return;
        // 资源在服务器磁盘中的绝对路径  = 上传根路径 + 文件的科目类别 + 文件的UUid
        String fileName = this.uploadRootPath + File.separator + discipline + File.separator + diskName;
        File file = new File(fileName);
        // 如果文件不存在
        if (!file.exists()) return;

        // 增加资源的下载量
        this.resourceService.addUserResourceDownloads(id);

        // 获取文件的原文件名
        String originFileName = this.resourceService.getUserResourceOriginFileName(id);

        // 获取响应输出流设置响应头参数
        ServletOutputStream outputStream = response.getOutputStream();
        // 设置下载的文件名
        response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(originFileName, "UTF-8"));
        response.setContentType("application/octet-stream");

        // 写出资源字节流数据并关闭流
        outputStream.write(FileUtil.readBytes(file));
        outputStream.flush();
        outputStream.close();
    }


}
