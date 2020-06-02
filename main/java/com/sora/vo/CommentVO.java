package com.sora.vo;

import com.sora.domain.Comment;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/10 16:37
 */
@Data
public class CommentVO {
    public Comment preComment;
    public List<Comment> postCommentList;
}
