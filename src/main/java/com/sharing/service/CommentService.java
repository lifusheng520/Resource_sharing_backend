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

    /**
     * 通过用户id获取用户评论的所有内容
     *
     * @param user_id 用户id
     * @param begin   开始的位置
     * @param number  需要取的个数
     * @return 返回一个包含用户所有评论的list
     */
    List<Comment> getUserAllCommentByUserId(int user_id, int begin, int number);

    /**
     * 根据用户id获取用户评论的记录数
     *
     * @param user_id 用户id
     * @return 返回用户的评论记录数量
     */
    int countUserComment(int user_id);

    /**
     * 通过list集合中的评论id，删除包含的评论
     *
     * @param commentList 要删除的评论集合
     * @return 返回删除结果
     */
    int deleteCommentByList(List<Comment> commentList);

    /**
     * 根据资源的id，统计资源所拥有的评论数量
     *
     * @param resource_id 资源的id
     * @return 返回资源的评论总数
     */
    int countResourceCommentNumber(int resource_id);

    /**
     * 根据评论的id，增加评论点赞数量
     *
     * @param id 评论的id
     * @return 返回受影响的行数
     */
    int supportCommentById(int id);

    /**
     * 记录用户对评论的点赞行为
     *
     * @param comment_id 评论的id
     * @param user_id    用户的id
     * @return 返回受影响的行数
     */
    int recordUserSupportComment(int comment_id, int user_id);

    /**
     * 判断用户是否已经点赞过该评论
     *
     * @param comment_id 评论的id
     * @param user_id    用户的id
     * @return 返回受影响的行数
     */
    boolean isUserSupportComment(int comment_id, int user_id);


    /**
     * 获取评论区中被某用户点赞的评论id集合
     *
     * @param comment_ids 评论区的id list集合
     * @param user_id    用户的id
     * @return 被用户点赞的评论id集合
     */
    List<Integer> getUserSupportedCommentInList(int user_id, List<Integer> comment_ids);

    /**
     * 取消用户对某条评论的点赞
     *
     * @param comment_id 评论的id
     * @param user_id    用户的id
     * @return 返回受影响的行数
     */
    int cancelUserSupportComment(int user_id, int comment_id);

    /**
     * 减少评论的点赞数量
     *
     * @param comment_id 评论的id
     * @return 返回受影响的行数
     */
    int deductCommentSupportNumber(int comment_id);


}
