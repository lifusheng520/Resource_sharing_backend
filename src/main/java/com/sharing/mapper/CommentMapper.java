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
}
