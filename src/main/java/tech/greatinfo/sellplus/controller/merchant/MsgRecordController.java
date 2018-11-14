package tech.greatinfo.sellplus.controller.merchant;

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

}
