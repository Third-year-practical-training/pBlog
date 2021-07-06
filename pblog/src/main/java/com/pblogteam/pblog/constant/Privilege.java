package com.pblogteam.pblog.constant;

import lombok.Getter;

@Getter
public enum Privilege {
    ADMIN(1);
    private Integer privilege;

    Privilege(Integer code) {
        this.privilege = code;
    }
}
