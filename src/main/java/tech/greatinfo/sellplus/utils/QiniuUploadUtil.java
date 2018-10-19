package tech.greatinfo.sellplus.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.common.Zone;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;
import com.qiniu.util.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Component
public class QiniuUploadUtil {

    private static String accessKey;
    private static String secretKey;
    private static String bucket;
    private static String qinuiDomain;

    @Value("${accessKey}")
    public void setAccessKey(String accessKey) {
        QiniuUploadUtil.accessKey = accessKey;
    }

    @Value("${secretKey}")
    public void setSecretKey(String secretKey) {
        QiniuUploadUtil.secretKey = secretKey;
    }

    @Value("${bucket}")
    public void setBucket(String bucket) {
        QiniuUploadUtil.bucket = bucket;
    }

    @Value("${qiniuDomain}")
    public void setQinuiDomain(String qinuiDomain) {
        QiniuUploadUtil.qinuiDomain = qinuiDomain;
    }

    public static String uploadBase64(String base64) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            String key = UUID.randomUUID().toString();
            Response response = uploadManager.put(base64.getBytes(), key, upToken);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //System.out.println(putRet.key);
            //System.out.println(putRet.hash);
            return qinuiDomain + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        }
        return null;
    }

    public static String upload(MultipartFile file) {
        //构造一个带指定Zone对象的配置类
        Configuration cfg = new Configuration(Zone.zone2());
        //...其他参数参考类注释
        UploadManager uploadManager = new UploadManager(cfg);
        //...生成上传凭证，然后准备上传
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        try {
            String originalFilename = file.getOriginalFilename();
            String key = null;
            if (!StringUtils.isNullOrEmpty(originalFilename)) {
                key = UUID.randomUUID() + originalFilename.substring(originalFilename.lastIndexOf("."));
            }
            //默认不指定key的情况下，以文件内容的hash值作为文件名
            Response response = uploadManager.put(file.getBytes(), key, upToken);

            //解析上传成功的结果
            DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
            //System.out.println(putRet.key);
            //System.out.println(putRet.hash);
            return qinuiDomain + putRet.key;
        } catch (QiniuException ex) {
            Response r = ex.response;
            System.err.println(r.toString());
            try {
                System.err.println(r.bodyString());
            } catch (QiniuException ex2) {
                //ignore
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


}
