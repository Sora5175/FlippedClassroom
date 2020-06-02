package com.sora.vo;

import com.sora.domain.User;
import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/4/1 21:57
 */
@Data
public class TokenVO {
    private String token;
    private User user;
}
