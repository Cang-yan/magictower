package com.yibingo.race.dal.dao;

import com.yibingo.race.dal.entity.RankSort;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 品质分级,默认概率写进枚举
 * 
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-07 15:51:23
 */
@Mapper
public interface RankSortDao extends BaseMapper<RankSort> {
	
}
