package com.yibingo.race.core.service;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.CharUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.crypto.SecureUtil;
import com.yibingo.race.storage.cloud.OssStorageFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @description:
 * @author: Yang Xin
 * @time: 2022/5/20 9:54
 */
@Service
public class UploadFileService {


    /*
     * 上传文件示例
     * */
    public String uploadUserFile(MultipartFile file, String userId) throws IOException {
        String path = new StringBuffer(SecureUtil.md5(String.valueOf(userId)))
                .append(CharUtil.SLASH)
                .append(IdUtil.simpleUUID())
                .append(CharUtil.DOT)
                .append(FileUtil.extName(file.getOriginalFilename())).toString();
        String url = OssStorageFactory.build().upload(file, path);
        return url;
    }


}
