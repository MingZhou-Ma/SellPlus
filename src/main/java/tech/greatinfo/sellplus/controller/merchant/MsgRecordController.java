package tech.greatinfo.sellplus.controller.merchant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.repository.MsgRecordRepository;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 */
@RestController
public class MsgRecordController {

    private static final Logger logger = LoggerFactory.getLogger(MsgRecordController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    CustomService customService;

    @Value("${company}")
    private String company;

    @Autowired
    MsgRecordRepository msgRecordRepository;

    @Value("${centerManagerSysUrl}")
    private String centerManagerSysUrl;

    @Value("${appid}")
    private String appid;

    /**
     * 获取短信记录
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/mer/msg/record",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findMsgRecord(@RequestParam(name = "token") String token,
                                 @RequestParam(name = "start",defaultValue = "0") Integer start,
                                 @RequestParam(name = "num",defaultValue = "10") Integer num) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }

            return ResJson.successJson("find msg record success", msgRecordRepository.findAll(new PageRequest(start, num)));

        } catch (Exception e) {
            logger.error("/api/mer/msg/record -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/mer/msg/fee",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson getMsgFee(@RequestParam(name = "token") String token) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }

            JSONObject json = new JSONObject();
            json.put("appId", appid);
            //创建一个OkHttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(centerManagerSysUrl + "/api/enterprise/getByAppId")
                    .post(RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toJSONString()))
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body() != null ? response.body().string() : null;
                System.out.println("返回结果：" + result);
                // 如果短信发送成功，则继续执行
                JSONObject obj = JSON.parseObject(result);
                String code = obj.getString("code");
                if (code.equals("1000")) {
                    return ResJson.successJson("get enterprise success", obj.get("data"));
                } else {
                    return ResJson.successJson("get enterprise fail");
                }

            } else {
                return ResJson.failJson(4000, "请求获取企业接口失败", null);
            }


        } catch (Exception e) {
            logger.error("/api/mer/msg/fee -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
