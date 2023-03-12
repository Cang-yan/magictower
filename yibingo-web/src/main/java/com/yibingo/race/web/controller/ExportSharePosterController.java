package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import com.yibingo.race.common.utils.ServletUtils;
import com.yibingo.race.core.service.UserInviteService;
import com.yibingo.race.img.service.SharePosterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/26 18:43
 */
@Api(
        value = "邀新海报" ,
        tags = "邀新海报"
)
@RestController
@RequestMapping("core/poster" )
public class ExportSharePosterController {


    @Autowired
    private SharePosterService sharePosterService;

    @Autowired
    private UserInviteService userInviteService;

    //todo dasdas fa
    @ApiOperation(
            value = "测试版分享海报" ,
            notes = "测试版分享海报"
    )
    @RequestMapping(
            value = "/test/share" ,
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    @SaCheckLogin
    public void exportPostersTest() throws Exception {

        String userId = StpUtil.getLoginIdAsString();
        String code = userInviteService.getInviteCode(userId);
//String code = "E36E85";
        HttpServletResponse response = ServletUtils.getResponse();

        String content = "https://www.meta-artwork.com:8045/#/?code=" + code;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
//        return Result.success(
//
//                sharePosterService.createPoster(code, content, byteArrayOutputStream)
//
//        ).map();

        sharePosterService.createPoster(code, content, byteArrayOutputStream, response);

    }

    @ApiOperation(
            value = "正式版分享海报" ,
            notes = "正式版分享海报"
    )
    @RequestMapping(
            value = "/share" ,
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    @SaCheckLogin
    public void exportPosters() throws Exception {

        String userId = StpUtil.getLoginIdAsString();
        String code = userInviteService.getInviteCode(userId);

        HttpServletResponse response = ServletUtils.getResponse();

        String content = "https://www.meta-artwork.com/#/?code=" + code;

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        sharePosterService.createPoster(code, content, byteArrayOutputStream, response);


        //return ResponseEntity.ok().headers(headers).contentLength(byteArrayOutputStream.size()).contentType(MediaType.IMAGE_JPEG).body(byteArrayOutputStream);

    }


    @ApiOperation(
            value = "正式版分享海报" ,
            notes = "正式版分享海报"
    )
    @RequestMapping(
            value = "/postercode" ,
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public void h5() throws Exception{
        HttpServletResponse response = ServletUtils.getResponse();

        String content = "https://www.meta-artwork.com/static/meta.apk";
        //String content = "https://www.meta-artwork.com";
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        sharePosterService.createH5QRCode(content, byteArrayOutputStream, response);

    }

}
