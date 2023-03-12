package com.yibingo.race.core.service;

import com.yibingo.race.core.service.base.FamilyBaseService;
import com.yibingo.race.dal.entity.Family;
import com.yibingo.race.dal.putMapper.FamilyPutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li XingHan
 * @date 2022-07-07 9:24
 */
@Service
public class FamilyService {

    @Autowired
    private FamilyBaseService familyBaseService;


    /**
     * 查询所有系列
     *
     * @return
     */
    public List<Map<String, Object>> selectFamilyList() {
        List<Family> familyList = familyBaseService.list();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Family family : familyList) {
            Map<String, Object> data = new HashMap<>();
            data.put("familyId", family.getId());
            data.put("title", family.getTitle());
            data.put("introduction", family.getIntroduction());
            data.put("url", family.getUrl());
            data.put("contractAddress", family.getContractAddress());
            data.put("contractAgreement", family.getContractAgreement());

            result.add(data);
        }

        return result;
    }


    /**
     * 创建新的系列
     *
     * @param familyPutMapper
     */
    public void createFamily(FamilyPutMapper familyPutMapper) {
        //TODO 系列上链
        Family family = FamilyPutMapper.convertToEntity(familyPutMapper);
        familyBaseService.save(family);
    }

    /**
     * 更新现有的系列
     *
     * @param familyPutMapper
     * @param familyId
     */
    public void updateFamily(FamilyPutMapper familyPutMapper, String familyId) {
        Family family = FamilyPutMapper.convertToEntity(familyPutMapper);
        family.setId(familyId);
        familyBaseService.updateById(family);
    }

}
