package tech.greatinfo.sellplus.controller.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
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
    public ResJson findActivity(@RequestParam(name = "token") String token,
                                @RequestParam(name = "act_type",defaultValue = "0") Integer type,
                                @RequestParam(name = "start", defaultValue = "0") Integer start,
                                @RequestParam(name = "num", defaultValue = "10") Integer num){
        try {
            Customer customer ;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                return null;
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
