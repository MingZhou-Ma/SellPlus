package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import tech.greatinfo.sellplus.config.StaticConfig;
import tech.greatinfo.sellplus.domain.QRcode;
import tech.greatinfo.sellplus.repository.QRcodeRepository;
import tech.greatinfo.sellplus.util.WebUtils;

/**
 * Created by Ericwyn on 18-7-31.
 */
@Service
public class QRcodeService {
    @Autowired
    QRcodeRepository repository;

    public static OkHttpClient client = WebUtils.client;

    private static final String QRcodePath = StaticConfig.SAVE_QRCODE_PATH;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public void save(QRcode qRcode){
        repository.save(qRcode);
    }

    public QRcode fineOne(Long id){
        return repository.findOne(id);
    }

    public QRcode findByScenceAndPage(String scene, String page){
        return repository.findByScenceAndPage(scene,page);
    }

    public String getQRCode(String token, String scene, String page) throws IOException {
        QRcode code = repository.findByScenceAndPage(scene, page);
        if (code != null){
            return code.getPath();
        }
        if (scene == null || page == null){
            scene = "";
            page = "";
        }
        String jsonFD = "{\"scene\":\""+scene+"\",\"page\":\""+page+"\"}";
        RequestBody body = RequestBody.create(JSON,jsonFD);
        Request request = new Request.Builder()
                .get()
                .url("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token="+token)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful() && response.code() == 200){
            InputStream inputStream = response.body().byteStream();
            File saveDirPath = new File(QRcodePath);
            if (!saveDirPath.isDirectory()){
                saveDirPath.mkdirs();
            }
            File saveFile = new File(saveDirPath,"" + System.currentTimeMillis());
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            int readLength = -1;
            byte[] bytesForRead = new byte[1024];

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            while ((readLength = inputStream.read(bytesForRead)) != -1){
                fileOutputStream.write(bytesForRead,0,readLength);
                result.write(bytesForRead,0,readLength);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            String resultStr = result.toString(StandardCharsets.UTF_8.name());
            // 如果发现返回的是错误提示的话
            if (resultStr.contains("errcode")){
                saveFile.delete();
                return resultStr;
            }
            QRcode qRcode = new QRcode();
            qRcode.setScence(scene);
            qRcode.setPage(page);
            qRcode.setPath(QRcodePath+"/"+saveFile.getName());
            repository.save(qRcode);
            return qRcode.getPath();
        }else {
            return null;
        }
    }
}
