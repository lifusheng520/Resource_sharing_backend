package com.sharing.service;

import com.sharing.pojo.Comment;
import com.sharing.pojo.CommentInfo;

import java.util.List;

/**
 * 评论业务接口
 *
 * @author 李福生
 * @date 2022-3-27
 * @time 下午 12:46
 */
public interface CommentService {


    /**
     * 添加一条评论
     *
     * @param comment 评论内容和信息
     * @return 返回添加结果
     */
    int publishComment(Comment comment);

    /**
     * 根据资源的id获取资源评论区内容
     *
     * @param resource_id 资源的id
     * @return 返回评论信息list
     */
    List<CommentInfo> getCommentInfo(int resource_id);

}
