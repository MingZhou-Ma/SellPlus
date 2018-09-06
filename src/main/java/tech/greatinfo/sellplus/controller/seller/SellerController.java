package tech.greatinfo.sellplus.controller.seller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;

import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.ReadHistoryService;
import tech.greatinfo.sellplus.service.SellerSerivce;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.AccessToken;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * Seller 后台相关接口
 *
 * Created by Ericwyn on 18-8-29.
 */
@RestController
public class SellerController {
    private static final Logger logger = LoggerFactory.getLogger(SellerController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    SellerSerivce sellerService;

    @Autowired
    CustomService customService;

    @Autowired
    ReadHistoryService readHistoryService;

    /**
     * Seller 登录
     *
     * POST
     *      account     seller 的帐号
     *      key         seller 的绑定 key
     *
     * TODO，后期考虑使用微信登录
     *
     * @param account
     * @param key
     * @return
     */
    @RequestMapping(value = "/api/sell/login",method = RequestMethod.POST)
    public ResJson sellerLogin(@RequestParam("account") String account,
                               @RequestParam("key") String key){
        try {
            Seller seller = sellerService.findByAccountAndSellerKey(account,key);
            if (seller == null){
                return ResJson.failJson(7001, "account or password error",null);
            }else {
                AccessToken accessToken = new AccessToken();
                accessToken.setUser(seller);
                HashMap<String, String> map = new HashMap<>();
                map.put("accsessToken",accessToken.getUuid());
                return ResJson.successJson("login Success",map);
            }
        }catch (Exception e){
            logger.error("/api/sell/login -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    //

    /**
     * 获取我的客户列表
     *
     * POST
     *      token
     *
     * 分页返回，num 设置为 9999 为了方便调试
     *
     * TODO, num 参数设置其他默认值，以实现分页
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/sell/getMyCustomer",method = RequestMethod.POST)
    public ResJson getMyCustomer(@RequestParam("token") String token,
                                 @RequestParam(value = "start",defaultValue = "0") Integer start,
                                 @RequestParam(value = "num",defaultValue = "9999") Integer num){
        try {
            Seller seller;
            if ((seller = (Seller) tokenService.getUserByToken(token)) != null){
                return ResJson.successJson("get my customer success",
                        customService.getAllBySeller(seller, new PageRequest(start,num)));
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/sell/getMyCustomer -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    /**
     * 获取客户的新活动
     *
     * POST
     *      token
     *
     * 按照时间排序，返回销售名下用户最新的一些文章阅读活动
     *
     * TODO：1，增加其他活动的展示，例如助力记录？或者是商品查看记录? ； 2，num 参数设置其他默认值，以实现分页
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/sell/getCustomerNews",method = RequestMethod.POST)
    public ResJson getCustomerNews(@RequestParam("token") String token,
                                   @RequestParam(value = "start",defaultValue = "0") Integer start,
                                   @RequestParam(value = "num",defaultValue = "9999") Integer num){
        try {
            Seller seller;
            if ((seller = (Seller) tokenService.getUserByToken(token)) != null){
                return ResJson.successJson("get read History success",
                        readHistoryService.findAllSellerOrderByReadTimeDesc(seller,new PageRequest(start,num)));
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/sell/getMyCustomer -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }

    }
}
