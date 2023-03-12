package com.yibingo.race.dal.dao;

import com.yibingo.race.dal.entity.Stock;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 库存
 * 
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-07 15:51:23
 */
@Mapper
public interface StockDao extends BaseMapper<Stock> {
	
}
