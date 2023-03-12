package com.yibingo.race.core.service;

import com.baidu.xasset.auth.XchainAccount;
import com.baidu.xasset.client.base.BaseDef.*;
import com.baidu.xasset.client.xasset.Asset;
import com.baidu.xasset.client.xasset.XassetDef.*;
import com.baidu.xasset.common.config.Config.*;
import com.baidu.xuper.api.Account;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class XuperPublishService {
    private static String testIP = "http://120.48.16.137:8360";
    private static String realIP = "https://xuper.baidu.com";
    public static String testPublish()
    {
        XassetCliConfig cfg = new XassetCliConfig();
        cfg.setCredentials(110560, DigestUtils.md5Hex("e5040b48bdde4839a682ba706e76f372"), DigestUtils.md5Hex(
                "67b24bc2bae4278d57c3dd91f4fa8d9d"));
        cfg.setEndPoint(testIP);
        Account acc = XchainAccount.newXchainEcdsaAccount(XchainAccount.mnemStrgthStrong, XchainAccount.mnemLangEN);
        Asset handle = new Asset(cfg, Logger.getGlobal());
        Resp<GetStokenResp> StokenResult = handle.getStoken(acc);
        byte[] bytes = new byte[0];

        UploadFile testFile = handle.uploadFile(acc,"test.png", "/Users/urningb/Downloads/d043ad4bd11373f06012bdc039f6c3f2faed0447.png",bytes, null);
        String[] link = {testFile.link};
        AssetInfo assetInfo = new AssetInfo(1,"testFile", link, "短描述", link, link,"长描述","json描述", 1);

        Resp<CreateAssetResp>CreateResult = handle.createAsset(acc, 1, assetInfo, 1,1);
        Resp<BaseResp>PublishAssetResult = handle.publishAsset(acc, CreateResult.apiResp.assetId, 1);

        Resp<QueryAssetResp>QueryAssetResult = handle.queryAsset(CreateResult.apiResp.assetId);
        while (QueryAssetResult.apiResp.meta.status != 4)
        {
            QueryAssetResult = handle.queryAsset(CreateResult.apiResp.assetId);
        }
        //System.out.println(QueryAssetResult.apiResp.meta.txId);
        return QueryAssetResult.apiResp.meta.txId;
    }
    public Resp<QueryAssetResp> publishtest(String fileName, String filePath, String assetTitle, String assetShortDesc, String assetLongDesc, String assetExt)
    {
        XassetCliConfig cfg = new XassetCliConfig();
        cfg.setCredentials(110560, DigestUtils.md5Hex("e5040b48bdde4839a682ba706e76f372"), DigestUtils.md5Hex(
                "67b24bc2bae4278d57c3dd91f4fa8d9d"));
        cfg.setEndPoint(testIP);
        Account acc = XchainAccount.newXchainEcdsaAccount(XchainAccount.mnemStrgthStrong, XchainAccount.mnemLangEN);
        Asset handle = new Asset(cfg, Logger.getGlobal());
        Resp<GetStokenResp> StokenResult = handle.getStoken(acc);
        byte[] bytes = new byte[0];

        UploadFile testFile = handle.uploadFile(acc,fileName, filePath,bytes, null);
        String[] link = {testFile.link};
        AssetInfo assetInfo = new AssetInfo(1,assetTitle, link, assetShortDesc, link, link,assetLongDesc,assetExt, 1);

        Resp<CreateAssetResp>CreateResult = handle.createAsset(acc, 1, assetInfo, 1,1);
        Resp<BaseResp>PublishAssetResult = handle.publishAsset(acc, CreateResult.apiResp.assetId, 1);

        Resp<QueryAssetResp>QueryAssetResult = handle.queryAsset(CreateResult.apiResp.assetId);
        while (QueryAssetResult.apiResp.meta.status != 4)
        {
            QueryAssetResult = handle.queryAsset(CreateResult.apiResp.assetId);
        }
        //System.out.println(QueryAssetResult.apiResp.meta.txId);
        return QueryAssetResult;
    }

    public Resp<QueryAssetResp> publish(String fileName, String filePath, String assetTitle, String assetShortDesc, String assetLongDesc, String assetExt)
    {
        XassetCliConfig cfg = new XassetCliConfig();
        cfg.setCredentials(110560, DigestUtils.md5Hex("e5040b48bdde4839a682ba706e76f372"), DigestUtils.md5Hex(
                "67b24bc2bae4278d57c3dd91f4fa8d9d"));
        cfg.setEndPoint(realIP);
        Account acc = XchainAccount.newXchainEcdsaAccount(XchainAccount.mnemStrgthStrong, XchainAccount.mnemLangEN);
        Asset handle = new Asset(cfg, Logger.getGlobal());
        Resp<GetStokenResp> StokenResult = handle.getStoken(acc);
        byte[] bytes = new byte[0];

        UploadFile testFile = handle.uploadFile(acc,fileName, filePath,bytes, null);
        String[] link = {testFile.link};
        AssetInfo assetInfo = new AssetInfo(1,assetTitle, link, assetShortDesc, link, link,assetLongDesc,assetExt, 1);

        Resp<CreateAssetResp>CreateResult = handle.createAsset(acc, 1, assetInfo, 1,1);
        Resp<BaseResp>PublishAssetResult = handle.publishAsset(acc, CreateResult.apiResp.assetId, 1);

        Resp<QueryAssetResp>QueryAssetResult = handle.queryAsset(CreateResult.apiResp.assetId);
        while (QueryAssetResult.apiResp.meta.status != 4)
        {
            QueryAssetResult = handle.queryAsset(CreateResult.apiResp.assetId);
        }
        //System.out.println(QueryAssetResult.apiResp.meta.txId);
        return QueryAssetResult;
    }
}
