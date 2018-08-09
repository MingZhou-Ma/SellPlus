package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;
import java.util.List;

import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.help.Help;
import tech.greatinfo.sellplus.domain.help.HelpHistory;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.GroupService;
import tech.greatinfo.sellplus.service.HelpHistoryService;
import tech.greatinfo.sellplus.service.HelpService;
import tech.greatinfo.sellplus.service.JoinGroupService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 助力活动 api
 *
 * Created by Ericwyn on 18-7-27.
 */
@RestController
public class HelpResController {
    @Autowired
    ActivityService activityService;

    @Autowired
    TokenService tokenService;

    @Autowired
    GroupService groupService;

    @Autowired
    JoinGroupService joinService;

    @Autowired
    HelpService helpService;

    @Autowired
    HelpHistoryService historyService;

    /**
     *
     * 发起一个助力活动
     *
     * 参数:
     *      token
     *      activityid
     *
     *
     *
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/user/addHelp", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson addHelp(@RequestBody JSONObject jsonObject){
        try {
            String token ;
            Long activityId ;
            try {
                token = jsonObject.getString("token");
                activityId = jsonObject.getLong("activityid");
                if (token == null || activityId == null){
                    return ResJson.errorRequestParam();
                }
            }catch (IllegalArgumentException ile){
                return ResJson.errorRequestParam();
            }
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                Activity activity = null;
                if ((activity = activityService.findOne(activityId))==null){
                    return ResJson.failJson(4001,"the activity id error",null);
                }
                if (activity.getGroup()){
                    return ResJson.failJson(4003,"not help activity",null);
                }
                Help help = new Help();
                help.setActivity(activity);
                help.setCustomer(customer);
                helpService.save(help);
                return ResJson.successJson("add help activity success",help);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 用户帮助一个助力活动
     *
     * 参数
     *      token
     *      helpid
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/user/helpOne", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson helpOne(@RequestBody JSONObject jsonObject){
        try {
            String token ;
            Long helpId ;
            try {
                token = jsonObject.getString("token");
                helpId = jsonObject.getLong("helpid");
                if (token == null || helpId == null){
                    return ResJson.errorRequestParam();
                }
            }catch (IllegalArgumentException ile){
                return ResJson.errorRequestParam();
            }
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                Help help = null;
                if ((help = helpService.findOne(helpId)) == null){
                    return ResJson.failJson(4004,"help activity does not exist",null);
                }
                if (help.getCustomer().getOpenid().equals(customer.getOpenid())){
                    return ResJson.failJson(4005,"无法助力自己发布的活动",null);
                }
                HelpHistory helpHistory = new HelpHistory();
                helpHistory.setCustomer(customer);
                helpHistory.setHelp(help);
                historyService.save(helpHistory);
                return ResJson.successJson("help success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 获取自己的助力活动列表
     *
     * 参数
     *      token
     *      state 0 是未开始 1 是正在进行 2 是已经结束
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/user/getHelpList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson getHelpList(@RequestBody JSONObject jsonObject){
        try {
            String token ;
            Long state ;
            try {
                token = jsonObject.getString("token");
                state = jsonObject.getLong("state");
                if (token == null || state == null){
                    return ResJson.errorRequestParam();
                }
            }catch (IllegalArgumentException ile){
                return ResJson.errorRequestParam();
            }
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                List<Help> helpList = helpService.findAllByCustomer(customer);
                Iterator<Help> iterator = helpList.iterator();
                if (iterator.hasNext()){
                    if (iterator.next().getActivity().getStatus() != state.intValue()){
                        iterator.remove();
                    }
                }
                return ResJson.successJson("get help list activity success",helpList);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 获取某个活动的详情
     *
     * 参数
     *      token
     *      helpid 0 是未开始 1 是正在进行 2 是已经结束
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/user/getHelpDetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson joinGroup(@RequestBody JSONObject jsonObject){
        try {
            String token ;
            Long helpId ;
            try {
                token = jsonObject.getString("token");
                helpId = jsonObject.getLong("helpid");
                if (token == null || helpId == null){
                    return ResJson.errorRequestParam();
                }
            }catch (IllegalArgumentException ile){
                return ResJson.errorRequestParam();
            }
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                Help help = helpService.findOne(helpId);
                if (help == null){
                    return ResJson.failJson(4004,"help activity does not exist",null);
                }else {
                    return ResJson.successJson("get help activity success",help);
                }
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

//    //获取商家其他拼团活动
//    @RequestMapping(value = "/api/user/getHelpRecommendList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//    public ResJson getGroupRecommendList(@RequestBody JSONObject jsonObject){
//        try {
//            String token ;
//            Long activityId ;
//            try {
//                token = jsonObject.getString("token");
//                activityId = jsonObject.getLong("activityid");
//                if (token == null || activityId == null){
//                    return ResJson.errorRequestParam();
//                }
//            }catch (IllegalArgumentException ile){
//                return ResJson.errorRequestParam();
//            }
//            Customer customer = null;
//            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
//                Activity activity = null;
//                if ((activity = activityService.findOne(activityId))==null){
//                    return ResJson.failJson(4001,"the activity id error",null);
//                }
//                Merchant merchant = activity.getMerchant();
//                return ResJson.successJson("get activity detail success",activityService.getAllHelpAct(merchant));
//            }else {
//                return ResJson.errorAccessToken();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResJson.serverErrorJson(e.getMessage());
//        }
//    }
}