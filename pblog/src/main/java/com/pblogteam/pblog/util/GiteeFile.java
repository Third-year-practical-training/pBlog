package com.pblogteam.pblog.util;

import cn.hutool.core.codec.Base64;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.pblogteam.pblog.config.GiteeImg;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class GiteeFile {

    public static String uploadImage(MultipartFile file, String fileName) {
        String paramImgFile = null;
        try {
            paramImgFile = Base64.encode(file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("access_token", GiteeImg.ACCESS_TOKEN);
        paramMap.put("message", GiteeImg.ADD_MESSAGE);
        paramMap.put("content", paramImgFile);
        //转存文件路径
        String targetDir = GiteeImg.PATH + fileName;
        //设置请求路径
        String requestUrl = String.format(GiteeImg.CREATE_REPOS_URL, GiteeImg.OWNER,
                GiteeImg.REPO_NAME, targetDir);

        String resultJson = HttpUtil.post(requestUrl, paramMap);
        JSONObject jsonObject = JSONUtil.parseObj(resultJson);
        if(jsonObject.getObj("commit") == null) {
            return null;
        }
        JSONObject content = JSONUtil.parseObj(jsonObject.getObj("content"));
        return (String) content.getObj("download_url");
    }
}
