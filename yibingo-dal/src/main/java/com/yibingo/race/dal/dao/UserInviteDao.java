package com.yibingo.race.dal.dao;

import com.yibingo.race.dal.entity.UserInvite;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户邀请码
 * 
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-07 15:51:22
 */
@Mapper
public interface UserInviteDao extends BaseMapper<UserInvite> {
	
}
