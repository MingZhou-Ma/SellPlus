package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Company;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.service.*;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.AccessToken;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * 老司机（老顾客）接口
 *
 * 具体的事务逻辑是
 *
 *      1,商家后台设定几个参数
 *
 *              老司机能够发的卷的id
 *              老司机推广名额阈值，推广卷的核销达到阈值就能够兑换一张老司机专属优惠卷，暂时没有兑换数量上限
 *              老司机专属优惠卷的 id
 *
 *      2,老司机注册
 *
 *              sell 查看自己的顾客人员列表
 *              查到到具体的要成为老司机的顾客的id或者其他唯一标识
 *              生成一个固定的链接 /api/freq/beFreq?cus={顾客id}
 *              顾客访问这个链接的时候，带上 token 参数， /api/freq/beFreq?cus= {顾客id} & token = {token}
 *              后台判断 token 是否正确然后为这个用户注册成为老司机
 *
 *      3,老司机发卷
 *
 *              所有老司机发卷都是使用同一个基础接口，也就是生成的发卷链接，只有老司机的id参数是不一样的
 *              前端获取到老司机的id参数之后，生成一个链接 /api/freq/general?freq={老司机的id}
 *              然后老司机将这个链接分享给其他用户（所以实际上这一步并不需要后台参与）
 *
 *      4，新人领卷
 *
 *              新人登录之后访问领卷接口 /api/freq/general?freq={老司机的id}，还要带上
 *              发的卷将在后台记录以下基础信息：老司机id（发卷人），新人id（领卷人），卷类型（后台预设好的）
 *
 *      5，新人卷核销
 *
 *              就是优惠卷核销的逻辑
 *
 *      6，老司机领取专属优惠卷
 *
 *              前端通过接口获取老司机的（已发卷数量，核销卷数量） 接口是 /api/freq/getFreqInfo
 *              老司机进入到专属页面后，可以点击兑换老司机卷（访问老司机卷兑换接口 /api/freq/convert ）
 *              后台根据核销的数量来发卷，发卷数量 = 核销数量 / 第1步时后台设置的核销阈值
 *
 *      至此整个老司机发卷流程结束
 *
 * Created by Ericwyn on 18-9-4.
 */
@RestController
public class FrequenterConrtoller {
    private static final Logger logger = LoggerFactory.getLogger(FrequenterConrtoller.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    CouponsService modelService;

    @Autowired
    CouponsObjService objService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CustomService customService;

    @Autowired
    FreqService freqService;


    /**
     * 申请成为老司机
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/freq/beFreq", method = RequestMethod.POST)
    public ResJson beFreq(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }

            customer.setFrequenter(true);
            customer.setFreqBonus(0);
            customService.save(customer);

            AccessToken accessToken = tokenService.getToken(token);
            accessToken.setUser(customer);
            tokenService.saveToken(accessToken);

            return ResJson.successJson("be freq success");

        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/beFreq");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/beFreq");
        }catch (Exception e){
            logger.error("/api/freq/beFreq -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 查询3条领券者列表
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/freq/coupon/list/3", method = RequestMethod.POST)
    public ResJson FreqCouponList3(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }

            List<CouponsObj> list = objService.findFirst3ByOrigin(customer);
            return ResJson.successJson("find coupon list success", list);

        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/coupon/list/3");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/coupon/list/3");
        }catch (Exception e){
            logger.error("/api/freq/coupon/list/3 -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 查询全部领券者列表
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/freq/coupon/list/all", method = RequestMethod.POST)
    public ResJson FreqCouponListAll(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }
            List<CouponsObj> list = objService.findAllByOrigin(customer);
            return ResJson.successJson("find coupon list success", list);

        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/coupon/list/all");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/coupon/list/all");
        }catch (Exception e){
            logger.error("/api/freq/coupon/list/all -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 客户领取老司机发放的券
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/freq/coupon/receive", method = RequestMethod.POST)
    public ResJson receiveFreqCoupon(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Long freqId = (Long) ParamUtils.getFromJson(jsonObject,"freqId", String.class); // 老司机id
            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }
            Customer freq = customService.getOne(freqId);
            if (null == freq) {
                return ResJson.failJson(4000, "老司机不存在", null);
            }
            Coupon coupon = companyService.getFreqCoupon();
            if (null == coupon) {
                return ResJson.failJson(4000, "尚未设置老司机发放的优惠卷", null);
            }
            CouponsObj couponsObj = new CouponsObj();
            couponsObj.setCoupon(coupon);
            couponsObj.setCode(objService.getRandomCouponCode());
            couponsObj.setOrigin(freq);
            couponsObj.setOwn(customer);
            couponsObj.setNote("老司机发放的优惠卷");
            couponsObj.setGeneralTime(new Date());
            objService.save(couponsObj);

            return ResJson.successJson("领取成功");
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/coupon/receive");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/coupon/receive");
        }catch (Exception e){
            logger.error("/api/freq/coupon/receive -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 老司机通过 Seller 的顾客链接来注册成为老司机
     * 该链接是给前端的一个特殊页面调用的，特殊页面固定地址，Seller 分享的时候就是分享这个特殊页面
     * 特殊页面里面调用 ajax ，向接口发送数据，完成绑定
     *
     * POST
     *      token
     *      cusid     这个 cusid 必须跟当前的登录用户一致，才能完成绑定
     *
     * @param jsonObject
     * @return
     */
//    @RequestMapping(value = "/api/freq/beFreq",method = RequestMethod.POST)
//    public ResJson beFreq(@RequestBody JSONObject jsonObject){
//        try {
//            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
//            Long cusId = (Long) ParamUtils.getFromJson(jsonObject,"cusid",Long.class);
//            Customer customer;
//            if ((customer = (Customer) tokenService.getUserByToken(token)) != null && customer.getId().equals(cusId)){
//                customer.setFrequenter(true);
//                customService.save(customer);
//                return ResJson.successJson("be freq success");
//            }else {
//                return ResJson.errorAccessToken();
//            }
//        }catch (JsonParseException jse){
//            logger.info(jse.getMessage()+" -> /api/freq/beFreq");
//            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/beFreq");
//        }catch (Exception e){
//            logger.error("/api/freq/beFreq -> ",e.getMessage());
//            e.printStackTrace();
//            return ResJson.serverErrorJson(e.getMessage());
//        }
//    }

    /**
     * 发卷无需使用接口，老司机那边生成一个链接就好了
     * 前端生成一个特定的页面链接，带上老司机 id 参数
     *
     * 用户访问那个特定页面的时候，上传 url 参数中的老司机 id 以及 token 来获得卷
     * 用户通过这个接口来领取老司机发的优惠卷
     *
     * POST
     *      cusid       老司机用户的 id
     *      token
     *
     * @return
     */
    @RequestMapping(value = "/api/freq/general",method = RequestMethod.POST)
    public ResJson general(@RequestBody JSONObject jsonObject){
//        return null;
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Long cusId = (Long) ParamUtils.getFromJson(jsonObject,"cusid",Long.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Customer freq;
                if ((freq = customService.getOne(cusId)) == null){
                    return ResJson.failJson(-1,"老司机 id 错误",null);
                }
                Company couponSet = companyService.findByKey("coupon2");
                if (couponSet == null){
                    return ResJson.failJson(-1,"默认奖励优惠卷未设置，请到后台设置",null);
                }else {
                    Long couponId = Long.parseLong(couponSet.getV());
                    Coupon coupon = modelService.findOne(couponId);
                    if (coupon == null){
                        return ResJson.failJson(-1,"默认奖励优惠卷不存在",null);
                    }else {
                        CouponsObj couponsObj = new CouponsObj();
                        couponsObj.setCoupon(coupon);
                        couponsObj.setExpired(false);
                        couponsObj.setCode(objService.getRandomCouponCode());
                        couponsObj.setOrigin(freq);
                        couponsObj.setOwn(customer);
                        objService.save(couponsObj);
                        return ResJson.successJson("领卷成功");
                    }
                }
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/general");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/general");
        }catch (Exception e){
            logger.error("/api/freq/general -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    /**
     *
     * 获取自己的老司机数据（发了多少卷，有多少人核销了）
     *
     * POST
     *      token
     *
     * 返回格式如下
     *
     *  {
     *      xxx
     *      xxx
     *      data:{
     *          "send":10,                      // 发送的卷数量
     *          "success":8                     // 核销的卷数量
     *      }
     *  }
     *
     * @return
     */
    @RequestMapping(value = "/api/freq/getFreqInfo",method = RequestMethod.POST)
    public ResJson getFreqInfo(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Long send = objService.countAllByOrigin(customer);
                Long success = objService.countAllByOriginAndExpiredTrue(customer);
                HashMap<String,Long> map = new HashMap<>();
                map.put("send", send);
                map.put("success", success);
                return ResJson.successJson("get freq info success", map);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/getFreqInfo");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/getFreqInfo");
        }catch (Exception e){
            logger.error("/api/freq/getFreqInfo -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    /**
     * 老司机兑换自己的推广名额
     *
     * POST
     *      token
     *
     * @return
     */
    @RequestMapping(value = "/api/freq/convert",method = RequestMethod.POST)
    public ResJson convert(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Long success = objService.countAllByOriginAndExpiredTrue(customer);
                Company promotionSet = companyService.findByKey("promotion");
                Company couponSet = companyService.findByKey("coupon1");
                if (promotionSet == null){
                    return ResJson.failJson(-1,"老司机奖励阈值未设置，请到后台设置",null);
                }
                if (couponSet == null){
                    return ResJson.failJson(-1,"老司机奖励卷未设置，请到后台设置",null);
                }
                Coupon coupon;
                if ((coupon = modelService.findOne(Long.parseLong(couponSet.getV()))) == null){
                    return ResJson.failJson(-1,"老司机奖励卷设置错误，请到后台设置",null);
                }
                Integer promotion = Integer.parseInt(promotionSet.getV());
                int convertNum = success.intValue()/ promotion;

                freqService.generalFreqConpons(convertNum,customer,coupon);
                return ResJson.successJson("兑换成功");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/convert");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/convert");
        }catch (Exception e){
            logger.error("/api/freq/convert -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
