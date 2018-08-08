package tech.greatinfo.sellplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.MerchantService;
import tech.greatinfo.sellplus.service.ProductService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.util.obj.AccessToken;
import tech.greatinfo.sellplus.util.obj.ResJson;

/**
 *
 * 商家登录
 *
 * Created by Ericwyn on 18-8-6.
 */
@RestController
public class MerResController {
    @Autowired
    TokenService tokenService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    ProductService productService;

    @Autowired
    ActivityService activityService;

    // 登录
    @RequestMapping(value = "/api/mer/login",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson login(@RequestParam(name = "account") String account,     // 商家账户
                         @RequestParam(name = "password") String password     // 密码
    ){
        try {
            Merchant merchant = merchantService.findByAccountAndPassword(account, password);
            if (merchant == null){
                return ResJson.failJson(7001, "account or password error",null);
            }else {
                AccessToken accessToken = new AccessToken();
                accessToken.setUser(merchant);
                tokenService.saveToken(accessToken);
                HashMap<String, String> map = new HashMap<String, String>() ;
                map.put("accsessToken",accessToken.getUuid());
                return ResJson.successJson("login Success",map);
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
