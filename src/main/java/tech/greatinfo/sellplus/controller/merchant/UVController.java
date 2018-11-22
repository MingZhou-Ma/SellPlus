package tech.greatinfo.sellplus.controller.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.repository.CustomRepository;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * UV PV统计
 */
@RestController
public class UVController {

    private static final Logger logger = LoggerFactory.getLogger(UVController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    CustomService customService;

    @Autowired
    CustomRepository customRepository;

    /**
     * UV统计
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/mer/uv")
    public ResJson withdraw(@RequestParam(name = "token") String token) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }

            long count = customService.count();
            return ResJson.successJson("success", count);

        } catch (Exception e) {
            logger.error("/api/mer/uv -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/customer/month/num")
    public ResJson getThisMonthCustomerNum() {
        return ResJson.successJson("success", customRepository.getThisMonthCustomerNum());
    }

}
