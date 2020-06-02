package com.sora.vo;

import lombok.Data;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * @author Sora
 * @version 1.0
 * @date 2020/3/30 21:16
 */
@Data
public class ResultVO {
    private int status;
    private String message;
    private Object result;

    public void setResult(Object result){
        this.result = result;
        this.status = 200;
        this.message = "success";
    }
}
