package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.CouponsObjService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 获取自己的优惠卷信息
 * Created by Ericwyn on 18-9-6.
 */
@RestController
public class CusCouponController {
    private static final Logger logger = LoggerFactory.getLogger(CusCouponController.class);

    @Autowired
    CouponsObjService objService;

    @Autowired
    TokenService tokenService;

    /**
     * 获取我的优惠卷详情
     *
     * POST
     *      token
     *      start
     *      num
     *
     * // TODO 分页查询
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/getMyCoupon",method = RequestMethod.POST)
    public ResJson getMyCoupon(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token",String.class);
            Integer start = (Integer) ParamUtils.getFromJsonWithDefault(jsonObject, "start", 0, Integer.class);
            Integer num = (Integer) ParamUtils.getFromJsonWithDefault(jsonObject, "num", 999, Integer.class);
            Customer customer ;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                return ResJson.successJson("get coupons success",objService.getAllByOwn(customer,start,num));
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> api/cus/listActivity");
            return ResJson.errorRequestParam(jse.getMessage()+" -> api/cus/listActivity");
        }catch (Exception e){
            logger.error("api/cus/listActivity -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
