package com.yibingo.race.dal.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yibingo.race.dal.entity.Market;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

/**
 * 商城
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-07 15:51:23
 */
@Mapper
public interface MarketDao extends BaseMapper<Market> {


    //stock_count > 0 和 版本号实现乐观锁 防止超卖
    @Update("update market set now_count = now_count - #{count}, version= version + 1 where id = #{id} and now_count - #{count} >= 0 and version = #{version}")
    public int reduceStockByVersion(@Param("count") Integer count, @Param("version")Integer version, @Param("id") String id);


}
