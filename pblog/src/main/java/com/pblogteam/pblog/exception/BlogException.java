package com.pblogteam.pblog.exception;

import com.pblogteam.pblog.constant.ResponseState;
import lombok.Getter;

@Getter
public class BlogException extends RuntimeException {
    private Integer code;
    private String msg;
    public BlogException(ResponseState responseState) {
        super(responseState.getMsg());
        this.code = responseState.getCode();
        this.msg = responseState.getMsg();
    }

    public BlogException(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }


}
