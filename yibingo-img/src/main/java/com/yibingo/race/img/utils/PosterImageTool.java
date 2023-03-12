package com.yibingo.race.img.utils;

//import org.junit.Test;

import cn.hutool.core.img.ImgUtil;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @ClassName : PosterImageTool
 * @Description : 海报生工具
 * @Author : bianmaren
 * @Date: 2020-04-09 21:14
 * @Bolg: http://www.bianmaren.com
 */
public class PosterImageTool {


    public void generateSharePoster(String code) throws Exception {
        String content = "https://www.meta-artwork.com:8045/#/pages/login/login?code=" + code;
        //海报图片
        URL url = new URL("https://meta-1312685046.cos.ap-nanjing.myqcloud.com/poster.jpg");

        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");

        conn.setConnectTimeout(5 * 1000);

        InputStream inStream = conn.getInputStream();// 通过输入流获取图片数据

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        Color black = new Color(0, 0, 0); // 黑色

        //添加文字水印
        ImgUtil.pressText(inStream, byteArrayOutputStream, code, black, new Font("宋体", Font.PLAIN, 20), 0, 0, 1);


        //添加图片水印
        byte[] bytes = QrcodeUtils.createQrcode(content, 800, null);
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);

        ImgUtil.pressImage(inStream, byteArrayOutputStream,ImageIO.read(bais),20,20,1);


    }


    //@Test
   /* public void generateSharePoster() throws Exception{

        // 存储路径
        String savePath = "/Users/dengwenbing/Downloads/testPosterImage.png";
        // 背景图片，网络地址，也可以自己改成本地地址
        String bgPath = "http://www.bianmaren.com/upload/image/20200409/1586438878193019624.png";
        // 头像
        String headImg = "http://www.bianmaren.com/upload/image/20200409/1586438971036060321.png";
        // 产品图片
        String productImg = "http://www.bianmaren.com//upload/image/20200409/1586439477682026373.png";
        // 二维码
        String qrcodeImg = "http://www.bianmaren.com/upload/image/20200409/1586439312240066963.png";
        // 昵称
        String nickName = "bianmaren";
        // 门店名称
        String storeName = "编码人";
        // 提示语
        String tips = "为您挑选了一个好物";
        // 价格
        String price = "88.00-199.00";
        // 羊角符号
        String pricePre = "￥";


        // 背景图
        BufferedImage bgBuffer = ImageIO.read(new URL(bgPath));

        // 头像
        BufferedImage avatarBuffer = ImageIO.read(new URL(headImg));

        BufferedImage avatarMinRoundBuffer = WxImageTool.roundImage(WxImageTool.resizeByHeight(avatarBuffer, 100), 100, 100);

        // 头像合并
        BufferedImage imgWithHeadImg = WxImageTool.synthesisPicAtXy(bgBuffer, avatarMinRoundBuffer, 35, 65);

        Color black = new Color(0, 0, 0); // 黑色
        Color grey = new Color(155, 155, 155); // 灰色
        Color red = new Color(255, 0, 0); // 红色

        // 添加用户昵称
        BufferedImage imgWithName = WxImageTool.addTxtAtXy(imgWithHeadImg, nickName, 160, 100, new Font("宋体", Font.PLAIN, 20), black);

        // 添加提示语
        BufferedImage imgWithTips = WxImageTool.addTxtAtXy(imgWithName, tips, 160, 150, new Font("宋体", Font.PLAIN, 25), grey);

        // 产品
        BufferedImage productBuffer = ImageIO.read(new URL(productImg));

        // 产品图片缩放
        BufferedImage goodsMinBuffer = WxImageTool.resizeByWidth(productBuffer, 678);

        // 合成产品图片
        BufferedImage imgWithGoods = WxImageTool.synthesisPicAtXy(imgWithTips, goodsMinBuffer, 35, 205);

        // 合成羊角符
        BufferedImage imgWithPricePre = WxImageTool.addTxtAtXy(imgWithGoods, pricePre, 55, 955, new Font("宋体", Font.PLAIN, 40), red);

        // 合成价格
        BufferedImage imgWithPrice = WxImageTool.addTxtAtXy(imgWithPricePre, price, 105, 955, new Font("宋体", Font.PLAIN, 50), red);

        // 合成店铺名称
        BufferedImage imgWithStoreName = WxImageTool.addTxtAtXy(imgWithPrice, storeName, 65, 1100, new Font("宋体", Font.PLAIN, 30), black);

        // 小程序码
        BufferedImage qrcodeBuffer = ImageIO.read(new URL(qrcodeImg));

        // 小程序码缩放
        BufferedImage qrcodeMinBuffer = WxImageTool.resizeByWidth(qrcodeBuffer, 170);

        // 小程序码合成
        BufferedImage imgWithQrcode = WxImageTool.synthesisPicAtXy(imgWithStoreName, qrcodeMinBuffer, 520, 1000);

        // 保存图片
        ImageIO.write(imgWithQrcode, "png", new File(savePath));

    }*/


    /**
     * 获取指定字体指定内容的宽度
     *
     * @param font    字体
     * @param content 内容
     * @return
     */
    public static int getWordWidth(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        int width = 0;
        for (int i = 0; i < content.length(); i++) {
            width += metrics.charWidth(content.charAt(i));
        }
        return width;
    }

    /**
     * 获取指定字体指定内容的宽度
     *
     * @param font    字体
     * @param content 内容
     * @return
     */
    public static int getWordWidthBody(Font font, String content) {
        FontDesignMetrics metrics = FontDesignMetrics.getMetrics(font);
        return metrics.stringWidth(content);
    }


    /**
     * 按比例裁剪图片
     *
     * @param image  待处理的图片流
     * @param startX 开始x坐标
     * @param startY 开始y坐标
     * @param endX   结束x坐标
     * @param endY   结束y坐标
     * @return
     */
    public static BufferedImage crop(BufferedImage image, int startX, int startY, int endX, int endY) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (startX <= -1) {
            startX = 0;
        }
        if (startY <= -1) {
            startY = 0;
        }
        if (endX <= -1) {
            endX = width - 1;
        }
        if (endY <= -1) {
            endY = height - 1;
        }
        BufferedImage result = new BufferedImage(endX, endY, image.getType());
        for (int y = startY; y < endY + startY; y++) {
            for (int x = startX; x < endX + startX; x++) {
                int rgb = image.getRGB(x, y);
                result.setRGB(x - startX, y - startY, rgb);
            }
        }
        return result;
    }


    /**
     * 图片缩放
     *
     * @param originalImage 原始图片
     * @param width         宽度
     * @param height        高度
     * @return
     */
    public static BufferedImage zoomInImage(BufferedImage originalImage, int width, int height) {
        /* 新建一个空白画布 */
        BufferedImage image = new BufferedImage(width, height, originalImage.getType());
        Graphics2D g2d = image.createGraphics();
        /* 设置背景透明 */
        image = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);
        g2d = image.createGraphics();
        g2d.drawImage(originalImage, 0, 0, width, height, null);
        g2d.dispose();
        return image;
    }

    /**
     * 实现图像的等比缩放(固定宽度)
     *
     * @param source  待处理的图片流
     * @param targetW 宽度
     * @return
     */
    public static BufferedImage resizeByWidth(BufferedImage source, double targetW) {
        double targetH = 0;
        double width = source.getWidth();// 图片宽度
        double height = source.getHeight();// 图片高度
        targetH = targetW / width * height;

        return zoomInImage(source, (int) targetW, (int) targetH);

    }

    /**
     * 实现图像的等比缩放(固定高度)
     *
     * @param source  待处理的图片流
     * @param targetH 高度
     * @return
     */
    public static BufferedImage resizeByHeight(BufferedImage source, double targetH) {
        double targetW;
        double width = source.getWidth();// 图片宽度
        double height = source.getHeight();// 图片高度
        targetW = targetH / height * width;
        return zoomInImage(source, (int) targetW, (int) targetH);
    }

    /***
     * 将图片处理为圆角图片
     * @param srcImgPath 源图片文件路径
     * @param destImgPath  新生成的图片的保存路径，需要带有保存的文件名和后缀
     * @param targetSize 文件的边长，单位：像素，最终得到的是一张正方形的图，所以要求targetSize<=源文件的最小边长
     * @param cornerRadius 圆角半径，单位：像素。如果=targetSize那么得到的是圆形图
     * @return 文件的保存路径
     * @throws IOException
     */
    public static String roundImage(String srcImgPath, String destImgPath, int targetSize, int cornerRadius) {
        BufferedImage bufferedImage = null;
        BufferedImage circularBufferImage = null;
        try {
            bufferedImage = ImageIO.read(new File(srcImgPath));
            circularBufferImage = roundImage(bufferedImage, targetSize, cornerRadius);
            ImageIO.write(circularBufferImage, "png", new File(destImgPath));
            return destImgPath;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bufferedImage != null) {
                bufferedImage.flush();
            }
            if (circularBufferImage != null) {
                circularBufferImage.flush();
            }
        }
        return destImgPath;
    }

    /**
     * 将图片处理为圆角图片
     *
     * @param image        待处理图片
     * @param targetSize   直径
     * @param cornerRadius 圆角半径，单位：像素。如果=targetSize那么得到的是圆形图
     * @return
     */
    public static BufferedImage roundImage(BufferedImage image, int targetSize, int cornerRadius) {
        BufferedImage outputImage = new BufferedImage(targetSize, targetSize, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = outputImage.createGraphics();
        g2d.setComposite(AlphaComposite.Src);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setColor(Color.WHITE);
        g2d.fill(new RoundRectangle2D.Float(0, 0, targetSize, targetSize, cornerRadius, cornerRadius));
        g2d.setComposite(AlphaComposite.SrcAtop);
        g2d.drawImage(image, 0, 0, null);
        g2d.dispose();
        return outputImage;
    }

    /**
     * 将第二张图片放到第一张的指定位置
     *
     * @param imageFirst
     * @param imageSecond
     * @param x
     * @param y
     */
    public static BufferedImage synthesisPicAtXy(BufferedImage imageFirst, BufferedImage imageSecond, int x, int y) {
        BufferedImage image = null;
        try {
            /* 读取第一张图片 宽高 */
            int width = imageFirst.getWidth();// 图片宽度
            int height = imageFirst.getHeight();// 图片高度

            /* 读取第二张图片 宽高 */
            int widthTwo = imageSecond.getWidth();// 图片宽度
            int heightTwo = imageSecond.getHeight();// 图片高度

            /* 计算总宽度 */
            int w = 0;
            if (x + widthTwo > width) {
                w = widthTwo + x;
            } else {
                w = width;
            }

            /* 计算总高度 */
            int h = 0;
            if (y + heightTwo > height) {
                h = heightTwo + y;
            } else {
                h = height;
            }

            /* 新建一个空白画布 */
            image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = image.createGraphics();

            /* 设置背景透明 */
            image = g2d.getDeviceConfiguration().createCompatibleImage(w, h, Transparency.TRANSLUCENT);
            g2d = image.createGraphics();
            g2d.drawImage(imageFirst, 0, 0, w, h, null);
            g2d.drawImage(imageSecond, x, y, widthTwo, heightTwo, null);

            return image;

        } catch (Exception e) {
            if (image != null) {
                image.flush();
            }
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 将第二张图片放到第一张的指定位置
     *
     * @param first
     * @param second
     * @param out
     * @param x
     * @param y
     */
    public static String synthesisPicAtXy(String first, String second, String out, int x, int y) {
        BufferedImage imageFirst = null;
        BufferedImage imageSecond = null;
        BufferedImage outBuffered = null;
        try {
            File fileOne = new File(first);
            imageFirst = ImageIO.read(fileOne);
            File fileTwo = new File(second);
            imageSecond = ImageIO.read(fileTwo);
            outBuffered = synthesisPicAtXy(imageFirst, imageSecond, x, y);
            File outFile = new File(out);
            assert outBuffered != null;
            ImageIO.write(outBuffered, "png", outFile);// 写图片
            return out;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (imageFirst != null) {
                imageFirst.flush();
            }
            if (imageSecond != null) {
                imageSecond.flush();
            }
            if (outBuffered != null) {
                outBuffered.flush();
            }
        }
        return null;
    }

    /**
     * 将文字添加到图片指定的位置
     *
     * @param src
     * @param out
     * @param x
     * @param y
     * @param center 可选居中 默认false
     * @return
     */
    public static String addTxtAtXy(String src, String out, String txt, int x, int y, boolean center, Font font, Color color) {
        BufferedImage picBuffer = null;
        BufferedImage outBuffered;
        try {
            File fileOne = new File(src);
            picBuffer = ImageIO.read(fileOne);
            outBuffered = addTxtAtXy(picBuffer, txt, x, y, font, color);
            File outFile = new File(out);
            assert outBuffered != null;
            ImageIO.write(outBuffered, "png", outFile);// 写图片
            return out;

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (picBuffer != null) {
                picBuffer.flush();
            }
        }
        return null;
    }

    /**
     * 将文字txt添加到图片指定的位置(x,y)
     *
     * @param src
     * @param txt
     * @param x
     * @param y
     * @return
     */
    public static BufferedImage addTxtAtXy(BufferedImage src, String txt, int x, int y, Font font, Color color) {
        BufferedImage outBuffer;
        try {

            /* 读取图片 宽高 */
            int width = src.getWidth();// 图片宽度
            int height = src.getHeight();// 图片高度

            /* 新建一个空白画布 */
            outBuffer = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            Graphics2D g2d = outBuffer.createGraphics();
            /* 设置背景透明 */
            outBuffer = g2d.getDeviceConfiguration().createCompatibleImage(width, height, Transparency.TRANSLUCENT);

            g2d = outBuffer.createGraphics();
            g2d.drawImage(src, 0, 0, width, height, null);

            g2d.setColor(color);
            g2d.setFont(font);

            // 10,20 表示这段文字在图片上的位置(x,y) .第一个是你设置的内容。
            g2d.drawString(txt, x, y);
            g2d.dispose();
            return outBuffer;

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return null;

    }

}
