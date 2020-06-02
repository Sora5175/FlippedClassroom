package com.sora.service;

import com.sora.domain.Comment;
import com.sora.vo.CommentVO;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/10 13:12
 */
public interface ICommentService {
    public int addComment(Comment comment,long blogId) throws Exception;

    public List<CommentVO> findAllByBlogId(long blogId) throws Exception;

    public int topComment(Comment comment) throws Exception;

    public int deleteComment(Comment comment) throws Exception;

    public List<Comment> findAllReplyByUserId(long userId) throws Exception;
}
