package tech.greatinfo.sellplus.controller.merchant;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
@RestController
public class MsgController {

    private static final Logger logger = LoggerFactory.getLogger(MsgController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    CustomService customService;

    /**
     * 群发短信
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/mer/group/msg")
    public ResJson withdraw(@RequestParam(name = "token") String token, @RequestParam(name = "msg") String msg) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }

            List<Customer> list = customService.findAllCustomer();
            List<String> phoneList = new ArrayList<>();
            if (null != list && !list.isEmpty()) {
                for (Customer c : list) {
                    if (StringUtils.isNotEmpty(c.getPhone())) {
                        phoneList.add(c.getPhone());
                    }
                }
            }
            String phone = JSONObject.toJSONString(phoneList);
            //发送短信
//            if (!SendMulSmsUtil.sendMulSms(company, phone, msg)) {
//                return ResJson.failJson(4000, "group msg fail", null);
//            }

            return ResJson.successJson("group msg success");

        } catch (Exception e) {
            logger.error("/api/mer/uv -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
