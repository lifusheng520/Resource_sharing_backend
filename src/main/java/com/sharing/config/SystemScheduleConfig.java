package com.sharing.config;

import cn.hutool.core.util.StrUtil;
import com.sharing.Utils.IllegalWordDisposeUtil;
import com.sharing.Utils.TextTypeFileUtil;
import com.sharing.pojo.Focus;
import com.sharing.pojo.UserResource;
import com.sharing.service.FavouriteService;
import com.sharing.service.PlatformResourceService;
import com.sharing.service.UserResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 系统定时任务配置
 *
 * @author 李福生
 * @date 2022-3-20
 * @time 上午 11:17
 */

@Configuration
@EnableScheduling
public class SystemScheduleConfig {

    @Value("${deleted.resource.record.timeout}")
    private int timeOutDays;

    @Value("${files.resource.upload.root.path}")
    private String uploadRootPath;

    @Autowired
    private FavouriteService favouriteService;

    @Autowired
    private PlatformResourceService platformResourceService;

    @Autowired
    private UserResourceService userResourceService;

    @Autowired
    private MyEmailSenderConfig sender;

    /**
     * cron(秒 分 时 日 月 周 年) *:所有值
     * 定时清理回收箱，超过{timeOutDays}天算过期
     */
    @Scheduled(cron = "0 0 0 * * ?")
//    @Scheduled(cron = "*/10 * * * * ?")
    public void clearDeletedResource() {
        // 获取回收箱中超时的资源
        List<Integer> resourceIdListList = this.favouriteService.getOverTimeDeletedResourceIdList(this.timeOutDays);

        if (resourceIdListList.size() != 0) {

            // 根据资源的id，删除评论
            this.platformResourceService.deleteCommentByResourceId(resourceIdListList);

            // 根据资源的id，删除收藏
            this.platformResourceService.deleteFavouriteByResourceId(resourceIdListList);

            // 根据资源的id，删除点赞记录
            this.platformResourceService.deleteSupportRecordByResourceId(resourceIdListList);

            // 根据资源的id，删除资源
            this.platformResourceService.realDeleteResourceByResourceId(resourceIdListList);

            List<UserResource> userResourceList = this.userResourceService.getUserResourceByIds(resourceIdListList);

            // 删除磁盘文件
            for (UserResource resource : userResourceList) {
                String fileName = this.uploadRootPath + File.separator + resource.getDiscipline() + File.separator + resource.getDisk_name();
                this.deleteDiskFile(fileName);
            }

            // 清理回收箱
            this.favouriteService.clearOvertimeDeletedRecord(this.timeOutDays);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName 被删除文件的文件名
     * @return 单个文件删除成功返回true, 否则返回false
     */
    public boolean deleteDiskFile(String fileName) {
        File file = new File(fileName);
        if (file.isFile() && file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }


    /**
     * 自动审批资源
     */
//    @Scheduled(cron = "0 */1 * * * ?")
    @Scheduled(cron = "0 0 3 * * ?")
    public void autoCheckResource() {
        // 读取需要审批的资源集合
        List<UserResource> checkResourceList = this.platformResourceService.getCheckResourceList();

        // 审批成功的资源容器
        ArrayList<Integer> passCheckResourceIdList = new ArrayList<>();

        // 审批失败的资源容器
        ArrayList<Integer> failCheckResourceIdList = new ArrayList<>();

        // 遍历集合检查内容是否违规
        for (UserResource resource : checkResourceList) {
            // 获取资源的磁盘文件
            String file = this.uploadRootPath + File.separator + resource.getDiscipline() + File.separator + resource.getDisk_name();

            // 获取文件内容
            String fileString = TextTypeFileUtil.getFileString(file, resource.getType());

            // 文件为空，或者文件类型不支持自动审批
            if (StrUtil.isEmpty(fileString))
                continue;

            // 进行敏感词检查
            boolean contain = IllegalWordDisposeUtil.contain(fileString);
            // 如果没有违规，则通过审批
            if (!contain)
                passCheckResourceIdList.add(resource.getId());
            else
                failCheckResourceIdList.add(resource.getId());

        }

        // 更新审批成功容器内的资源
        if (passCheckResourceIdList.size() != 0){
            this.platformResourceService.updateCheckStateByResourceIdList(passCheckResourceIdList, "已通过审批");
            List<Focus> focusUserInfoList = this.platformResourceService.getPassCheckResourceFocusUserInfoList(passCheckResourceIdList);
            this.sendPushMessageByEmail(focusUserInfoList);

        }

        // 更新审批失败的资源
        if (failCheckResourceIdList.size() != 0)
            this.platformResourceService.updateCheckStateByResourceIdList(failCheckResourceIdList, "资源违规");
    }

    /**
     * 发送资源推送邮件
     *
     * @param focusList 关注信息
     */
    public void sendPushMessageByEmail(List<Focus> focusList) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
        for (Focus focus : focusList) {
            // 如果关注的用户绑定了邮箱
            String email = focus.getFocusUserEmail();
            if (email == null || email.length() == 0)
                continue;

            StringBuffer title = new StringBuffer();
            title.append("白给网")
                    .append(" | 关注推送");

            StringBuffer content = new StringBuffer();
            content.append("你关注的用户: ").append(focus.getFocusUserName())
                    .append(" 刚刚发布新的资源啦!").append("</br>")
                    .append("名称: ").append(focus.getFocusOriginName()).append("</br>")
                    .append("发布时间: ").append(format.format(new Date())).append("</br>")
                    .append("赶快来看看吧!")
                    .append("<a href=http://localhost:8086/focus>").append("查看详情").append("</a>");

            this.sender.sendHTMLEmail(email, title.toString(), content.toString());
        }
    }

}
