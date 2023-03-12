package com.yibingo.race.web.controller.base;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import com.yibingo.race.dal.entity.Stock;
import com.yibingo.race.core.service.base.StockBaseService;
import com.yibingo.race.dal.filterMapper.StockFilterMapper;
import com.yibingo.race.dal.putMapper.StockPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 库存
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "stock",
        tags = "库存"
)
@RestController
@RequestMapping("core/stock")
public class StockBaseController {
    @Autowired
    private StockBaseService stockBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "库存查询",
            notes = "库存查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:stock:info")
    public Map<String,Object> info(@RequestParam String id) {
            Stock stock = stockBaseService.getById(id);

        return Result.success(stock).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "库存保存",
            notes = "库存保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:stock:save")
    public Map<String,Object> save(@RequestBody StockPutMapper stockPutMapper){
        stockBaseService.save(StockPutMapper.convertToEntity(stockPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "库存修改",
            notes = "库存修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:stock:update")
    public Map<String,Object> update(@RequestBody Stock stock){
        stockBaseService.updateById(stock);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "库存批量删除",
            notes = "库存批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:stock:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        stockBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
