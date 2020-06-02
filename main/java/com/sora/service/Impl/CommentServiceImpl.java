package com.sora.service.Impl;

import com.sora.dao.ICommentDao;
import com.sora.domain.Comment;
import com.sora.service.ICommentService;
import com.sora.utils.CommentsSortUtils;
import com.sora.vo.CommentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/10 13:14
 */
@Service
public class CommentServiceImpl  implements ICommentService {

    @Autowired
    private ICommentDao commentDao;


    @Override
    public int addComment(Comment comment, long blogId) {
        comment.setPublishTime(new Date());
        if(comment.getPreId()==0){
            return commentDao.addPreComment(comment,blogId);
        }else if(blogId == -1){
            blogId = commentDao.getBlogIdById(comment.getPreId());
        }
        return commentDao.addComment(comment,blogId);
    }

    @Override
    public List<CommentVO> findAllByBlogId(long blogId) {
        List<Comment> commentList = commentDao.findAllTeacherByBlogId(blogId);
        commentList.addAll(commentDao.findAllStudentByBlogId(blogId));
        return CommentsSortUtils.sort(commentList);
    }

    @Override
    public int topComment(Comment comment) throws Exception {
        if(comment.isTop()){
            comment.setTop(false);
        }else{
            comment.setTop(true);
        }
        return commentDao.updateComment(comment);
    }

    @Override
    public int deleteComment(Comment comment) throws Exception{
        return commentDao.deleteComment(comment);
    }

    @Override
    public List<Comment> findAllReplyByUserId(long userId) throws Exception{
        List<Comment> commentList = commentDao.findAllTeacherReplyByUserId(userId);
        commentList.addAll(commentDao.findAllStudentReplyByUserId(userId));
        commentList.sort(new Comparator<Comment>() {
            @Override
            public int compare(Comment o1, Comment o2) {
                return (int)(o2.getId()-o1.getId());
            }
        });
        return commentList;
    }
}
