package com.yibingo.race.satoken.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.common.utils.RedisUtils;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.BaseInfoService;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.InvitationRecords;
import com.yibingo.race.dal.entity.User;
import com.yibingo.race.dal.entity.UserInvite;
import com.yibingo.race.dal.filterMapper.UserFilterMapper;
import com.yibingo.race.dal.filterMapper.UserInviteFilterMapper;
import com.yibingo.race.sms.TxCloudSms;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @desc： 登录注册
 * @author： xhs
 * @date： 2021/8/19 12:54
 * @version： JDK 1.8
 */
@Api(value = "satoken",
        tags = "satoken")
@Slf4j
@RestController
@RequestMapping("login")
public class LoginController {

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private UserInviteBaseService userInviteBaseService;

    @Autowired
    private InvitationRecordsBaseService invitationRecordsBaseService;

    @Autowired
    private InvatitionRankBaseService invatitionRankBaseService;

    @Autowired
    private BaseInfoService baseInfoService;

    @Value("${selfTime.sms.expirationMilliSeconds}")
    private Long expireMilliSeconds;

    @Autowired
    private RedisUtils redisUtils;

    @Autowired
    private TxCloudSms txCloudSms;

    @ApiOperation(
            value = "发送手机验证码",
            notes = "发送手机验证码"
    )
    @RequestMapping(
            value = "/sms/send",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> smsSend(@RequestParam String phone) {
        if (phone.equals("123456")) {
           // redisUtils.set("手机号码是"+phone, "666666", expireMilliSeconds);

            return Result.success(redisUtils.get(phone)).map();
        }


        //生成验证码
        //利用哈希值生成验证码
        int hash = phone.hashCode();
        //加密
        int encryption = 2020666;
        //用hash异或上加密得到生成第一次加密结果
        //这个生成的加过永远是固定的如果加密码不动的情况下
        long result = hash ^ encryption;
        //利用获得当前时间再次加密得出结果
        long time = System.currentTimeMillis();
        result = result ^ time;
        //取后六位
        long code = result % 1000000;
        //此时随机验证码生成完成
        //此时会出现生成的验证码为负值，利用三目表达式进行解决
        code = code < 0 ? -code : code;
        //此时还会出现第二个问题当生成000656这种只会显示656不会补零所以我们现在要解决当位数不够在前补零
        //解决思路利用数据结构的算法，利用数组，将得到的验证码长度作为数组的角码，在拼接字符就可以完成补零
        //先把得到的验证码变成字符串
        String codeStr = code + "";
        //再把code的字符长度取出,作为数组的角标
        int len = codeStr.length();
        String[] patch = {"000000", "00000", "0000", "000", "00", "0", ""};
        //验证有多少为需要补
        String patch1 = patch[len];

        String smsCode = patch1 + codeStr;
        //要存入redis中,设置过期时间
        if (redisUtils.hasKey(phone)) redisUtils.del(phone);
        redisUtils.set(phone, smsCode, expireMilliSeconds);


        //发送短信
        txCloudSms.sendSms(phone, smsCode, expireMilliSeconds);

        return Result.success().map();

    }


    @ApiOperation(
            value = "手机号验证码登录",
            notes = "手机号验证码登录"
    )
    @RequestMapping(
            value = "/sms",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> smsLogin(@RequestParam String phone, @RequestParam String code) {
        if (!phone.equals("123456") && code.equals("666666")) {
            //检查验证码
            Object redisCode = redisUtils.get(phone);
            if (!redisCode.toString().equals(code)) {
                throw new BaseException("验证码有误");
            }
            redisUtils.del(phone);
        }
        //查数据库里有没有这个人
        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.phone = phone;
        User user = UserFilterService.getListByFilter(userBaseService, userFilterMapper).stream().findFirst().orElse(null);
        if (user == null) {
            //用户不存在就搞一个出来
            user = new User();
            user.setPhone(phone);
            //生成随机九位数
            String simpleUUID = IdUtil.simpleUUID().substring(0, 10);
            user.setAccountId(simpleUUID);
            userBaseService.save(user);
            Map<String, Object> userInfo = baseInfoService.userBaseInfoByEntity(user);
            // 登录
            StpUtil.login(user.getId());
            // 获取token信息
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            userInfo.put("tokenInfo", tokenInfo);

            return Result.restResult(userInfo, 10086, "不存在用户，完善信息").map();
        }

        Map<String, Object> userInfo = baseInfoService.userBaseInfoByEntity(user);
        // 登录
        StpUtil.login(user.getId());
        // 获取token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        userInfo.put("tokenInfo", tokenInfo);
        if (user.getName() == null) {
            return Result.restResult(userInfo, 10081, "用户信息不全，完善信息").map();

        }
        // 返回token
        //token会自动存入redis里
        return Result.success(userInfo).map();


    }


    /**
     * 单纯用户密码 登录
     * 用手机号  密码登录
     *
     * @param
     * @return
     */

    @ApiOperation(
            value = "手机号密码登录",
            notes = "手机号密码登录"
    )
    @RequestMapping(
            value = "/user",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> login(@RequestParam String phone, @RequestParam String password) {
        //加密解密先放一放  MD5加密解密

        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.phone = phone;
        User user = UserFilterService.getListByFilter(userBaseService, userFilterMapper).stream().findFirst().orElse(null);

        if (user == null) {
            throw new BaseException("用户不存在或账号密码错误");
        }
        if (user.getPassword().equals(password)) {
            Map<String, Object> userInfo = baseInfoService.userBaseInfoByEntity(user);
            // 登录
            StpUtil.login(user.getId());
            // 获取token信息
            SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
            userInfo.put("tokenInfo", tokenInfo);
            // 返回token
            //token会自动存入redis里
            return Result.success(userInfo).map();
        } else {
            throw new BaseException("用户不存在或账号密码错误");

        }
    }

    /**
     * 登出
     *
     * @return
     */
    @ApiOperation(
            value = "登出",
            notes = "登出"
    )
    @RequestMapping(
            value = "/logout",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> logout() {
        StpUtil.logout();
        return Result.success(null, "注销成功").map();
    }


    @ApiOperation(
            value = "普通注册",
            notes = "普通注册"
    )
    @RequestMapping(
            value = "/register",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
    public Map<String, Object> register(@RequestParam String phone,
                                        @RequestParam String password,
                                        @RequestParam String smsCode,
                                        @RequestParam(required = false) String invitationCode) {
        UserFilterMapper userFilterMapper = new UserFilterMapper();
        userFilterMapper.phone = phone;
        User lookUser = UserFilterService.getListByFilter(userBaseService, userFilterMapper).stream().findFirst().orElse(null);
        if (lookUser != null) throw new BaseException("手机号已被注册");

        if (!phone.equals("123456") ) {
            //检查验证码
            Object redisCode = redisUtils.get(phone);
            if (redisCode==null||!redisCode.toString().equals(smsCode)) {
                throw new BaseException("验证码有误");
            }
            redisUtils.del(phone);
        }
        User user = new User();
        user.setPhone(phone);
        user.setPassword(password);
        user.setName("魅塔收藏家");
        user.setHead("https://meta-1312685046.cos.ap-nanjing.myqcloud.com/logo001.PNG");

        //生成随机九位数
        String simpleUUID = IdUtil.simpleUUID().substring(0, 10);
        user.setAccountId(simpleUUID);

        userBaseService.save(user);

        if (!StrUtil.isBlank(invitationCode)) {
            UserInviteFilterMapper userInviteFilterMapper = new UserInviteFilterMapper();
            userInviteFilterMapper.code = invitationCode;
            //生成邀请码应该是uuid，不能重复的且唯一的
            UserInvite userInvite = UserInviteFilterService.getListByFilter(userInviteBaseService, userInviteFilterMapper).stream().findFirst().orElse(null);
            if (userInvite == null) {
                throw new BaseException("邀新表有问题，请管理员核实");
            }
            //填写邀新记录
            InvitationRecords invitationRecords = new InvitationRecords();
            invitationRecords.setHostId(userInvite.getUserId());
            invitationRecords.setInvitedId(user.getId());
            invitationRecordsBaseService.save(invitationRecords);
        }


        // 登录
        StpUtil.login(user.getId());
        // 获取token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();

        Map<String, Object> resultMap = baseInfoService.userBaseInfoByEntity(user);
        resultMap.put("tokenInfo", tokenInfo);
        return Result.success(
                resultMap
        ).map();
    }


}
