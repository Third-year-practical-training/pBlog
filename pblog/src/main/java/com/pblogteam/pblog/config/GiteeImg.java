package com.pblogteam.pblog.config;

import java.text.SimpleDateFormat;
import java.util.Date;

public class GiteeImg {
    public static String getNowDate() {
        Date now = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
        return formatter.format(now);
    }

    //码云私人令牌，需要补充
    public static final String ACCESS_TOKEN = "d1b63b22b3f632336fa86a183667c095";
    //个人空间名，需要补充
    public static final String OWNER = "luobuyu";
    //仓库名，需要补充
    public static final String REPO_NAME = "shixun";
    //文件夹路径
    public static final String PATH = "/img/" + getNowDate() + "/";
    //message消息
    public static final String ADD_MESSAGE = "add img";
    public static final String DEl_MESSAGE = "del img";

    /**
     * 新建文件请求路径
     * <p>
     * owner*   仓库所属空间地址(企业、组织或个人的地址path)
     * repo*    仓库路径
     * path*    文件的路径
     * content* 文件内容, 要用 base64 编码
     * message* 提交信息
     * <p>
     * %s =>仓库所属空间地址(企业、组织或个人的地址path)  (owner)
     * %s => 仓库路径(repo)
     * %s => 文件的路径(path)
     */
    public static String CREATE_REPOS_URL = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";
    /**
     * 获取路径下所有的内容
     * <p>
     * owner*   仓库所属空间地址(企业、组织或个人的地址path)
     * repo*    仓库路径
     * path*    文件的路径
     * content* 文件内容, 要用 base64 编码
     * message* 提交信息
     * <p>
     * %s =>仓库所属空间地址(企业、组织或个人的地址path)  (owner)
     * %s => 仓库路径(repo)
     * %s => 文件的路径(path)
     */
    public static String GET_IMG_URL = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";

    /**
     * 删除文件请求路径
     * <p>
     * owner*   仓库所属空间地址(企业、组织或个人的地址path)
     * repo*    仓库路径
     * path*    文件的路径
     * content* 文件内容, 要用 base64 编码
     * message* 提交信息
     * <p>
     * %s =>仓库所属空间地址(企业、组织或个人的地址path)  (owner)
     * %s => 仓库路径(repo)
     * %s => 文件的路径(path)
     */
    public static String DEL_IMG_URL = "https://gitee.com/api/v5/repos/%s/%s/contents/%s";

}
