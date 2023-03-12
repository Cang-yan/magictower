package com.yibingo.race.core.service;

import cn.hutool.core.util.IdUtil;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.common.utils.SecurityUtil;
import com.yibingo.race.core.service.base.UserBaseService;
import com.yibingo.race.core.service.base.UserFilterService;
import com.yibingo.race.dal.entity.User;
import com.yibingo.race.dal.filterMapper.UserFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/21 19:09
 */
@Service
public class UserInfoService {

    @Autowired
    private UserBaseService userBaseService;

    private static String islDCard18 = "^([1-6][1-9]|50)\\d{4}(18|19|20)\\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\\d{3}[0-9Xx]$";
    private static String islDCard15 = "^([1-6][1-9]|50)\\d{4}\\d{2}((0[1-9])|10|11|12)(([0-2][1-9])|10|20|30|31)\\d{3}$";

    public User realNameAuth(String userId, String identity, String realName) {

        if (identity.matches(islDCard18) || identity.matches(islDCard15)) {
            SecurityUtil securityUtil = new SecurityUtil();
            String encryption = securityUtil.encrypt(identity);

            UserFilterMapper userFilterMapper = new UserFilterMapper();
            userFilterMapper.identity = encryption;

            List<User> userList = UserFilterService.getListByFilter(userBaseService, userFilterMapper);
            if (!userList.isEmpty()) throw new BaseException("该身份证号已被注册，请不要重复注册");

            User user = userBaseService.getById(userId);
            user.setIdentity(encryption);
            user.setRealName(realName);
            //四十八位的用户钱包
            user.setChainwallet("0x" + IdUtil.objectId() + IdUtil.objectId());
            userBaseService.updateById(user);
            return user;
        } else {
            throw new BaseException("认证失败");
        }
    }


}
