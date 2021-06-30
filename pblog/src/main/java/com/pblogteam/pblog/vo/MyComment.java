package com.pblogteam.pblog.vo;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyComment {
    private Integer articleId;
    private String articleTitle;
    private Integer commentId;
}
