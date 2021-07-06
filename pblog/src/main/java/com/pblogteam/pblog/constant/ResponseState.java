package com.pblogteam.pblog.constant;

import lombok.Getter;

@Getter
public enum ResponseState {
    BODY_NOT_MATCH(400, "请求的数据格式不符！"),
    SIGNATURE_NOT_MATCH(401, "请求的数字签名不匹配!"),
    NOT_FOUND(404, "未找到该资源!"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误!"),
    SERVER_BUSY(503, "服务器正忙，请稍后再试!"),
    UNKNOWN_ERROR(504, "未知错误！"),
    SUCCESS(100, "成功！");

    private Integer code;
    private String msg;

    ResponseState(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
