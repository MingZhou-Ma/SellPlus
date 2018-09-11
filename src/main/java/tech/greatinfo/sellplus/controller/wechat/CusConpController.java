package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.service.CouponsObjService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * Created by Ericwyn on 18-9-11.
 */
@RestController
public class CusConpController {
    private static final Logger logger = LoggerFactory.getLogger(CusConpController.class);

    @Autowired
    CouponsObjService objService;

    @Autowired
    CustomService customService;

    @Autowired
    TokenService tokenService;

    // 获取活动列表
    // 查看活动
    // act_type 1 为 拼团, 0 为助力

    /**
     * 获取自己的优惠卷
     *
     * POST
     *      token
     *      start       分页开始页数，默认为 0
     *      num         分页，每页的数量，默认为 999
     *      type        0 为未使用， 1 为已经使用，默认为 0
     *
     * @return
     */
    @RequestMapping(value = "/api/cus/listCoupons",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson listCoupons(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token",String.class);
            Integer type = (Integer) ParamUtils.getFromJsonWithDefault(jsonObject, "type",0,Integer.class);
            Integer start = (Integer) ParamUtils.getFromJsonWithDefault(jsonObject, "start",0,Integer.class);
            Integer num = (Integer) ParamUtils.getFromJsonWithDefault(jsonObject, "num",999, Integer.class);

            Customer customer ;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Page<CouponsObj> res ;
                if (type == 0){
                    return ResJson.successJson("get un used coupons success",objService.getAllByOwnAndUsed(customer,start,num));
                }
                return ResJson.successJson("get used coupons success",objService.getAllByOwnAndUsed(customer,start,num));
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/cus/listCoupons");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/cus/listCoupons");
        }catch (Exception e){
            logger.error("/api/cus/listCoupons -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
