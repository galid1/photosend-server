package com.photosend.photosendserver01.user.service;

import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileUtilTest {

    @Autowired
    private FileUtil fileUtil;

    String filePath = "/Users/jeonjun-yeob/clothes/clothesImage.png";

    @Test
    public void 파일_업로드_Test() {
        // given
        MockMultipartFile mockMultipartFile = new MockMultipartFile("test", "test.png", null, "test txt data...".getBytes());

        // when
        fileUtil.uploadFile(filePath, mockMultipartFile);

        // then
        File file = new File(filePath);
        assertTrue(file.exists());
    }

    @Test
    public void 파일_지우기_Test() throws IOException {
        // given
        File file = new File(filePath);

        file.createNewFile();

        // when
        fileUtil.deleteFile(filePath);

        // then
        assertFalse(file.exists());
    }

    // 테스트 후 file 정리
    @After
    public void afterTest() throws IOException{
        File file = new File(filePath);

        if(file.exists())
            file.delete();
    }
}
