package com.yibingo.race.img.service;

import cn.hutool.core.codec.Base64Encoder;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import com.yibingo.race.img.utils.MatrixToLogoImageConfig;
import com.yibingo.race.img.utils.QrcodeUtils;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/26 20:46
 */
@Service
public class SharePosterService {

    public void createPoster(String code, String content, ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response) throws Exception {
        //海报图片
        ClassPathResource classPathResource = new ClassPathResource("/static/poster.png");
        //BufferedImage backAroundBuffer = ImageIO.read(new URL("https://meta-1312685046.cos.ap-nanjing.myqcloud.com/metaPoster.png"));
        BufferedImage backAroundBuffer = ImageIO.read(classPathResource.getStream());
        Color black = new Color(0, 0, 0); // 黑色

        //添加文字水印
        Image backAroundTextImg = ImgUtil.pressText(backAroundBuffer, code, black, new Font("宋体", Font.BOLD, 75), -35, 852, 1);


        //添加图片水印
        byte[] bytes = QrcodeUtils.createQrcode(content, 180, null);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        ImgUtil.pressImage(backAroundTextImg, byteArrayOutputStream, ImageIO.read(bais), 280, 796, 1);

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Content-Disposition", "attachment; filename=" + "anyoneElse" + File.separator + "jpeg");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Last-Modified", "new Date().toString()");
        response.setHeader("ETag", String.valueOf(System.currentTimeMillis()));
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        response.setContentLength(byteArrayOutputStream.size());
        ServletOutputStream out = response.getOutputStream();
        out.write(byteArrayOutputStream.toByteArray());
        byteArrayOutputStream.close();
        out.close();
        out.flush();



    }


    public String createPoster(String code, String content, ByteArrayOutputStream byteArrayOutputStream) throws Exception {
        //海报图片
        BufferedImage backAroundBuffer = ImageIO.read(new URL("https://meta-1312685046.cos.ap-nanjing.myqcloud.com/metaPoster.png"));
        Color black = new Color(0, 0, 0); // 黑色

        //添加文字水印
        Image backAroundTextImg = ImgUtil.pressText(backAroundBuffer, code, black, new Font("宋体", Font.BOLD, 80), -40, 851, 1);


        //添加图片水印
        byte[] bytes = QrcodeUtils.createQrcode(content, 180, null);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        ImgUtil.pressImage(backAroundTextImg, byteArrayOutputStream, ImageIO.read(bais), 280, 796, 1);


        String base64 = Base64Encoder.encode(byteArrayOutputStream.toByteArray());
        //out.write(byteArrayOutputStream.toByteArray());

        byteArrayOutputStream.close();

        //out.flush();
        return base64;


    }

    public void createH5QRCode(String content, ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response) throws Exception{
        ClassPathResource classPathResource = new ClassPathResource("/static/logo.png" );
        MatrixToLogoImageConfig logoConfig = new MatrixToLogoImageConfig();

        //createQrcode(String content, int length, File logoFile, MatrixToLogoImageConfig logoConfig)
        //byte[] bytes = QrcodeUtils.createQrcode(content, 180, classPathResource.getFile());
        byte[] bytes = QrcodeUtils.createQrcode(content, 500, classPathResource.getFile());

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Content-Disposition", "attachment; filename=" + "anyoneElse" + File.separator + "png");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Last-Modified", "new Date().toString()");
        response.setHeader("ETag", String.valueOf(System.currentTimeMillis()));
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setContentLength(bytes.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        byteArrayOutputStream.close();
        out.close();
        out.flush();

    }

    public void createDownloadQRCode(String content, ByteArrayOutputStream byteArrayOutputStream, HttpServletResponse response) throws Exception{
        ClassPathResource classPathResource = new ClassPathResource("/static/logo.png" );
        MatrixToLogoImageConfig logoConfig = new MatrixToLogoImageConfig();

        //createQrcode(String content, int length, File logoFile, MatrixToLogoImageConfig logoConfig)
        //byte[] bytes = QrcodeUtils.createQrcode(content, 180, classPathResource.getFile());
        byte[] bytes = QrcodeUtils.createQrcode(content, 500, classPathResource.getFile());

        response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Content-Disposition", "attachment; filename=" + "anyoneElse" + File.separator + "png");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        response.setHeader("Last-Modified", "new Date().toString()");
        response.setHeader("ETag", String.valueOf(System.currentTimeMillis()));
        response.setContentType(MediaType.IMAGE_PNG_VALUE);
        response.setContentLength(bytes.length);
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        byteArrayOutputStream.close();
        out.close();
        out.flush();

    }
}
