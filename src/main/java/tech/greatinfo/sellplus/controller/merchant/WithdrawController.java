package tech.greatinfo.sellplus.controller.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.FreqWithdraw;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.repository.FreqWithdrawRepository;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 提现管理
 */
@RestController
public class WithdrawController {

    private static final Logger logger = LoggerFactory.getLogger(WithdrawController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    FreqWithdrawRepository freqWithdrawRepository;


    /**
     * 提现
     * @param token
     * @param withdrawId
     * @return
     */
    @RequestMapping(value = "/api/mer/withdraw")
    public ResJson withdraw(@RequestParam(name = "token") String token, @RequestParam(name = "withdrawId") Long withdrawId) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            FreqWithdraw freqWithdraw = freqWithdrawRepository.getOne(withdrawId);
            if (freqWithdraw.getSuccessTran()) {
                return ResJson.failJson(4000, "不允许重复提现", null);
            }
            freqWithdraw.setSuccessTran(true);
            freqWithdrawRepository.save(freqWithdraw);
            return ResJson.successJson("withdraw success");

        } catch (Exception e) {
            logger.error("/api/mer/withdraw ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/mer/withdraw/list")
    public ResJson withdraw(@RequestParam(name = "token") String token,
                            @RequestParam(name = "start",defaultValue = "0") Integer start,
                            @RequestParam(name = "num",defaultValue = "10") Integer num) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            return ResJson.successJson("query withdraw list success", freqWithdrawRepository.findAll(new PageRequest(start, num)));
        } catch (Exception e) {
            logger.error("/api/mer/withdraw/list", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
