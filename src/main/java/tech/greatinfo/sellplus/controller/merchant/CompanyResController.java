package tech.greatinfo.sellplus.controller.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import tech.greatinfo.sellplus.domain.Company;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.CompanyService;
import tech.greatinfo.sellplus.service.MerchantService;
import tech.greatinfo.sellplus.service.ProductService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 公司设置的 API
 *
 * Created by Ericwyn on 18-8-7.
 */
@RestController
public class CompanyResController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyResController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    ProductService productService;

    @Autowired
    ActivityService activityService;

    @Autowired
    CompanyService companyService;

    @RequestMapping(value = "/api/mer/setMainInfo",method = RequestMethod.POST)
    public ResJson setMainInfo(@RequestParam(value = "banner1",required = false) String banner1,
                               @RequestParam(value = "banner2",required = false) String banner2,
                               @RequestParam(value = "banner3",required = false) String banner3,
                               @RequestParam(value = "notify1",required = false) String notify1,
                               @RequestParam(value = "notify2",required = false) String notify2,
                               @RequestParam(value = "notify3",required = false) String notify3,
                               @RequestParam(value = "token") String token){

        try {
            if (tokenService.getUserByToken(token) != null){
                List<Company> list = new ArrayList<>();
                list.add(new Company("banner1",banner1));
                list.add(new Company("banner2",banner2));
                list.add(new Company("banner3",banner3));
                list.add(new Company("notify1",notify1));
                list.add(new Company("notify2",notify2));
                list.add(new Company("notify3",notify3));
                companyService.saveMainInfo(list);
                return ResJson.successJson("set company info success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/setMainInfo -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 获取接口在 public Res Controller



}
