package com.sharing.controller;

import com.sharing.Utils.IllegalWordDisposeUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.pojo.Comment;
import com.sharing.pojo.CommentInfo;
import com.sharing.pojo.MyPage;
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
     * 统计字符串中出现符号sign的值的个数
     *
     * @param text 处理的字符串
     * @param sign 统计的符号
     * @return 返回包含的个数，若字符串为空或不包含，则返回0
     */
    int countStringSign(String text, char sign) {
        // 空的字符串，直接返回 0
        if (!this.strIsNotNull(text))
            return 0;
        int count = 0;
        for (int i = 0; i < text.length(); i++) {
            if (text.charAt(i) == sign)
                count++;
        }
        return count;
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
        char replace = '*';
        String hiddenStr = IllegalWordDisposeUtil.hideIllegalWords(tempContent, replace);

        // 统计评论中的屏蔽符号
        int countHide = this.countStringSign(hiddenStr, replace);
        int countOrigin = this.countStringSign(content, replace);

        // 如果过滤的字符串中屏蔽符号多余原字符串，说明为违规评论
        if (countHide > countOrigin) {
            comment.setContent(hiddenStr);
            comment.setIsIllegal(1);
        } else {
            comment.setContent(content);
            comment.setIsIllegal(0);
        }

        // 将过滤后的评论进行持久化
        int i = this.commentService.publishComment(comment);

        ResponseCode responseCode;
        if (i > 0) {
            // 过滤后的内容和原字符串不一样，说明用户评论带有违规内容，给予警告
            if (countHide > countOrigin) {
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
     * 
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


    /**
     * 根据参数获取评论内容list
     *
     * @param params 参数map
     * @return 返回用户评论内容列表json
     */
    @PostMapping("/manager")
    public String getUserCommentInfo(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String user_id = params.get("user_id");
        String currentPage = params.get("currentPage");
        String totalPage = params.get("total");
        String pageSize = params.get("pageSize");
        if (!this.strIsNotNull(user_id) || !this.strIsNotNull(totalPage))
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, params);

        // 获取分页参数
        Integer userId = Integer.valueOf(user_id);
        int page = MyPage.getValidTransfer(currentPage, "page");
        int size = MyPage.getValidTransfer(pageSize, "size");

        // 根据用户id查询用户的评论
        List<Comment> commentList = this.commentService.getUserAllCommentByUserId(userId, (page - 1) * size, size);

        // 设置分页参数
        MyPage<Comment> commentMyPage = new MyPage<>();
        commentMyPage.setCurrentPage(page);
        commentMyPage.setPageSize(size);
        commentMyPage.setPageList(commentList);

        int total = Integer.valueOf(totalPage);
        if (total < 0) {
            total = this.commentService.countUserComment(userId);
        }
        commentMyPage.setTotal(total);

        return ResultFormatUtil.format(ResponseCode.GET_USER_ALL_COMMENT_SUCCESS, commentMyPage);
    }

    /**
     * 删除评论接口
     *
     * @param commentList 需要删除的评论内容list集合
     * @return 返回删除的结果json
     */
    @PostMapping("/delete")
    public String deleteListOfComment(@RequestBody List<Comment> commentList) {
        if (commentList == null || commentList.size() == 0)
            return ResultFormatUtil.format(ResponseCode.REQUEST_PARAM_ERROR, commentList);

        // 删除list中包含的评论
        int i = this.commentService.deleteCommentByList(commentList);

        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.DELETE_COMMENT_SUCCESS;
        else
            responseCode = ResponseCode.DELETE_COMMENT_FAIL;

        return ResultFormatUtil.format(responseCode, commentList);
    }

    /**
     * 对某条评论内容进行点赞
     *
     * @param params 请求参数
     * @return 返回删除的结果json
     */
    @PostMapping("/support")
    public String supportComment(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String commentId = params.get("comment_id");
        String userId = params.get("user_id");

        // 是否已经点赞过该评论
        boolean isSupported = this.commentService.isUserSupportComment(Integer.valueOf(commentId), Integer.valueOf(userId));
        if (isSupported)
            return ResultFormatUtil.format(ResponseCode.USER_ALREADY_SUPPORT_COMMENT, true);

        // 为对应id的评论增加点赞数
        int i = this.commentService.supportCommentById(Integer.valueOf(commentId));

        // 记录用户点赞行为
        this.commentService.recordUserSupportComment(Integer.valueOf(commentId), Integer.valueOf(userId));

        ResponseCode responseCode;
        if (i > 0)
            responseCode = ResponseCode.UPDATE_COMMENT_SUPPORT_SUCCESS;
        else
            responseCode = ResponseCode.UPDATE_COMMENT_SUPPORT_FAIL;

        return ResultFormatUtil.format(responseCode, null);
    }

    /**
     * 评论区有哪些评论被当前用户点赞了
     *
     * @param params 请求参数，包含评论id list和用户id
     * @return 返回删除的结果json
     */
    @PostMapping("/getSupportInfo")
    public String supportInComment(@RequestBody Map<String, Object> params) {
        // 获取请求参数
        String userId = (String) params.get("user_id");
        List<Integer> commentIdList = (List<Integer>) params.get("comment_id_list");

        // 查询该评论区的评论中，有哪个comment被该用户点赞了
        List<Integer> supportedCommentIdList = this.commentService.getUserSupportedCommentInList(Integer.valueOf(userId), commentIdList);

        return ResultFormatUtil.format(ResponseCode.FIND_OUT_SUPPORTED_COMMENT_SUCCESS, supportedCommentIdList);
    }

    /**
     * 取消点赞接口
     *
     * @param params 请求参数，包含评论id list和用户id
     * @return 返回删除的结果json
     */
    @PostMapping("/cancelSupport")
    public String cancelSupport(@RequestBody Map<String, String> params) {
        // 获取请求参数
        String userId = params.get("user_id");
        String commentId = params.get("comment_id");

        // 用户是否点赞过该评论
        boolean isSupported = this.commentService.isUserSupportComment(Integer.valueOf(commentId), Integer.valueOf(userId));
        if (!isSupported)
            return ResultFormatUtil.format(ResponseCode.DO_NOT_EXIST_COMMENT_SUPPORT, null);

        // 删除该用户对该评论的点赞记录
        int j = this.commentService.cancelUserSupportComment(Integer.valueOf(userId), Integer.valueOf(commentId));

        // 为对应id的评论减少点赞数
        int i = this.commentService.deductCommentSupportNumber(Integer.valueOf(commentId));

        ResponseCode responseCode;
        if (i > 0 && j > 0)
            responseCode = ResponseCode.ADD_COMMENT_SUPPORT_SUCCESS;
        else
            responseCode = ResponseCode.ADD_COMMENT_SUPPORT_FAIL;    
        return ResultFormatUtil.format(responseCode, null);
    }


}
