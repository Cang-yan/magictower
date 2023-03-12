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

import com.yibingo.race.dal.entity.Author;
import com.yibingo.race.core.service.base.AuthorBaseService;
import com.yibingo.race.dal.filterMapper.AuthorFilterMapper;
import com.yibingo.race.dal.putMapper.AuthorPutMapper;
import com.yibingo.race.common.utils.Result;



/**
 * 作品作者
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-07-22 18:08:09
 */
@Api(
        value = "author",
        tags = "作品作者"
)
@RestController
@RequestMapping("core/author")
public class AuthorBaseController {
    @Autowired
    private AuthorBaseService authorBaseService;


    //基础增删改查基本没啥用，需要的话再打开即可

    /* @ApiOperation(
            value = "作品作者查询",
            notes = "作品作者查询"
    )*/
    @RequestMapping(
            value = "/info",
            method = RequestMethod.GET
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:author:info")
    public Map<String,Object> info(@RequestParam String id) {
            Author author = authorBaseService.getById(id);

        return Result.success(author).map();
    }

    /**
     * 保存
     */
    /* @ApiOperation(
            value = "作品作者保存",
            notes = "作品作者保存"
    )*/
    @RequestMapping(
            value = "/save",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:author:save")
    public Map<String,Object> save(@RequestBody AuthorPutMapper authorPutMapper){
        authorBaseService.save(AuthorPutMapper.convertToEntity(authorPutMapper));

        return Result.success().map();
    }

    /**
     * 修改
     */
    /* @ApiOperation(
            value = "作品作者修改",
            notes = "作品作者修改"
    )*/
    @RequestMapping(
            value = "/update",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:author:update")
    public Map<String,Object> update(@RequestBody Author author){
        authorBaseService.updateById(author);

        return Result.success().map();
    }

    /**
     * 删除
     */

    /* @ApiOperation(
            value = "作品作者批量删除",
            notes = "作品作者批量删除"
    )*/
    @RequestMapping(
            value = "/delete",
            method = RequestMethod.POST
    )
    @Transactional(
            rollbackFor = Exception.class
    )
    @ResponseBody
//    @RequiresPermissions("core:author:delete")
    public Map<String,Object> delete(@RequestBody List<String> ids){
        authorBaseService.removeByIds(ids);

        return Result.success().map();
    }




}
