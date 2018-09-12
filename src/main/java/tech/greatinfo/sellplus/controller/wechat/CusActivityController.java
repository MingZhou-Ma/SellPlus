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

import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 用户端的活动相关接口
 *
 * Created by Ericwyn on 18-8-13.
 */
@RestController
public class CusActivityController {

    private static final Logger logger = LoggerFactory.getLogger(CusActivityController.class);

    @Autowired
    ActivityService activityService;

    @Autowired
    CustomService customService;

    @Autowired
    TokenService tokenService;

    // 获取活动列表
    // 查看活动
    // act_type 1 为 拼团, 0 为助力
    @RequestMapping(value = "/api/cus/listActivity",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findActivity(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token",String.class);
            Integer type = (Integer) ParamUtils.getFromJsonWithDefault(jsonObject, "act_type",0,Integer.class);
            Integer start = (Integer) ParamUtils.getFromJsonWithDefault(jsonObject, "start",0,Integer.class);
            Integer num = (Integer) ParamUtils.getFromJsonWithDefault(jsonObject, "num", 10, Integer.class);

            Customer customer ;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Page<Activity> res;
                if (type == 0){
                    res = activityService.getAllHelpAct(start, num);
                    return ResJson.successJson("find  Activity success", res);
                }else if (type == 1){
                    res = activityService.getAllGroupAct(start, num);
                    return ResJson.successJson("find  Activity success", res);
                }else {
                    return ResJson.errorAccessToken();
                }
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
