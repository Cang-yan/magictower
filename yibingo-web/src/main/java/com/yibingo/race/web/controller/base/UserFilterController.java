package com.yibingo.race.web.controller.base;

import com.yibingo.race.common.utils.Result;
import com.yibingo.race.core.service.base.UserBaseService;
import com.yibingo.race.core.service.base.UserFilterService;
import com.yibingo.race.dal.entity.User;
import com.yibingo.race.dal.filterMapper.UserFilterMapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 用户表扩展
 *
 * @author Yang Xin
 * @email little-cy@qq.com
 * @date 2022-06-25 14:52:41
 */
@Api(
        value = "user_extends",
        tags = "用户表扩展管理"
)
@RestController
@RequestMapping("core/user/extends")
public class UserFilterController {

    @Autowired
    private UserBaseService userBaseService;

    //需要的话再打开即可
    @ApiOperation(
            value = "搜索条件查询",
            notes = "搜索条件查询"
    )
    @RequestMapping(
            value = "/search",
            method = RequestMethod.GET
    )
    @ResponseBody
    public Map<String, Object> getListByFilter(@RequestParam(value = "updateTimeFrom", required = false) Long updateTimeFrom,
                                               @RequestParam(value = "updateTimeTo", required = false) Long updateTimeTo,
                                               @RequestParam(value = "createTimeFrom", required = false) Long createTimeFrom,
                                               @RequestParam(value = "createTimeTo", required = false) Long createTimeTo,
                                               @RequestParam(value = "page", required = false, defaultValue = "1") Long page,
                                               @RequestParam(value = "row", required = false, defaultValue = "10") Long row,
                                               @RequestParam(value = "orderBy", required = false) List<String> orderBy,
                                               @RequestParam(value = "accountId", required = false) String accountId,
                                               @RequestParam Integer count
    ) {
        UserFilterMapper mapper = new UserFilterMapper();
        mapper.updateTimeFrom = updateTimeFrom;
        mapper.updateTimeTo = updateTimeTo;
        mapper.createTimeFrom = createTimeFrom;
        mapper.createTimeTo = createTimeTo;
        mapper.page = page;
        mapper.row = row;
        mapper.orderBy = orderBy;
        mapper.accountId = accountId;
        List<User> userList = UserFilterService.getListByFilter(userBaseService, mapper);
        if (count < userList.size()) userList = userList.subList(0, count);
        return Result.success(
                userList
        ).map();
    }


}