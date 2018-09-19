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

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.service.CouponsHistoryService;
import tech.greatinfo.sellplus.service.CouponsObjService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.ReadHistoryService;
import tech.greatinfo.sellplus.service.SellerSerivce;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.AccessToken;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * Seller 后台相关接口
 * <p>
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

    @Autowired
    CouponsObjService objService;

    @Autowired
    CouponsHistoryService couponsHistoryService;

    /**
     * Seller 登录
     * <p>
     * POST
     * account     seller 的帐号
     * key         seller 的绑定 key
     * <p>
     * TODO，后期考虑使用微信登录
     *
     * @param account
     * @param key
     * @return
     */
    @RequestMapping(value = "/api/sell/login", method = RequestMethod.POST)
    public ResJson sellerLogin(@RequestParam("account") String account,
                               @RequestParam("key") String key) {
        try {
            Seller seller = sellerService.findByAccountAndSellerKey(account, key);
            if (seller == null) {
                return ResJson.failJson(7001, "account or password error", null);
            } else {
                AccessToken accessToken = new AccessToken();
                accessToken.setUser(seller);
                HashMap<String, String> map = new HashMap<>();
                map.put("accessToken", accessToken.getUuid());
                return ResJson.successJson("login Success", map);
            }
        } catch (Exception e) {
            logger.error("/api/sell/login -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    //

    /**
     * 获取我的客户列表
     * <p>
     * POST
     * token
     * <p>
     * 分页返回，num 设置为 9999 为了方便调试
     * <p>
     * TODO, num 参数设置其他默认值，以实现分页
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/sell/getMyCustomer", method = RequestMethod.POST)
    public ResJson getMyCustomer(@RequestParam("token") String token,
                                 @RequestParam(value = "start", defaultValue = "0") Integer start,
                                 @RequestParam(value = "num", defaultValue = "9999") Integer num,
                                 @RequestParam(value = "accessRecord", required = false) String accessRecord

    ) {
        try {
            Seller seller;
            if ((seller = (Seller) tokenService.getUserByToken(token)) != null) {
                if (accessRecord == null) {
                    return ResJson.successJson("get my customer success",
                            customService.getAllBySellerOrderByCreateTimeDesc(seller, new PageRequest(start,num)));
                } else {
                    return ResJson.successJson("get my customer success",
                            customService.getAllBySellerAndAccessRecordOrderByCreateTimeDesc(seller, accessRecord, new PageRequest(start,num)));
                }

                //customService.getAllBySellerAndOriginOrderByCreateTimeDesc(seller, origin, new PageRequest(start, num)));
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            logger.error("/api/sell/getMyCustomer -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 成为老客户
     *
     * @param token
     * @param customerId 客户id
     * @return 返回成功
     */
    @RequestMapping(value = "/api/sell/beOldCustomer", method = RequestMethod.POST)
    public ResJson beOldCustomer(@RequestParam("token") String token, @RequestParam("customerId") Long customerId) {
        try {
            Seller seller = (Seller) tokenService.getUserByToken(token);
            if (null == seller) {
                return ResJson.errorAccessToken();
            }
            Customer customer = customService.getOne(customerId);
            if (null == customer) {
                return ResJson.failJson(4003, "not customer", null);
            }
//            if (customer.getType().equals(1)) {    // 用户类型：1代表潜在客户   2代表老客户   可以写在专门定义常量的工具类中
//                customer.setType(2);
//                customService.save(customer);
//            }
            return ResJson.successJson("beOldCustomer success");
        } catch (Exception e) {
            logger.error("/api/sell/getMyCustomer -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    /**
     * 获取客户的新活动
     * <p>
     * POST
     * token
     * <p>
     * 按照时间排序，返回销售名下用户最新的一些文章阅读活动
     * <p>
     * TODO：1，增加其他活动的展示，例如助力记录？或者是商品查看记录? ； 2，num 参数设置其他默认值，以实现分页
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/sell/getCustomerNews", method = RequestMethod.POST)
    public ResJson getCustomerNews(@RequestParam("token") String token,
                                   @RequestParam(value = "start", defaultValue = "0") Integer start,
                                   @RequestParam(value = "num", defaultValue = "9999") Integer num) {
        try {
            Seller seller;
            if ((seller = (Seller) tokenService.getUserByToken(token)) != null) {
                return ResJson.successJson("get read History success",
                        readHistoryService.findAllSellerOrderByReadTimeDesc(seller, new PageRequest(start, num)));
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            logger.error("/api/sell/getMyCustomer -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     *
     * 电脑端 seller 核销优惠卷
     *
     * POST
     *      token   电脑端 seller 登录 token
     *      code    优惠卷的 code
     *
     * @param token
     * @param couponCode
     * @return
     */
    @RequestMapping(value = "/api/sell/writeOffCoupons", method = RequestMethod.POST)
    public ResJson getCustomerNews(@RequestParam("token") String token,
                                   @RequestParam(value = "code") String couponCode) {
        try {
            Seller seller;
            if ((seller = (Seller) tokenService.getUserByToken(token)) != null) {
                CouponsObj coupon = objService.findByCode(couponCode);
                if (coupon == null){
                    return ResJson.failJson(-1,"优惠卷代码错误",null);
                }else {
                    // 核销优惠卷
                    objService.writeOffCoupons(coupon,seller);
                    return  ResJson.successJson("write off coupon success");
                }
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            logger.error("/api/sell/writeOffCoupons -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 销售后台获取所有的核销记录
     *
     * POST
     *      token 销售登录后获取的 token
     *      start 分页开始，默认为 0
     *      num   分页一个页面含有的数据量，默认为 999
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/sell/writeOffHistory", method = RequestMethod.POST)
    public ResJson writeOffHistory(@RequestParam("token") String token,
                                   @RequestParam(value = "start", defaultValue = "0") Integer start,
                                   @RequestParam(value = "num", defaultValue = "9999") Integer num) {
        try {
            Seller seller;
            if ((seller = (Seller) tokenService.getUserByToken(token)) != null) {
                return ResJson.successJson("get all write off history success",couponsHistoryService.getHistoryBySeller(seller,start,num));
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            logger.error("/api/sell/writeOffCoupons -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
