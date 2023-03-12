package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.StrUtil;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.common.utils.SecurityUtil;
import com.yibingo.race.core.service.BaseInfoService;
import com.yibingo.race.core.service.UserInfoService;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.InvitationRecords;
import com.yibingo.race.dal.entity.User;
import com.yibingo.race.dal.filterMapper.InvitationRecordsFilterMapper;
import com.yibingo.race.ocr.baidu.BaiduIdCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/18 9:37
 */
@Api(
        value = "用户信息",
        tags = "用户信息"
)
@RestController
@RequestMapping("core/user/info")
public class UserInfoController {


    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private UserInviteBaseService userInviteBaseService;

    @Autowired
    private InvatitionRankBaseService invatitionRankBaseService;

    @Autowired
    private InvitationRecordsBaseService invitationRecordsBaseService;

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private BaiduIdCardService baiduIdCardService;

    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation(
            value = "获取注册状况",
            notes = "获取注册状况"
    )
    @RequestMapping(
            value = "/register/count",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> getRealNameCount() {
        List<User> userList = userBaseService.list();
        Integer allCount = userList.size();
        Integer realNameCount = userList.stream().filter(
                item -> !StrUtil.isBlank(item.getIdentity())
        ).collect(Collectors.toList()).size();
        Map<String, Object> map = new HashMap<>();
        map.put("allCount", allCount);
        map.put("realNameCount", realNameCount);
        return Result.success(map).map();
    }


    @ApiOperation(
            value = "实名认证",
            notes = "实名认证"
    )
    @RequestMapping(
            value = "/realname",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> realNameAuth(@RequestParam String realName,
                                            @RequestParam String identity,
                                            @RequestParam(required = false) String invitationCode) {
        String userId = StpUtil.getLoginIdAsString();
        User user = userInfoService.realNameAuth(userId, identity, realName);

       /* if (!StrUtil.isBlank(invitationCode)) {
            UserInviteFilterMapper userInviteFilterMapper = new UserInviteFilterMapper();
            userInviteFilterMapper.code = invitationCode;
            //生成邀请码应该是uuid，不能重复的且唯一的
            UserInvite userInvite = UserInviteFilterService.getListByFilter(userInviteBaseService, userInviteFilterMapper).stream().findFirst().orElse(null);
            if (userInvite == null) {
                throw new BaseException("邀新表有问题，请管理员核实");
            }
            //填写邀新记录
            InvitationRecords invitationRecords = new InvitationRecords();
            invitationRecords.setHostId(userInvite.getId());
            invitationRecords.setInvitedId(user.getId());
            invitationRecordsBaseService.save(invitationRecords);
        }*/

        //把实名认证的 计算在里面
        InvitationRecordsFilterMapper invitationRecordsFilterMapper = new InvitationRecordsFilterMapper();
        invitationRecordsFilterMapper.invitedId = userId;
        List<InvitationRecords> invitationRecordsList = InvitationRecordsFilterService.getListByFilter(invitationRecordsBaseService, invitationRecordsFilterMapper);
        if (!invitationRecordsList.isEmpty()) {
            //一个人只能被一个人邀请，invitedId只出现一次
            InvitationRecords invitationRecords = invitationRecordsList.get(0);
            invitationRecords.setIsIdentified(1);
            invitationRecordsBaseService.updateById(invitationRecords);

        }
        return Result.success(
                baseInfoService.userBaseInfoByEntity(user)
        ).map();


    }

    @ApiOperation(
            value = "实名认证",
            notes = "实名认证"
    )
    @RequestMapping(
            value = "/idcard",
            method = RequestMethod.POST
    )
    @ResponseBody
    @Transactional(
            rollbackFor = Exception.class
    )
    @SaCheckLogin
    public Map<String, Object> idCardOcr(@RequestBody MultipartFile file,
                                         @RequestParam(required = false) String invitationCode) {
        String userId = StpUtil.getLoginIdAsString();

        String imgBase64 = null;
        byte[] imgByte = null;
        try {
            imgByte = file.getBytes();
            imgBase64 = Base64.encode(file.getBytes());
        } catch (IOException ioException) {
            throw new BaseException(ioException.toString());
        }

        Map<String, Object> result = baiduIdCardService.idCardOcr(imgBase64, imgByte);
        User user = userInfoService.realNameAuth(userId, result.get("realName").toString(), result.get("identity").toString());


       /* if (invitationCode != null) {
            UserInviteFilterMapper userInviteFilterMapper = new UserInviteFilterMapper();
            userInviteFilterMapper.code = invitationCode;
            //生成邀请码应该是uuid，不能重复的且唯一的
            UserInvite userInvite = UserInviteFilterService.getListByFilter(userInviteBaseService, userInviteFilterMapper).stream().findFirst().orElse(null);
            if (userInvite == null) {
                throw new BaseException("邀新表有问题，请管理员核实");
            }
            //填写邀新记录
            InvitationRecords invitationRecords = new InvitationRecords();
            invitationRecords.setHostId(userInvite.getId());
            invitationRecords.setInvitedId(user.getId());
            invitationRecordsBaseService.save(invitationRecords);
        }*/


        return Result.success(
                baseInfoService.userBaseInfoByEntity(user)
        ).map();
    }

    @ApiOperation(
            value = "完善信息",
            notes = "完善信息"
    )
    @RequestMapping(
            value = "/perfect/info",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> perfectInfo(@RequestParam String userName,
                                           @RequestParam(required = false) String password,
                                           @RequestParam(required = false) String head,
                                           @RequestParam(required = false) String invitationCode
    ) {
        String userId = StpUtil.getLoginIdAsString();

        User user = userBaseService.getById(userId);
        user.setName(userName);
        if (StrUtil.isBlank(userName)) user.setName("魅塔收藏家");
        if (!StrUtil.isBlank(password)) user.setPassword(password);
        if (StrUtil.isBlank(head)) user.setHead("https://meta-1312685046.cos.ap-nanjing.myqcloud.com/logo001.PNG");
        user.setHead(head);
        userBaseService.updateById(user);


        return Result.success(
                baseInfoService.userBaseInfoByEntity(user)
        ).map();
    }

    @ApiOperation(
            value = "修改用户名",
            notes = "修改用户名"
    )
    @RequestMapping(
            value = "/change/name",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> changeName(@RequestParam String userName) {
        String userId = StpUtil.getLoginIdAsString();
        User user = userBaseService.getById(userId);
        user.setName(userName);
        userBaseService.updateById(user);

        return Result.success().map();

    }

    @ApiOperation(
            value = "修改头像",
            notes = "修改头像"
    )
    @RequestMapping(
            value = "/change/head",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> changeHead(@RequestParam String head) {
        String userId = StpUtil.getLoginIdAsString();
        User user = userBaseService.getById(userId);
        user.setName(head);
        userBaseService.updateById(user);

        return Result.success().map();

    }

    @ApiOperation(
            value = "查询用户实名信息",
            notes = "查询用户实名信息"
    )
    @RequestMapping(
            value = "/user/info",
            method = RequestMethod.GET
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> getUserInfo() {
        String userId = StpUtil.getLoginIdAsString();
        User user = userBaseService.getById(userId);
        Map<String, Object> data = baseInfoService.userBaseInfoByEntity(user);
        data.put("realName", user.getRealName());
        SecurityUtil securityUtil = new SecurityUtil();
        String idInfo = "";
        if (!StrUtil.isBlank(user.getIdentity())) {
            String idcard = securityUtil.decrypt(user.getIdentity());
             idInfo = "******" + idcard.substring(idcard.length() - 4);
        }
        data.put("identify", idInfo);


        return Result.success(
                data
        ).map();
    }

}
