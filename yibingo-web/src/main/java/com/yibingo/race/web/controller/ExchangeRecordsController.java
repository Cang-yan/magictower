package com.yibingo.race.web.controller;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.UserNftRecordsService;
import com.yibingo.race.dal.putMapper.ExchangeRecordsPutMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(
        value = "藏品交易记录控制",
        tags = "藏品交易记录控制"
)
@RestController
@RequestMapping("core/exchangeRecords")
public class ExchangeRecordsController {
    @Autowired
    private UserNftRecordsService userNftRecordsService;

    //这或许有点问题
    @ApiOperation(
            value = "转赠商品",
            notes = "转赠商品"
    )
    @RequestMapping(
            value = "/reGifted",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> reGifted(@RequestBody ExchangeRecordsPutMapper exchangeRecordsPutMapper) {
        userNftRecordsService.addExchangeRecordsPutMapper(exchangeRecordsPutMapper);
        return Result.success().map();
    }
}
