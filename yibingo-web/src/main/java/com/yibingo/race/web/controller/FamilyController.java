package com.yibingo.race.web.controller;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.FamilyService;
import com.yibingo.race.dal.putMapper.FamilyPutMapper;
import com.yibingo.race.dal.putMapper.MetaNftPutMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Li XingHan
 * @date 2022-07-07 9:34
 */
@Api(
        value = "系列的查询、创建、更新操作",
        tags = "系列的查询、创建、更新操作"
)
@RestController
@RequestMapping("core/family/extends")
public class FamilyController {

    @Autowired
    private FamilyService familyService;


    @ApiOperation(
            value = "查询所有系列",
            notes = "查询所有系列"
    )
    @RequestMapping(
            value = "/retrieve",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> retrieveFamily() {

        return Result.success(
                familyService.selectFamilyList()
        ).map();
    }


    @ApiOperation(
            value = "创建新的系列",
            notes = "创建新的系列"
    )
    @RequestMapping(
            value = "/create",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> createFamily(@RequestBody FamilyPutMapper familyPutMapper) {

        familyService.createFamily(familyPutMapper);
        return Result.success().map();
    }


    @ApiOperation(
            value = "更新系列",
            notes = "更新系列"
    )
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> updateFamily(@RequestBody FamilyPutMapper familyPutMapper,
                                      @RequestParam String familyId) {

        familyService.updateFamily(familyPutMapper, familyId);
        return Result.success().map();
    }
}
