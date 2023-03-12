package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.MetaNftOperationService;
import com.yibingo.race.dal.putMapper.MetaNftPutMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Li XingHan
 * @description 藏品的创建、更新操作
 * @date 2022-06-27 14:14
 */
@Api(
        value = "藏品的创建、更新操作",
        tags = "藏品的创建、更新操作"
)
@RestController
@RequestMapping("core/metaNft")
public class MetaNftOperationController {

    @Autowired
    private MetaNftOperationService metaNftOperationService;


    @ApiOperation(
            value = "创建新的藏品",
            notes = "创建新的藏品"
    )
    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> create(@RequestBody MetaNftPutMapper metaNftPutMapper,
                                      @RequestParam("totalCount") Integer totalCount,
                                      @RequestParam("reserveCount") Integer reserveCount) {

        metaNftOperationService.createNewMetaNft(metaNftPutMapper, totalCount, reserveCount);
        return Result.success().map();
    }


    @ApiOperation(
            value = "更新藏品信息",
            notes = "更新藏品信息"
    )
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> update(@RequestBody MetaNftPutMapper metaNftPutMapper,
                                      @RequestParam("metaNftId") String metaNftId) {

        metaNftOperationService.updateMetaNft(metaNftPutMapper, metaNftId);
        return Result.success().map();
    }


    @ApiOperation(
            value = "根据系列名 查询",
            notes = "根据系列名 查询"
    )
    @RequestMapping(
            value = "/family/search",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> searchFamily(@RequestParam String familyName,
                                            @RequestParam Integer nftType) {
        return Result.success(

                metaNftOperationService.sarchFamilyName(familyName, nftType)
        ).map();


    }

}
