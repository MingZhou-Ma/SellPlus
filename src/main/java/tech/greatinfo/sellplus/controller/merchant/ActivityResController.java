package tech.greatinfo.sellplus.controller.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

import tech.greatinfo.sellplus.config.converter.StringToDateConverter;
import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.MerchantService;
import tech.greatinfo.sellplus.service.ProductService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 活动接口
 *
 * Created by Ericwyn on 18-8-6.
 */
@RestController
public class ActivityResController {

    @Autowired
    TokenService tokenService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    ProductService productService;

    @Autowired
    ActivityService activityService;

    @InitBinder
    public void intDate(WebDataBinder dataBinder){
        dataBinder.addCustomFormatter(new StringToDateConverter("yyyy-MM-dd hh:mm:ss"));
//        dataBinder.addCustomFormatter(new StringToBooleanConverter());
    }

    // 增加活动
    @RequestMapping(value = "/api/mer/addActivity",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson addActivity(@RequestParam(name = "token") String token,
                               @ModelAttribute Activity activity,
                               HttpServletRequest request){
        try {
            if (tokenService.getUserByToken(token) != null){
                // TODO ModelAttribute 无法解析 Boolean 有点奇怪
                activity.setGroup(request.getParameter("isGroup") != null &&
                        (request.getParameter("isGroup").equals("true") || request.getParameter("isGroup").equals("1")));
                activityService.save(activity);
                return ResJson.successJson("add activity success",activity);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 查看活动
    // act_type 1 为 拼团, 0 为助力
    @RequestMapping(value = "/api/mer/listActivity",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findActivity(@RequestParam(name = "token") String token,
                                @RequestParam(name = "act_type",defaultValue = "0") Integer type,
                                @RequestParam(name = "start", defaultValue = "0") Integer start,
                                @RequestParam(name = "num", defaultValue = "10") Integer num){
        try {
            if (tokenService.getUserByToken(token) != null){
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


    // 删除活动
    @RequestMapping(value = "/api/mer/delActivity",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson delActivity(@RequestParam(name = "token") String token,
                               @RequestParam(name = "activityid") Long activityId){
        try {
            if (tokenService.getUserByToken(token) != null){
                activityService.deleteActivity(activityId);
                return ResJson.successJson("delete activity success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    // 修改活动
    @RequestMapping(value = "/api/mer/updateActivity",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson updateActivity(@RequestParam(name = "token") String token,
                                  @ModelAttribute Activity activity ){
        try {
            if (tokenService.getUserByToken(token) != null){
                if (activity.getId() == null){
                    return ResJson.failJson(7004,"activity id error",null);
                }
                Activity oldActivity;
                if ((oldActivity = activityService.findOne(activity.getId())) == null ){
                    return ResJson.failJson(7003,"无法更新, 权限错误",null);
                }
                activityService.updateActivity(oldActivity,activity);
                return ResJson.successJson("update Activity success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
