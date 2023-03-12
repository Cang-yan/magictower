package com.yibingo.race.web.config;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/7/20 17:40
 */

import cn.dev33.satoken.stp.StpInterface;
import cn.hutool.core.util.StrUtil;
import com.yibingo.race.core.service.base.UserBaseService;
import com.yibingo.race.dal.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义权限验证接口扩展
 */
@Component    // 保证此类被SpringBoot扫描，完成Sa-Token的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private UserBaseService userBaseService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        // 本list仅做模拟，实际项目中要根据具体业务逻辑来查询权限
        List<String> list = new ArrayList<String>();
        list.add("101" );
        list.add("user-add" );
        list.add("user-delete" );
        list.add("user-update" );
        list.add("user-get" );
        list.add("article-get" );
        return list;
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        List<String> list = new ArrayList<>();

        User user = userBaseService.getById(loginId.toString());

        if (StrUtil.isBlank(user.getIdentity())) list.add("real");
        return list;
    }

}

