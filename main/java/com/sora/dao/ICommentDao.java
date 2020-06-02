package com.sora.dao;

import com.sora.domain.Comment;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/10 11:05
 */
@Repository
public interface ICommentDao {
    public int addPreComment(Comment comment,long blogId);

    public int addComment(Comment comment,long blogId);

    public List<Comment> findAllTeacherByBlogId(long blogId) ;

    public List<Comment> findAllStudentByBlogId(long blogId) ;

    public int updateComment(Comment comment);

    public int deleteComment(Comment comment);

    public List<Comment> findAllTeacherReplyByUserId(long userId);

    public List<Comment> findAllStudentReplyByUserId(long userId);

    public long getBlogIdById(long commentId);
}
