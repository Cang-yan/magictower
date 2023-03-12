package com.yibingo.race.dal.dao;

import com.yibingo.race.dal.entity.BenefitUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户获得的权益表
 * 
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-10 14:59:32
 */
@Mapper
public interface BenefitUserDao extends BaseMapper<BenefitUser> {
	
}
