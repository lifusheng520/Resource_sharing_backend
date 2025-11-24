package com.sharing.serviceImpl;

import com.sharing.mapper.CommentMapper;
import com.sharing.pojo.Comment;
import com.sharing.pojo.CommentInfo;
import com.sharing.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    @Override
    public List<CommentInfo> getCommentInfo(int resource_id) {
        return this.commentMapper.getCommentAndUserInfo(resource_id);
    }

    @Override
    public List<Comment> getUserAllCommentByUserId(int user_id, int begin, int number) {
        return this.commentMapper.getAllCommentByUserId(user_id, begin, number);
    }

    @Override
    public int countUserComment(int user_id) {
        return this.commentMapper.getCommentNumbersByUserId(user_id);
    }

    @Override
    public int deleteCommentByList(List<Comment> commentList) {
        return this.commentMapper.deleteCommentByList(commentList);
    }

    @Override
    public int countResourceCommentNumber(int resource_id) {
        return this.commentMapper.countResourceCommentNumber(resource_id);
    }

    @Override
    public int supportCommentById(int id) {
        return this.commentMapper.supportCommentById(id);
    }

    @Override
    public int recordUserSupportComment(int comment_id, int user_id) {
        return this.commentMapper.recordUserSupportComment(comment_id, user_id);
    }

    @Override
    public  boolean isUserSupportComment(int comment_id, int user_id) {
        return this.commentMapper.searchUserSupportComment(comment_id, user_id).getAmount() > 0;
    }

    @Override
    public  List<Integer> getUserSupportedCommentInList(int user_id, List<Integer> comment_ids) {
        return this.commentMapper.getUserSupportedCommentInList(user_id, comment_ids);
    }

    @Override
    public int cancelUserSupportComment(int user_id, int comment_id) {
        return this.commentMapper.deleteUserSupportComment(user_id, comment_id);
    }

    @Override
    public int deductCommentSupportNumber(int comment_id) {
        return this.commentMapper.deductCommentSupportNumber(comment_id);
    }
    
}
