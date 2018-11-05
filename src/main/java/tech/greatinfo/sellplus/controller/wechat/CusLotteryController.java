package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Lottery;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.service.*;
import tech.greatinfo.sellplus.utils.LotteryUtil;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.AccessToken;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 抽奖管理
 */
@RestController
public class CusLotteryController {

    private static final Logger logger = LoggerFactory.getLogger(CusLotteryController.class);

    @Autowired
    CompanyService companyService;

    @Autowired
    TokenService tokenService;

    @Autowired
    CouponsService couponsService;

    @Autowired
    CouponsObjService couponsObjService;

    @Autowired
    CustomService customService;

    @RequestMapping(value = "/api/cus/lottery", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson lottery(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }
            if (customer.getLotteryNum() <= 0) {
                return ResJson.failJson(4000, "无抽奖机会", null);
            }

            String lotteryStr = companyService.getLotteryStr();
            if (StringUtils.isEmpty(lotteryStr)) {
                return ResJson.failJson(4000, "未设置奖品、概率", null);
            }
            String[] lotteryList = lotteryStr.split(",");
            List<Lottery> list = new ArrayList<>();
            for (String lotteryItem : lotteryList) {
                if (StringUtils.isNotEmpty(lotteryItem)) {
                    String[] item = lotteryItem.split("/");
                    if (StringUtils.isNotEmpty(item[1]) && StringUtils.isNotEmpty(item[2])) {
                        Lottery lottery = new Lottery(item[0], Long.valueOf(item[1]) ,Double.valueOf(item[2]));
                        list.add(lottery);
                    }
                }
            }
            int index = LotteryUtil.drawGift(list); // 抽到的奖品下标
            Lottery lottery = list.get(index);
            if (null != lottery) {
                Long couponsId = lottery.getCouponsId();
                Coupon coupon = couponsService.findOne(couponsId);
                if (null != coupon) {
                    // 发券
                    CouponsObj couponsObj = new CouponsObj();
                    couponsObj.setCoupon(coupon);
                    couponsObj.setCode(couponsObjService.getRandomCouponCode());
                    couponsObj.setOwn(customer);
                    couponsObj.setNote("抽奖发放的优惠卷");
                    couponsObj.setGeneralTime(new Date());
                    couponsObj.setExpired(false);
                    couponsObjService.save(couponsObj);
                }
            }
            // 抽奖次数减一
            if (customer.getLotteryNum() > 0) {
                customer.setLotteryNum(customer.getLotteryNum() - 1);
                customService.save(customer);

                //更新redis缓存
                AccessToken accessToken = tokenService.getToken(token);
                accessToken.setUser(customer);
                tokenService.saveToken(accessToken);
            }

            return ResJson.successJson("success", index);
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/cus/lottery");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/cus/lottery");
        } catch (Exception e) {
            logger.error("/api/cus/lottery", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
