package com.pblogteam.pblog.util;

import org.junit.Test;
import org.springframework.web.multipart.MultipartFile;

import static org.junit.Assert.*;

public class GiteeFileTest {
    private GiteeFile giteeFile;
    @Test
    public void uploadImage() {
        giteeFile = new GiteeFile();
    }
}