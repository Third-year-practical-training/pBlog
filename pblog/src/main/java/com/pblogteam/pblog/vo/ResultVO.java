package com.pblogteam.pblog.vo;

import com.pblogteam.pblog.constant.ResponseState;
import com.pblogteam.pblog.exception.BlogException;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    private Integer code;
    private String msg;
    private T data;

    public ResultVO(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.data = null;
    }

    public static ResultVO throwError(BlogException e) {
        return new ResultVO(e.getCode(), e.getMsg());
    }

    public static ResultVO throwError(Integer code, String msg) {
        return new ResultVO(code, msg);
    }

    public static ResultVO throwError(ResponseState e) {
        return new ResultVO(e.getCode(), e.getMsg());
    }

    public static <T> ResultVO<T> throwSuccessAndData(ResponseState e, T data) {
        return new ResultVO<T>(e.getCode(), e.getMsg(), data);
    }

    public static ResultVO throwSuccess(ResponseState e) {
        return new ResultVO(e.getCode(), e.getMsg());
    }
}
