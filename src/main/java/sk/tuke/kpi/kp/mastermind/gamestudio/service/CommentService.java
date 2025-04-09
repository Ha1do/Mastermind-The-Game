package sk.tuke.kpi.kp.mastermind.gamestudio.service;

import sk.tuke.kpi.kp.mastermind.gamestudio.entity.Comment;

import java.util.List;

public interface CommentService {
    void addComment(Comment comment) throws CommentException;
    List<Comment> getComments(String game) throws CommentException;
    void reset() throws CommentException;
}
