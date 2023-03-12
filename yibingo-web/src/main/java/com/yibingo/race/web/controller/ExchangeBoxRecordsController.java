package com.yibingo.race.web.controller;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.UserNftRecordsService;
import com.yibingo.race.dal.putMapper.ExchangeBoxRecordsPutMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Api(
        value = "盲盒交易记录控制",
        tags = "盲盒交易记录控制"
)
@RestController
@RequestMapping("core/exchangeBoxRecords")
public class ExchangeBoxRecordsController {
    @Autowired
    private UserNftRecordsService userNftRecordsService;

    @ApiOperation(
            value = "转赠盲盒",
            notes = "转赠盲盒"
    )
    @RequestMapping(
            value = "/reGifted",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> reGifted(@RequestBody ExchangeBoxRecordsPutMapper exchangeBoxRecordsPutMapper) {
        userNftRecordsService.addExchangeBoxRecordsPutMapper(exchangeBoxRecordsPutMapper);
        return Result.success().map();
    }
}

