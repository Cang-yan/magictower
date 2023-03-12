package com.yibingo.race.web.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.BlindBoxOperationService;
import com.yibingo.race.core.service.base.BoxMetaBaseService;
import com.yibingo.race.core.service.base.StockBaseService;
import com.yibingo.race.dal.entity.BoxMeta;
import com.yibingo.race.dal.entity.Stock;
import com.yibingo.race.dal.putMapper.BlindBoxPoolPutMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Api(
        value = "盲盒控制" ,
        tags = "盲盒控制"
)
@RestController
@RequestMapping("core/blindBox" )
public class BlindBoxController {
    @Autowired
    BlindBoxOperationService blindBoxOperationService;
    @Autowired
    private BoxMetaBaseService boxMetaBaseService;
    @Autowired
    private StockBaseService stockBaseService;

    @ApiOperation(
            value = "开启盲盒" ,
            notes = "开启盲盒"
    )
    @RequestMapping(
            value = "/open" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    @SaCheckLogin
    public Map<String, Object> reGifted(@RequestParam String blindBoxId) {
        String userId = StpUtil.getLoginIdAsString();
        blindBoxOperationService.openBlindBox(blindBoxId, userId);
        return Result.success().map();
    }

    @ApiOperation(
            value = "创建盲盒" ,
            notes = "创建盲盒"
    )
    @RequestMapping(
            value = "/create" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> createBox(@RequestBody BlindBoxPoolPutMapper blindBoxPoolPutMapper,
                                         @RequestParam Integer totalCount,
                                         @RequestParam Integer reverseCount) {

        blindBoxOperationService.createBlindBox(blindBoxPoolPutMapper, totalCount, reverseCount);
        return Result.success().map();
    }

    @ApiOperation(
            value = "绑定盲盒藏品 盲盒里藏品不做预留" ,
            notes = "绑定盲盒藏品 盲盒里藏品不做预留"
    )
    @RequestMapping(
            value = "/bind" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> bindBoxMeta(@RequestBody JSONArray jsonArray) {
        List<BoxMeta> boxMetaList = new ArrayList<>();
        for (int i = 0; i < jsonArray.size(); i++) {
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            BoxMeta boxMeta = new BoxMeta();
            boxMeta.setBlindBoxId(jsonObject.getStr("blindBoxPoolId" ));
            boxMeta.setMetaNftId(jsonObject.getStr("metaNftId" ));
            boxMeta.setMetaNftPrice(Double.parseDouble(jsonObject.getStr("metaNftPrice" )));
            boxMeta.setRankName(jsonObject.getStr("rankName" ));
            boxMeta.setRankPossibility(Double.parseDouble(jsonObject.getStr("rankPossibility" )));
            boxMeta.setRealPossibility(Double.parseDouble(jsonObject.getStr("realPossibility" )));
            Integer preCount = Integer.parseInt(jsonObject.getStr("stockPreCount" ));
            Stock stock = new Stock();
            stock.setPreCount(preCount);
            stock.setNowCount(preCount);
            stock.setReserveCount(0);
            stockBaseService.save(stock);
            boxMeta.setStockId(stock.getId());
            boxMetaList.add(boxMeta);
        }
            //计算一下藏品总数和盲盒总数  不合理的要回退


        boxMetaBaseService.saveBatch(boxMetaList);
        return Result.success().map();

    }

}
