package com.education.uv.Utile;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;


public class QiNiuManager {

    static String ImageUrl = "http://pxjos3z9b.bkt.clouddn.com/";
    static String iconUrl = "http://pxlywreg5.bkt.clouddn.com/";

    public String updateImage (MultipartFile file, String bucketName){

        //构造一个带指定Region对象的配置类
        Configuration cfg = new Configuration(Region.region1());
//...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);

        //...生成上传凭证，然后准备上传
        String accessKey = "Wg2dDNZi_RY9D0dbCCf5rpxVruOL2-1J4EJ5rv_X";
        String secretKey = "xopuiCqEDP6aefj00wq5WKx3fO18QBwrlhBbEvpC";
        String bucket = bucketName;
//默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        //最终url
        String url = null;
        try {
            byte[] uploadBytes = file.getBytes();
            ByteArrayInputStream byteInputStream = new ByteArrayInputStream(uploadBytes);
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);

            try {
                Response response = uploadManager.put(byteInputStream,key,upToken,null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                if (bucketName.equals("uv-icon")) {
                    url = iconUrl + putRet.key;
                } else {
                    url = ImageUrl + putRet.key;
                }
                System.out.println(url);

                return url;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (UnsupportedEncodingException ex) {
            //ignore
        } catch (IOException e) {
            e.printStackTrace();
        }
        return url;
    }

}
