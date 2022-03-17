package com.sharing.controller;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.crypto.SecureUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.UserResource;
import com.sharing.service.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * 资源文件管理接口
 *
 * @author 李福生
 * @date 2022-3-16
 * @time 下午 06:15
 */

@RestController
@RequestMapping("/resource")
public class ResourceFileController {

    @Value("${files.resource.upload.path}")
    private String uploadPath;

    @Value("${files.resource.host.url}")
    private String hostUrl;

    @Autowired
    private UserResourceService resourceService;

    @PostMapping("/upload/{id}/{discipline}")
    public String uploadFile(@RequestBody MultipartFile file, @PathVariable int id, @PathVariable String discipline) {
        if (discipline == null || "".equals(discipline) || id == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, "id,discipline");

        // 获取文件信息
        String originalFilename = file.getOriginalFilename();
        String type = FileUtil.extName(originalFilename);
        long size = file.getSize();

        // 先存放到磁盘
        File diskPath = new File(this.uploadPath);
        if (!diskPath.exists())
            diskPath.mkdirs();

        // 文件唯一标识id
        String fileId = IdUtil.fastUUID();
        String fileName = fileId + StrUtil.DOT + type;
        File distFile = new File(this.uploadPath + fileName);
        try {
            file.transferTo(distFile);
        } catch (IOException e) {
            e.printStackTrace();
            return ResultFormatUtil.format(ResponseCode.EXCEPTION_IO, originalFilename);
        }

        // 生成文件的md5
        String md5 = SecureUtil.md5(distFile);
        // 到数据库中查询文件是否存在
        List<UserResource> resourceByMd5 = this.resourceService.getUserResourceByMd5(md5);

        UserResource userResource = new UserResource();
        userResource.setUser_id(id);
        userResource.setOrigin_name(originalFilename);
        userResource.setType(type);
        userResource.setSize(size);
        userResource.setDiscipline(discipline);
        userResource.setUpload_time(new Date());
        userResource.setFavorite_number(0);
        userResource.setEnabled(1);
        userResource.setIsDeleted(0);
        userResource.setMd5(md5);

        // 如果查询到了文件信息（不为null），说明本次上传的是重复文件，则数据中的disk_name = 查询的disk_name
        if (resourceByMd5 != null && resourceByMd5.size() > 0) {
            userResource.setDisk_name(resourceByMd5.get(0).getDisk_name());
            // 删除重复文件
            distFile.delete();
        } else {
            userResource.setDisk_name(fileName);
        }

        //将资源文件记录添加到数据库中
        int i = this.resourceService.addUserResource(userResource);
        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.RESOURCE_UPLOAD_SUCCESS, originalFilename);
        else
            return ResultFormatUtil.format(ResponseCode.RESOURCE_UPLOAD_FAIL, originalFilename);
    }

}
