package com.yibingo.race.dal.dao;

import com.yibingo.race.dal.entity.Lottery;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 抽奖;
 * 
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-08-31 15:14:56
 */
@Mapper
public interface LotteryDao extends BaseMapper<Lottery> {
	
}
