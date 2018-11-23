package tech.greatinfo.sellplus.controller.merchant;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.MsgRecord;
import tech.greatinfo.sellplus.repository.MsgRecordRepository;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.GroupSmsParamUtil;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 */
@RestController
public class GroupMsgController {

    private static final Logger logger = LoggerFactory.getLogger(GroupMsgController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    CustomService customService;

    @Value("${company}")
    private String company;

    @Value("${appid}")
    private String appid;

    @Value("${centerManagerSysUrl}")
    private String centerManagerSysUrl;

    @Autowired
    MsgRecordRepository msgRecordRepository;

    /**
     * 群发短信
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/mer/group/msg")
    public ResJson withdraw(@RequestParam(name = "token") String token, @RequestParam(name = "content") String content) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }

            List<Customer> list = customService.findAllCustomer();
            List<String> phoneList = new ArrayList<>();
            List<GroupSmsParamUtil> paramList = new ArrayList<>();
            List<String> signNameList = new ArrayList<>();
            if (null != list && !list.isEmpty()) {
                for (Customer c : list) {
                    if (StringUtils.isNotEmpty(c.getPhone())) {
                        phoneList.add(c.getPhone());
                        signNameList.add("获客Plus");
                        GroupSmsParamUtil groupSmsParamUtil = new GroupSmsParamUtil();
                        groupSmsParamUtil.setContent("提示：" + content + "。");
                        groupSmsParamUtil.setCompany(company);
                        paramList.add(groupSmsParamUtil);
                    }
                }
            }
            String phone = JSONObject.toJSONString(phoneList);
            String signName = JSONObject.toJSONString(signNameList);
            String param = JSONObject.toJSONString(paramList);
            //发送短信
            /*if (!SendGroupSmsUtil.sendMulSms(phone, signName, param)) {
                return ResJson.failJson(4000, "group msg fail", null);
            }*/

            JSONObject json = new JSONObject();
            json.put("phone", phone);
            json.put("signName", signName);
            json.put("param", param);
            json.put("appId", appid);
            json.put("numbers", phoneList.size());
            //创建一个OkHttpClient对象
            OkHttpClient okHttpClient = new OkHttpClient();
            Request request = new Request.Builder()
                    .url(centerManagerSysUrl + "/api/sms/send")
                    .post(okhttp3.RequestBody.create(MediaType.parse("application/json; charset=utf-8"), json.toJSONString()))
                    .build();
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                String result = response.body() != null ? response.body().string() : null;
                System.out.println("返回结果：" + result);
                // 如果短信发送成功，则继续执行
                JSONObject obj = JSON.parseObject(result);
                String code = obj.getString("code");
                if (code.equals("1000")) {
                    MsgRecord msgRecord = new MsgRecord();
                    msgRecord.setNum(phoneList.size());
                    msgRecord.setContent(content);
                    msgRecord.setSendTime(new Date());
                    msgRecord.setCustomer(null);
                    msgRecordRepository.save(msgRecord);

                    return ResJson.successJson("group msg success");
                } else {
                    return ResJson.successJson("group msg fail");
                }

            } else {
                return ResJson.failJson(4000, "请求发送短信接口失败", null);
            }

            //return ResJson.successJson("group msg success");

        } catch (Exception e) {
            logger.error("/api/mer/group/msg -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
