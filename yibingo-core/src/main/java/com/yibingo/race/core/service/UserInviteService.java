package com.yibingo.race.core.service;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.IdUtil;
import com.yibingo.race.common.exception.BaseException;
import com.yibingo.race.core.service.base.*;
import com.yibingo.race.dal.entity.InvatitionRank;
import com.yibingo.race.dal.entity.InvitationRecords;
import com.yibingo.race.dal.entity.User;
import com.yibingo.race.dal.entity.UserInvite;
import com.yibingo.race.dal.filterMapper.InvatitionRankFilterMapper;
import com.yibingo.race.dal.filterMapper.InvitationRecordsFilterMapper;
import com.yibingo.race.dal.filterMapper.UserInviteFilterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/6/28 15:54
 */
@Service
public class UserInviteService {

    @Autowired
    private UserBaseService userBaseService;

    @Autowired
    private BaseInfoService baseInfoService;

    @Autowired
    private UserInviteBaseService userInviteBaseService;

    @Autowired
    private InvatitionRankBaseService invatitionRankBaseService;

    @Autowired
    private InvitationRecordsBaseService invitationRecordsBaseService;

    /**
     * 生成邀请码
     *
     * @param userId
     * @return
     */
    public String getInviteCode(String userId) {


        UserInviteFilterMapper userInviteFilterMapper = new UserInviteFilterMapper();
        userInviteFilterMapper.userId = userId;
        UserInvite userInvite = UserInviteFilterService.getListByFilter(userInviteBaseService, userInviteFilterMapper).stream().findFirst().orElse(null);
        if (userInvite == null) {
            userInvite = new UserInvite();
            userInvite.setCode(IdUtil.randomUUID().substring(0,6).toUpperCase());
            userInvite.setUserId(userId);
            userInviteBaseService.save(userInvite);
        }

        return userInvite.getCode();
    }


    //邀新排行榜 这里有问题  排行榜本身的更新时间都是一样的
    //所以是要去邀新记录里查，先到达这个人数的人排在前面
    public List<Map<String, Object>> rankInvitation(String userId) {
        List<Map<String, Object>> rankDataMapList = new ArrayList<>();
        List<String> orderBy = new ArrayList<>();
        orderBy.add("count desc");
        InvatitionRankFilterMapper invatitionRankFilterMapper = new InvatitionRankFilterMapper();
        invatitionRankFilterMapper.orderBy = orderBy;
        List<InvatitionRank> invatitionRankList = InvatitionRankFilterService.getListByFilter(invatitionRankBaseService, invatitionRankFilterMapper);
        //随便取一个 问题不大

        if (!invatitionRankList.isEmpty()) {

            Date lastUpdateTime = invatitionRankList.get(0).getUpdateTime();
            List<String> userIdList = invatitionRankList.stream().map(InvatitionRank::getUserId).collect(Collectors.toList());
            List<User> userList = userBaseService.listByIds(userIdList);

            for (InvatitionRank invatitionRank:collectElement(invatitionRankList,lastUpdateTime)){

                User user = userList.stream().filter(
                        item-> item.getId()!=null && item.getId().equals(invatitionRank.getUserId())
                ).findFirst().orElse(null);
                if(user==null) throw new BaseException("邀请排行榜有误，请核实");
                Map<String,Object> resultMap = baseInfoService.userBaseInfoByEntity(user);
                resultMap.put("timeStamp",invatitionRank.getUpdateTime());
                resultMap.put("inviteCount",invatitionRank.getCount());
                //加一个字段，验证这个是不是本人
                resultMap.put("isMe",0);
                if (user.getId().equals(userId)) resultMap.replace("isMe",1);
                rankDataMapList.add(resultMap);
            }
        }
        return rankDataMapList;
    }

    /**
     * 基本逻辑是，如果n个人的邀请人数相同，那么就去查他们在邀请记录里的createTime并比较，越小的越在前面
     * 入参是直接查到的原始的排名
     * @param invatitionRankList
     * @return 按照一样分数的具体要求（先达到的排前面）细化排序后的排名
     */
    public List<InvatitionRank> collectElement(List<InvatitionRank> invatitionRankList,Date lastUpdateTime) {
        List<InvatitionRank> endList = new ArrayList<>();
        int j = 0;
        //从invatitionRankList第一个开始细化排序，之后放在endList中
        for (int i = 0; i < invatitionRankList.size(); i++) {
            InvatitionRank invatitionRank = invatitionRankList.get(i);
            List<InvatitionRank> tempList = new ArrayList<>();
            //InvatitionRank的表前面判空了，所以这里的templist不可能是空
            tempList.add(invatitionRank);
            //取人数相等的人
            for (j = i + 1; j < invatitionRankList.size(); j++) {
                if (invatitionRankList.get(j).getCount().equals(invatitionRank.getCount())) {
                    tempList.add(invatitionRankList.get(j));
                } else {
                    i = j - 1;//为了从当前不相等的地方重新进入循环，得到另一组同分的
                    break;
                }
            }
            //到这就拿到了所有同分的元素，对同分元素进行细化排序
            simpleSort(tempList, endList,lastUpdateTime);
            if (j == invatitionRankList.size()) break;
        }
        return endList;
    }



    /**
     * 对temp进行排序以后，放在endlilst后面,
     * @param tempList
     * @param entityList
     * @return
     */
    public List<InvatitionRank> simpleSort(List<InvatitionRank> tempList, List<InvatitionRank> entityList,Date lastUpdateTime) {
        //冒泡排序，比较同分用户，最后一个邀请时间早的排在前面
        for (int j = 0; j < tempList.size() - 1; j++) {
            for (int i = 0; i < tempList.size() - 1 - j; i++) {
                if (getGmtCreateAcrossData(tempList.get(i),lastUpdateTime).after(getGmtCreateAcrossData(tempList.get(i + 1),lastUpdateTime))) {
                    InvatitionRank temp = tempList.get(i);
                    tempList.set(i, tempList.get(i + 1));
                    tempList.set(i + 1, temp);

                }
            }
        }
        entityList.addAll(tempList);
        return entityList;
    }

    //得出该用户最后邀请人的时间
    public Date getGmtCreateAcrossData(InvatitionRank invatitionRank,Date lastUpdateTime) {
        InvitationRecordsFilterMapper invitationRecordsFilterMapper = new InvitationRecordsFilterMapper();
        invitationRecordsFilterMapper.hostId = invatitionRank.getUserId();
        //在榜单更新点之前的记录，排名的时间先后才有意义
        invitationRecordsFilterMapper.createTimeTo= lastUpdateTime.getTime();
        List<InvitationRecords> invitationRecordsList = InvitationRecordsFilterService.getListByFilter(invitationRecordsBaseService,invitationRecordsFilterMapper);
        //如果pointlist为空的话，表示这个人没有搞邀新，给他的排名排在最后去
        if (invitationRecordsList.isEmpty()) return new Date();
        return invitationRecordsList.get(0).getCreateTime();
    }

}
