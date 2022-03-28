package com.sharing.controller;

import com.sharing.Utils.IllegalWordDisposeUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.Comment;
import com.sharing.pojo.CommentInfo;
import com.sharing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 资源评论接口
 *
 * @author 李福生
 * @date 2022-3-26
 * @time 上午 09:27
 */

@RestController
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Value("${files.icon.host.url}")
    private String iconHostURL;

    /**
     * 判断字符串是否为空
     *
     * @param str 需要判断的字符串参数
     * @return 如果字符串不为空返回true，否则返回false
     */
    boolean strIsNotNull(String str) {
        return str != null && !"".equals(str);
    }

    /**
     * 添加资源评论接口
     *
     * @param params 评论参数
     * @return 返回通过敏感词过滤后的内容
     */
    @PostMapping("/add")
    public String addComment(@RequestBody Map<String, String> params) throws IOException {
        String resourceId = params.get("resource_id");
        String userId = params.get("user_id");
        String content = params.get("content");
        String toId = params.get("to_id");
        String tempContent = content.replaceAll(" ", "");

        if (!this.strIsNotNull(resourceId) && !this.strIsNotNull(userId) && !this.strIsNotNull(tempContent))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, "rid,uid");

        Comment comment = new Comment();
        comment.setResource_id(Integer.valueOf(resourceId));
        comment.setUser_id(Integer.valueOf(userId));
        comment.setSupport_number(0);
        comment.setTime(new Date());

        if (this.strIsNotNull(toId))    // 不为空，为回复评论
            comment.setTo_uid(Integer.valueOf(toId));
        else
            comment.setTo_uid(-1);

        // 对评论进行敏感词过滤，用 * 替其中的换敏感字符
        String hiddenStr = null;
        if (this.strIsNotNull(content)) {
            //  去除空格
            content = content.replace(" ", "");
            hiddenStr = IllegalWordDisposeUtil.hideIllegalWords(content, '*');
        }
        comment.setContent(hiddenStr);

        // 将过滤后的评论进行持久化
        int i = this.commentService.publishComment(comment);

        ResponseCode responseCode;
        if (i > 0) {
            // 过滤后的内容和原字符串不一样，说明用户评论带有违规内容，给予警告
            if (!content.equals(hiddenStr)) {
                responseCode = ResponseCode.ILLEGAL_RESOURCE_COMMENT_CONTENT;
            } else {
                responseCode = ResponseCode.RESOURCE_COMMENT_SUCCESS;
            }

        } else
            return ResultFormatUtil.format(ResponseCode.RESOURCE_COMMENT_FAIL, comment);

        return ResultFormatUtil.format(responseCode, content);
    }


    /**
     * 获取资源的评论区内容信息
     *
     * @param resource_id 资源id
     * @return 返回资源评论区内容的
     */
    @GetMapping("/load/{resource_id}")
    public String getCommentInfo(@PathVariable Integer resource_id) {
        if (resource_id == null || resource_id == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, "resource_id");

        // 查询数据库中的资源的评论区内容
        List<CommentInfo> commentInfoList = this.commentService.getCommentInfo(resource_id);

        // 添加头像URL地址
        for (CommentInfo commentInfo : commentInfoList) {
            String iconUrl;
            if (this.strIsNotNull(commentInfo.getHeadIcon()))
                iconUrl = this.iconHostURL + commentInfo.getHeadIcon();
            else
                iconUrl = "";
            commentInfo.setHeadIcon(iconUrl);
        }

        return ResultFormatUtil.format(ResponseCode.GET_RESOURCE_COMMENT_INFO_SUCCESS, commentInfoList);
    }


}
