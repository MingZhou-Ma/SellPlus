package tech.greatinfo.sellplus.service.impl;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.config.StaticConfig;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.SellerCode;
import tech.greatinfo.sellplus.repository.SellerCodeRepository;
import tech.greatinfo.sellplus.service.SellerCodeService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.WeChatUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class SellerCodeServiceImpl implements SellerCodeService {

    private static final Logger logger = LoggerFactory.getLogger(SellerCodeServiceImpl.class);

    private final TokenService tokenService;

    private final SellerCodeRepository sellerCodeRepository;

    public static OkHttpClient client = WeChatUtils.client;

    private static final String SellerCodePath = StaticConfig.SAVE_SELLER_CODE_PATH;

    private static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    @Autowired
    public SellerCodeServiceImpl(TokenService tokenService, SellerCodeRepository sellerCodeRepository) {
        this.tokenService = tokenService;
        this.sellerCodeRepository = sellerCodeRepository;
    }


    @Override
    public String getSellerCode(Customer customer, String token, String scene, String page) throws IOException {
        SellerCode sc = sellerCodeRepository.findBySceneAndPage(scene, page);
        if (sc != null) {
            return sc.getPath();
        }
        if (scene == null || page == null) {
            scene = "";
            page = "";
        }
        String jsonFD = "{\"scene\":\"" + scene + "\",\"page\":\"" + page + "\"}";
        RequestBody body = RequestBody.create(JSON, jsonFD);
        Request request = new Request.Builder()
                .get()
                .url("https://api.weixin.qq.com/wxa/getwxacodeunlimit?access_token=" + token)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();

        if (response.isSuccessful() && response.code() == 200) {
            InputStream inputStream = response.body().byteStream();
            File saveDirPath = new File(SellerCodePath);
            if (!saveDirPath.isDirectory()) {
                saveDirPath.mkdirs();
            }
            File saveFile = new File(saveDirPath, "" + System.currentTimeMillis() + ".jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(saveFile);
            int readLength = -1;
            byte[] bytesForRead = new byte[1024];

            ByteArrayOutputStream result = new ByteArrayOutputStream();
            while ((readLength = inputStream.read(bytesForRead)) != -1) {
                fileOutputStream.write(bytesForRead, 0, readLength);
                result.write(bytesForRead, 0, readLength);
            }
            fileOutputStream.flush();
            fileOutputStream.close();
            String resultStr = result.toString(StandardCharsets.UTF_8.name());
            // 如果发现返回的是错误提示的话
            if (resultStr.contains("errcode")) {
                saveFile.delete();
                return resultStr;
            }

            SellerCode sellerCode = new SellerCode();
            sellerCode.setScene(scene);
            sellerCode.setPage(page);
            sellerCode.setPath(SellerCodePath + "/" + saveFile.getName());
            sellerCode.setCustomer(customer);
            sellerCodeRepository.save(sellerCode);

            return sellerCode.getPath();
        } else {
            return null;
        }
    }


    @Override
    public ResJson getSellerCodeList(JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);

            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }

            List<SellerCode> list = sellerCodeRepository.findAllByCustomer(customer);
            return ResJson.successJson("get sellerCode list success", list);
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/sellerCode/getSellerCodeList");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/sellerCode/getSellerCodeList");
        } catch (Exception e) {
            logger.error("/api/sellerCode/getSellerCodeList -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
