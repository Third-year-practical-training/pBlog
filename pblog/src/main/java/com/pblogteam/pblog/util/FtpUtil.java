package com.pblogteam.pblog.util;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;
import java.util.Properties;

public class FtpUtil {
    //ftp服务器ip地址
    private static final String FTP_ADDRESS = "120.27.239.4";

    private static final int FTP_PORT = 21;

    private static final String FTP_USERNAME = "myftp";

    private static final String FTP_PASSWORD = "123456";

    private static final String FTP_BASEPATH = "/home/vsftpd/myftp";

    public static boolean uploadFile(String fileName, InputStream input) {
        boolean success = false;
        FTPClient ftp = new FTPClient();
        try {
            int reply;
            ftp.connect(FTP_ADDRESS, FTP_PORT);
            ftp.login(FTP_USERNAME, FTP_PASSWORD);
            reply = ftp.getReplyCode();
            if (!FTPReply.isPositiveCompletion(reply)) {
                ftp.disconnect();
                return false;
            }
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            ftp.makeDirectory(FTP_BASEPATH);
            ftp.enterLocalPassiveMode();
            if (!ftp.storeFile(fileName, input)) {
                return false;
            }
            ;
            input.close();
            ftp.logout();
            success = true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (ftp.isConnected()) {
                try {
                    ftp.disconnect();
                } catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return success;
    }

    public static FTPClient initFTP(FTPClient ftp) throws IOException {

        ftp = new FTPClient();
        ftp.connect(FTP_ADDRESS, FTP_PORT);
        ftp.login(FTP_USERNAME, FTP_PASSWORD);
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        ftp.setControlEncoding("UTF-8");
        ftp.setBufferSize(1024 * 1024 * 10); //设置缓冲区上传速度为10M，默认为1K
        ftp.setFileType(FTP.BINARY_FILE_TYPE);//设置上传方式位字节
        ftp.enterLocalPassiveMode();//Switch to passive mode
        return ftp;
    }

    public static void destroy(FTPClient ftp) throws IOException {
        if (ftp != null) {
            ftp.disconnect();
            ftp = null;
        }
    }
}
