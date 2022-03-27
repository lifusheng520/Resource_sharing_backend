package com.sharing.serviceImpl;

import com.sharing.mapper.CommentMapper;
import com.sharing.pojo.Comment;
import com.sharing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 李福生
 * @date 2022-3-27
 * @time 下午 12:47
 */

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Override
    public int publishComment(Comment comment) {
        return this.commentMapper.addComment(comment);
    }
}
