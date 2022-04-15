package com.sharing.controller;

import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.*;
import com.sharing.service.ResourceDetailService;
import com.sharing.service.SupportService;
import com.sharing.service.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资源服务controller接口
 *
 * @author 李福生
 * @date 2022-3-22
 * @time 下午 09:35
 */

@RestController
@RequestMapping("/resource/server")
public class ResourceFileServerController {

    @Autowired
    private ResourceDetailService resourceDetailService;

    @Autowired
    private UserResourceService userResourceService;

    @Autowired
    private SupportService supportService;

    @Value("${files.icon.host.url}")
    private String iconHostURL;

    @Value("${files.resource.upload.root.path}")
    private String fileRootPath;

    /**
     * 获取资源种类
     *
     * @return 返回资源种类list
     */
    @GetMapping("/discipline")
    public String getAllResourceDiscipline() {
        return ResultFormatUtil.format(ResponseCode.GET_ALL_RESOURCE_DISCIPLINE_SUCCESS, IndexData.discipline);
    }


    /**
     * 根据资源的id，获取资源的完整详细信息
     *
     * @param resourceId 资源id
     * @return 资源完整信息的json
     */
    @GetMapping("/detail/{resourceId}")
    public String getResourceDetails(@PathVariable Integer resourceId) {
        if (resourceId == null || resourceId == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, "id");

        UserAndResource userResourceDetail = this.resourceDetailService.getUserResourceDetail(resourceId);

        if (userResourceDetail.getResource().getIsDeleted() == 1) {
            return ResultFormatUtil.format(ResponseCode.RESOURCE_IS_NOT_EXIST, null);
        }

        // 查询点赞数量
        int id = userResourceDetail.getResource().getId();
        int number = this.supportService.countResourceSupportNumbers(id);
        userResourceDetail.getResource().setSupportNumber(number);

        // 设置头像URL
        String headIcon = userResourceDetail.getUserInfo().getHeadIcon();
        String iconURL;
        if (headIcon == null || "".equals(headIcon))
            iconURL = "";
        else
            iconURL = this.iconHostURL + headIcon;
        userResourceDetail.getUserInfo().setHeadIcon(iconURL);


        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_DETAIL_SUCCESS, userResourceDetail);
    }


    /**
     * 根据用户id，获取用户的资源信息
     *
     * @param params 请求参数
     * @return 返回用户的分页查询json数据
     */
    @PostMapping("/getList")
    public String getUserResourceById(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String userId = params.get("user_id");
        String pageSize = params.get("pageSize");
        String currentPage = params.get("currentPage");
        String totalPage = params.get("total");

        if (userId == null || "".equals(userId))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, null);

        // 获取分页参数
        int size = MyPage.getValidTransfer(pageSize, "size");
        int page = MyPage.getValidTransfer(currentPage, "page");

        // 查询用户资源信息
        int user_id = Integer.valueOf(userId);
        List<UserAndResource> userAndResources = this.resourceDetailService.getFocusUserResourceByUserId(user_id, (page - 1) * size, size);

        // 设置分页参数
        MyPage<UserAndResource> myPage = new MyPage<>();
        myPage.setPageSize(size);
        myPage.setCurrentPage(page);
        myPage.setPageList(userAndResources);

        int total = Integer.valueOf(totalPage);
        if (total < 0)
            total = this.resourceDetailService.countUserResourceNumbers(user_id);

        myPage.setTotal(total);

        return ResultFormatUtil.format(ResponseCode.GET_FOCUS_USER_RESOURCE_SUCCESS, myPage);
    }

    /**
     * 播放视频接口
     *
     * @param resourceId 资源的id
     * @return 返回删除结果
     */
    @GetMapping("/getVideo/{resourceId}")
    public void getResourceVideo(@PathVariable Integer resourceId, HttpServletResponse response) {
        if (resourceId == null || resourceId == 0)
            return;

        // 根据资源的id获取文件的磁盘信息
        UserResource resource = this.userResourceService.getUserResourceById(resourceId);
        String filePath = this.fileRootPath + File.separator + resource.getDiscipline() + File.separator + resource.getDisk_name();
        File file = new File(filePath);

        // 设置响应体内容
        OutputStream outputStream = null;
        FileInputStream inputStream = null;
        try {
            // 获得 response 的字节流
            outputStream = response.getOutputStream();
            // 判断文件是否存在
            if (!file.exists()) {
                //设置响应内容类型
                response.setContentType("application/json;charset=UTF-8");
                outputStream.write("未找到文件信息！".getBytes());
                if (outputStream != null)
                    outputStream.close();
                return;
            }

            // 判断文件格式是否支持播放
            if (!("mp4".equals(resource.getType()) || "webm".equals(resource.getType()) || "ogg".equals(resource.getType()))) {
                //设置响应内容类型
                response.setContentType("application/json;charset=UTF-8");
                outputStream.write("这个资源的格式不支持播放！".getBytes());
                if (outputStream != null)
                    outputStream.close();
                return;
            }

            //	获得视频文件的输入流
            inputStream = new FileInputStream(file);
            // 创建字节数组，数组大小为视频文件大小
            byte[] data = new byte[inputStream.available()];
            // 将视频文件读入到字节数组中
            inputStream.read(data);

            response.setContentType("video/" + resource.getType());
            response.setHeader("Content-Disposition", "attachment; filename=\"" + resource.getOrigin_name() + "\"");
            response.setContentLength(data.length);
            response.setHeader("Content-Range", "" + Integer.valueOf(data.length - 1));
            response.setHeader("Accept-Ranges", "bytes");
            response.setHeader("Etag", "W/\"9767057-1323779115364\"");

            // 将视频文件的字节数组写入 response 中
            outputStream.write(data);
            outputStream.flush();

        } catch (Exception e) {
        } finally {
            try {
                if (null != outputStream)
                    outputStream.close();

                if (null != inputStream)
                    inputStream.close();
            } catch (IOException e) {
            }
        }
    }

}
