package com.yibingo.race.web.controller;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.AuthorService;
import com.yibingo.race.dal.putMapper.AuthorPutMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author Li XingHan
 * @date 2022-07-07 11:15
 */
@Api(
        value = "作者的查询、创建、更新操作" ,
        tags = "作者的查询、创建、更新操作"
)
@RestController
@RequestMapping("core/author/extends" )
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    @ApiOperation(
            value = "查询所有作者" ,
            notes = "查询所有作者"
    )
    @RequestMapping(
            value = "/retrieve" ,
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> retrieveAuthor() {

        return Result.success(
                authorService.selectAuthorList()
        ).map();
    }


    @ApiOperation(
            value = "创建新的作者" ,
            notes = "创建新的作者"
    )
    @RequestMapping(
            value = "/create" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> createAuthor(@RequestBody AuthorPutMapper authorPutMapper) {

        authorService.createAuthor(authorPutMapper);
        return Result.success().map();
    }


    @ApiOperation(
            value = "更新作者" ,
            notes = "更新作者"
    )
    @RequestMapping(
            value = "/update" ,
            method = RequestMethod.POST
    )
    @ResponseBody
    public Map<String, Object> updateAuthor(@RequestBody AuthorPutMapper authorPutMapper,
                                            @RequestParam String authorId) {

        authorService.updateAuthor(authorPutMapper, authorId);
        return Result.success().map();
    }


}
