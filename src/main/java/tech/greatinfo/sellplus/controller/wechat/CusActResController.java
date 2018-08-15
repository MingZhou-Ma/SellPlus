package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.models.auth.In;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 用户端的活动相关接口
 *
 * Created by Ericwyn on 18-8-13.
 */
@RestController
public class CusActResController {

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
                if (type == 0){
                    return ResJson.successJson("find  Activity success",
                            activityService.getAllHelpAct(start, num));
                }else if (type == 1){
                    return ResJson.successJson("find  Activity success",
                            activityService.getAllGroupAct(start, num));
                }else {
                    return ResJson.errorAccessToken();
                }
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
