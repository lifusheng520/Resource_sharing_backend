package com.sharing.mapper;

import com.sharing.pojo.Comment;
import com.sharing.pojo.CommentInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 评论业务数据库操作mapper
 *
 * @author 李福生
 * @date 2022-3-27
 * @time 下午 12:49
 */

@Mapper
@Repository
public interface CommentMapper {

    /**
     * 添加一条评论
     *
     * @param comment 评论内容和信息
     * @return 返回添加结果
     */
    int addComment(Comment comment);

    /**
     * 根据资源的id查询资源评论区内容和对应用户的信息
     *
     * @param resource_id 资源的id
     * @return 返回评论信息和对应的用户内容lsit
     */
    List<CommentInfo> getCommentAndUserInfo(int resource_id);

    /**
     * 通过用户id获取用户评论的所有内容
     *
     * @param user_id 用户id
     * @param begin   开始的位置
     * @param number  需要取的个数
     * @return 返回一个包含用户所有评论的list
     */
    List<Comment> getAllCommentByUserId(int user_id, int begin, int number);

    /**
     * 根据用户id获取用户评论的记录数
     *
     * @param user_id 用户id
     * @return 返回用户的评论记录数量
     */
    int getCommentNumbersByUserId(int user_id);

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


}
