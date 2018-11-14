package tech.greatinfo.sellplus.controller.merchant;

import com.alibaba.fastjson.JSONObject;
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
import tech.greatinfo.sellplus.utils.SendGroupSmsUtil;
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
                        groupSmsParamUtil.setContent(content);
                        groupSmsParamUtil.setCompany(company);
                        paramList.add(groupSmsParamUtil);
                    }
                }
            }
            String phone = JSONObject.toJSONString(phoneList);
            String signName = JSONObject.toJSONString(signNameList);
            String param = JSONObject.toJSONString(paramList);
            //发送短信
            if (!SendGroupSmsUtil.sendMulSms(phone, signName, param)) {
                return ResJson.failJson(4000, "group msg fail", null);
            }

            MsgRecord msgRecord = new MsgRecord();
            msgRecord.setNum(phoneList.size());
            msgRecord.setContent(content);
            msgRecord.setSendTime(new Date());
            msgRecord.setCustomer(null);
            msgRecordRepository.save(msgRecord);

            return ResJson.successJson("group msg success");

        } catch (Exception e) {
            logger.error("/api/mer/group/msg -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
