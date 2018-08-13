package tech.greatinfo.sellplus.controller.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.ProductService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 微信用户 商品相关接口
 *
 * Created by Ericwyn on 18-8-13.
 */
@RestController
public class CusProResController {
    @Autowired
    TokenService tokenService;

    @Autowired
    ProductService productService;

    @RequestMapping(value = "/api/cus/listProduct",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findActivity(@RequestParam(name = "token") String token,
                                @RequestParam(name = "start", defaultValue = "0") Integer start,
                                @RequestParam(name = "num", defaultValue = "10") Integer num){
        try {
            if (tokenService.getUserByToken(token) != null){
                return ResJson.successJson("get product list success",productService.findAllByPages(start,num));
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
