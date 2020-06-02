package com.sora.vo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 16:45
 */
@Data
public class WeiXinLoginMessage {
    private String openid;
    private String session_key;
}
