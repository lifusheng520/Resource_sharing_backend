package com.sharing.mapper;

import com.sharing.pojo.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

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
}
