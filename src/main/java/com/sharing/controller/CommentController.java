package com.sharing.controller;

import com.sharing.Utils.IllegalWordDisposeUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.Comment;
import com.sharing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Date;
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

    /**
     * 判断字符串是否为空
     *
     * @param str 需要判断的字符串参数
     * @return 如果字符串不为空返回true，否则返回false
     */
    boolean strIsNull(String str) {
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

        if (!this.strIsNull(resourceId) && !this.strIsNull(userId))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_LOSE, "rid,uid");

        Comment comment = new Comment();
        comment.setResource_id(Integer.valueOf(resourceId));
        comment.setUser_id(Integer.valueOf(userId));
        comment.setSupport_number(0);
        comment.setTime(new Date());

        if (this.strIsNull(toId))    // 不为空，为回复评论
            comment.setTo_uid(Integer.valueOf(toId));
        else
            comment.setTo_uid(-1);

        // 对评论进行敏感词过滤，用 * 替其中的换敏感字符
        String hiddenStr = null;
        if (content != null || !"".equals(content))
            hiddenStr = IllegalWordDisposeUtil.hideIllegalWords(content, '*');
        comment.setContent(hiddenStr);

        // 将过滤后的评论进行持久化
        int i = this.commentService.publishComment(comment);

        if (i > 0)
            return ResultFormatUtil.format(ResponseCode.RESOURCE_COMMENT_SUCCESS, hiddenStr);
        else
            return ResultFormatUtil.format(ResponseCode.RESOURCE_COMMENT_FAIL, comment);
    }


}
