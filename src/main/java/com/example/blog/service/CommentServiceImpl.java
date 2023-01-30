package com.example.blog.service;

import com.example.blog.constant.Messages;
import com.example.blog.entity.Comment;
import com.example.blog.repository.CommentRepository;
import com.example.blog.util.AuthUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public List<Comment> get() {
        return commentRepository.findAllByOrderByCreateTimeDesc();
    }

    @Override
    public Comment getById(UUID commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }

    @Override
    public List<Comment> getByUserId(UUID userId) {
        return commentRepository.findCommentsByUserId(userId);
    }

    @Override
    public List<Comment> getByArticleId(UUID aId) {
        return commentRepository.findCommentsByArticleIdOrderByCreateTimeDesc(aId);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Messages remove(UUID commentId) {//管理员删除评论业务
        Comment comment=commentRepository.findById(commentId).orElse(null);
        if(comment==null){
            return Messages.WRONG_ID;
        }
        commentRepository.delete(comment);
        return  Messages.SUCCESS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Messages remove(UUID commentId, String token) {//评论本人删除业务
        Comment comment=commentRepository.findById(commentId).orElse(null);
        if(comment==null){
            return Messages.WRONG_ID;
        }
        UUID userId=comment.getUser().getId();
        if(AuthUtils.invalid(userId,token)){
            return  Messages.NO_PERMISSION;
        }
        commentRepository.delete(comment);
        return  Messages.SUCCESS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Comment add(Comment comment) {
        return commentRepository.save(comment);
    }
}
