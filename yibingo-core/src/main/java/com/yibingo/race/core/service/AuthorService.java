package com.yibingo.race.core.service;

import com.yibingo.race.core.service.base.AuthorBaseService;
import com.yibingo.race.dal.entity.Author;
import com.yibingo.race.dal.putMapper.AuthorPutMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Li XingHan
 * @date 2022-07-07 9:56
 */
@Service
public class AuthorService {


    @Autowired
    private AuthorBaseService authorBaseService;

    /**
     * 查询所有的作者
     *
     * @return
     */
    public List<Map<String, Object>> selectAuthorList() {
        List<Author> authorList = authorBaseService.list();

        List<Map<String, Object>> result = new ArrayList<>();

        for (Author author : authorList) {
            Map<String, Object> data = new HashMap<>();
            data.put("authorId", author.getId());
            data.put("name", author.getName());
            data.put("head", author.getHead());
            data.put("introduce", author.getIntroduce());

            result.add(data);
        }

        return result;
    }

    /**
     * 创建新的作者
     * @param authorPutMapper
     */
    public void createAuthor(AuthorPutMapper authorPutMapper) {
        Author author = AuthorPutMapper.convertToEntity(authorPutMapper);
        authorBaseService.save(author);
    }

    /**
     * 更新现有的作者
     * @param authorPutMapper
     * @param familyId
     */
    public void updateAuthor(AuthorPutMapper authorPutMapper, String familyId) {
        Author author = AuthorPutMapper.convertToEntity(authorPutMapper);
        author.setId(familyId);
        authorBaseService.updateById(author);
    }
}
