package com.yibingo.race.web.controller;

import com.baidu.xasset.client.base.BaseDef.*;
import com.baidu.xasset.client.xasset.XassetDef;
import com.yibingo.race.core.service.XuperPublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Api(
        value = "XuperAsset控制",
        tags = "XuperAsset控制"
)
@RestController
@RequestMapping("core/XuperAsset")
public class XuperAssetController {

    @Autowired
    private XuperPublishService xuperPublishService;

    @ApiOperation(
            value = "藏品上链测试接口",
            notes = "藏品上链测试接口"
    )
    @RequestMapping(
            value = "publishTest",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Resp<XassetDef.QueryAssetResp> publishTest(
            @RequestParam(value = "fileName") String fileName, //文件名
            @RequestParam(value = "filePath") String filePath,  //文件路径
            @RequestParam(value = "assetTitle") String assetTitle, //藏品标题
            @RequestParam(value = "assetShortDesc") String assetShortDesc,//藏品短描述
            @RequestParam(value = "assetLongDesc") String assetLongDesc, //藏品长描述
            @RequestParam(value = "assetExt") String assetExt  //藏品额外描述  json类型    比如标签一类的东西
    )
    {
        return xuperPublishService.publishtest(fileName,filePath,assetTitle,assetShortDesc,assetLongDesc,assetExt);
    }


    @ApiOperation(
            value = "藏品上链测试正式（收费）",
            notes = "藏品上链测试正式（收费）"
    )
    @RequestMapping(
            value = "publish",
            method = RequestMethod.POST
    )
    @ResponseBody
    public Resp<XassetDef.QueryAssetResp> publish(
            @RequestParam(value = "fileName") String fileName,
            @RequestParam(value = "filePath") String filePath,
            @RequestParam(value = "assetTitle") String assetTitle,
            @RequestParam(value = "assetShortDesc") String assetShortDesc,
            @RequestParam(value = "assetLongDesc") String assetLongDesc,
            @RequestParam(value = "assetExt") String assetExt
    )
    {
        return xuperPublishService.publish(fileName,filePath,assetTitle,assetShortDesc,assetLongDesc,assetExt);
    }
}
