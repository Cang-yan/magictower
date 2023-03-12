package com.yibingo.race.web.controller;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.RankSortService;
import com.yibingo.race.dal.entity.RankSort;
import com.yibingo.race.dal.putMapper.RankSortPutMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li XingHan
 * @date 2022-07-04 10:37
 */
@Api(
        value = "品质分级查询和创建",
        tags = "品质分级查询和创建"
)
@RestController
@RequestMapping("core/rankSort")
public class RankSortController {

    @Autowired
    private RankSortService rankSortService;


    @ApiOperation(
            value = "查询品质",
            notes = "查询品质"
    )
    @RequestMapping(
            value = "/retrieve",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> retrieve() {

        Map<String, Object> result = new HashMap<>();
        int countRankSort = rankSortService.countRankSort();
        List<Map<String, Object>> rankSortList = rankSortService.selectRankSortList();
        result.put("rankSortList", rankSortList);
        result.put("count", countRankSort);

        return Result.success(
                result
        ).map();
    }


    @ApiOperation(
            value = "创建品质",
            notes = "创建品质"
    )
    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> create(@RequestBody RankSortPutMapper rankSortPutMapper) {
        rankSortService.createRankSort(rankSortPutMapper);
        return Result.success().map();
    }

    @ApiOperation(
            value = "更新品质",
            notes = "更新品质"
    )
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> update(@RequestBody List<Map<String, Object>> rankSortList) {
        rankSortService.updateRankSort(rankSortList);
        return Result.success().map();
    }


}
