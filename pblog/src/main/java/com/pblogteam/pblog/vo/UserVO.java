package com.pblogteam.pblog.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import java.util.Date;

@Data
public class UserVO
{
    private Integer id;
    //@JsonIgnore
    private String username;
    @JsonIgnore
    private String password;

    private String nickname;
    private String real_name;
    private String email;
    private String sex;
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "Asia/Shanghai")
    private Date birthday;
    private String edu_bg;
    private String photoUrl;
    private String description;
    private Integer attentionCount;
    private Byte privilege;
    private boolean myAttention;
}
