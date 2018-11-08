package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.domain.help.Help;
import tech.greatinfo.sellplus.domain.help.HelpHistory;
import tech.greatinfo.sellplus.service.*;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 *
 * 助力活动 api
 *
 * Created by Ericwyn on 18-7-27.
 */
@RestController
public class CusHelpController {
    private static final Logger logger = LoggerFactory.getLogger(CusHelpController.class);

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

    @Autowired
    CouponsObjService objService;

    /**
     *
     * 发起一个助力活动
     *
     * 参数:
     *      token
     *      activityid
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/addHelp", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson addHelp(@RequestBody JSONObject jsonObject){
        try {
            String token  = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            Long activityId = (Long) ParamUtils.getFromJson(jsonObject, "activityid", Long.class);
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                Activity activity = null;
                if ((activity = activityService.findOne(activityId))==null){
                    return ResJson.failJson(4001,"the activity id error",null);
                }
                if (activity.getGroup()){
                    return ResJson.failJson(4003,"not help activity",null);
                }
                Help help;
                if ((help = helpService.findByCustomerAndActivity(customer, activity))!= null){
                    HashMap<String,Object> map = new HashMap<>();
                    map.put("id",help.getId());
                    return ResJson.failJson(4006,"该用户已经发布过该活动",map);
                }
                help = new Help();
                help.setActivity(activity);
                help.setCustomer(customer);
                help.setGeneral(false);
                helpService.save(help);
                return ResJson.successJson("add help activity success",help);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/cus/addHelp");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/cus/addHelp");
        }catch (Exception e){
            logger.error("/api/cus/addHelp -> ",e.getMessage());
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
    @RequestMapping(value = "/api/cus/helpOne", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson helpOne(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token",String.class);
            Long helpId = (Long) ParamUtils.getFromJson(jsonObject, "helpid", Long.class);
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                Help help = null;
                if ((help = helpService.findOne(helpId)) == null){
                    return ResJson.failJson(4004,"help activity does not exist",null);
                }
                if (help.getCustomer().getOpenid().equals(customer.getOpenid())){
                    return ResJson.failJson(4005,"无法助力自己发布的活动",null);
                }
                if (historyService.findByCustomerAndHelp(customer,help) != null){
                    return ResJson.failJson(4007,"have help this",null);
                }
                HelpHistory helpHistory = new HelpHistory();
                helpHistory.setCustomer(customer);
                helpHistory.setHelp(help);
                historyService.save(helpHistory);
                return ResJson.successJson("help success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/cus/helpOne");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/cus/helpOne");
        }catch (Exception e){
            logger.error("/api/cus/helpOne -> ",e.getMessage());
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
    @RequestMapping(value = "/api/cus/getHelpList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson getHelpList(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token",String.class);
            //Long state = (Long) ParamUtils.getFromJson(jsonObject, "status", Long.class);
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                List<Help> helpList = helpService.findAllByCustomer(customer);
//                Iterator<Help> iterator = helpList.iterator();
//                if (iterator.hasNext()){
//                    if (iterator.next().getActivity().getStatus() != state.intValue()){
//                        iterator.remove();
//                    }
//                }
                return ResJson.successJson("get help list activity success",helpList);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/cus/getHelpList");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/cus/getHelpList");
        }catch (Exception e){
            logger.error(" /api/cus/getHelpList -> ",e.getMessage());
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
    @RequestMapping(value = "/api/cus/getHelpDetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson joinGroup(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Long helpId = (Long) ParamUtils.getFromJson(jsonObject,"helpid", Long.class);

            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                Help help = helpService.findOne(helpId);
                if (help == null){
                    return ResJson.failJson(4004,"help activity does not exist",null);
                }else {
                    // 设置是否是发起人
                    help.setIsOwner(help.getCustomer().getOpenid().equals(customer.getOpenid())?1:0);
                    // 设置是否已经助力
                    help.setIsHelp(historyService.findByCustomerAndHelp(customer,help) != null?1:0);
                    // 设置已经助力的人数
                    help.setHelpCount(historyService.findAllByHelp(help).size());
                    return ResJson.successJson("get help activity success",help);
                }
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/cus/getHelpDetail");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/cus/getHelpDetail");
        }catch (Exception e){
            logger.error("/api/cus/getHelpDetail -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 助力活动发券
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/obtainHelpCoupon", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson obtainHelpCoupon(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Long activityId = (Long) ParamUtils.getFromJson(jsonObject,"activityId", Long.class);

            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }
            Activity activity = activityService.findOne(activityId);
            if (null == activity) {
                return ResJson.failJson(4000, "活动不存在", null);
            }

            Help help = helpService.findByCustomerAndActivity(customer, activity);
            if (null == help) {
                return ResJson.failJson(4000, "助力活动不存在", null);
            }
            if (help.isGeneral()) {
                return ResJson.failJson(4000, "已经领取过该券", null);
            }
            if (historyService.findAllByHelp(help).size() < activity.getHelpNum()) {
                return ResJson.failJson(4000, "未达到助力人数", null);
            }

            //发放助力活动券
            CouponsObj couponsObj = new CouponsObj();
            couponsObj.setCoupon(activity.getCoupon());
            couponsObj.setCode(objService.getRandomCouponCode());
            //couponsObj.setOrigin(freq);
            couponsObj.setOwn(customer);
            couponsObj.setNote("助力活动发放的优惠卷");
            couponsObj.setGeneralTime(new Date());
            couponsObj.setExpired(false);
            objService.save(couponsObj);

            // 将该助力活动设置为已经领取过券
            help.setGeneral(true);
            helpService.save(help);

            return ResJson.successJson("领取成功");
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/cus/getHelpDetail");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/cus/getHelpDetail");
        }catch (Exception e){
            logger.error("/api/cus/getHelpDetail -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}