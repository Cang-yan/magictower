package com.yibingo.race.img.service;

import com.yibingo.race.img.utils.QrcodeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/26 18:05
 */
@Service
@Slf4j
public class QRcodeService {

    public void createQrcode(String content) throws Exception {
        byte[] bytes = QrcodeUtils.createQrcode(content, 800, null);
        Path path = Files.createTempFile("qrcode_800_", ".jpg");
        //generatedQrcodePaths.add(path);
        log.info("{}", path.toAbsolutePath());
        Files.write(path, bytes);

        bytes = QrcodeUtils.createQrcode(content, null);
        path = Files.createTempFile("qrcode_400_", ".jpg");
        //generatedQrcodePaths.add(path);
        log.info("{}", path.toAbsolutePath());
        Files.write(path, bytes);



    }


   /* public void testCreateQrcodeWithLogo() throws Exception {
        try (InputStream inputStream = ClassLoader.getSystemResourceAsStream("logo.png")) {
            File logoFile = Files.createTempFile("logo_", ".jpg").toFile();
            FileUtils.copyInputStreamToFile(inputStream, logoFile);
            log.info("{}", logoFile);
            byte[] bytes = QrcodeUtils.createQrcode(content, 800, logoFile);
            Path path = Files.createTempFile("qrcode_with_logo_", ".jpg");
            generatedQrcodePaths.add(path);
            log.info("{}", path.toAbsolutePath());
            Files.write(path, bytes);
        }
    }*/

}
