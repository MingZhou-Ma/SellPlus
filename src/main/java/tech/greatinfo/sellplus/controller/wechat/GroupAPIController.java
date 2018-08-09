package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.group.Group;
import tech.greatinfo.sellplus.domain.group.JoinGroup;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.GroupService;
import tech.greatinfo.sellplus.service.JoinGroupService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 拼团活动 api
 *
 * Created by Ericwyn on 18-7-30.
 */
@RestController
public class GroupAPIController {
    @Autowired
    ActivityService activityService;

    @Autowired
    TokenService tokenService;

    @Autowired
    GroupService groupService;

    @Autowired
    JoinGroupService joinService;


    //用户参团
    @RequestMapping(value = "/api/user/addGroup", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson addActivityGroup(@RequestBody JSONObject jsonObject){
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
                if (!activity.getGroup()){
                    return ResJson.failJson(4003,"not group activity",null);
                }
                // 保存团
                Group group = new Group();
                group.setActivity(activity);
                group.setCustomer(customer);
                group = groupService.save(group);
                // 保存拼团信息
                JoinGroup joinGroup = new JoinGroup();
                joinGroup.setGroup(group);
                joinGroup.setCustomer(customer);
                joinService.save(joinGroup);
                return ResJson.successJson("add group success", group);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 获取团详情
    @RequestMapping(value = "/api/user/getGroupActivityDetail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson getGroupActivityDetail(@RequestBody JSONObject jsonObject){
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
                if (!activity.getGroup()){
                    return ResJson.failJson(4003,"not group activity",null);
                }
                return ResJson.successJson("get activity detail success",activity);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    //加入团
    @RequestMapping(value = "/api/user/joinGroup", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson joinGroup(@RequestBody JSONObject jsonObject){
        try {
            String token ;
            Long groupId ;
            try {
                token = jsonObject.getString("token");
                groupId = jsonObject.getLong("groupid");
                if (token == null || groupId == null){
                    return ResJson.errorRequestParam();
                }
            }catch (IllegalArgumentException ile){
                return ResJson.errorRequestParam();
            }
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                Group group = null;
                if ((group = groupService.findOne(groupId)) == null){
                    return ResJson.failJson(4002,"group id error",null);
                }
                JoinGroup joinGroup = new JoinGroup();
                joinGroup.setCustomer(customer);
                joinGroup.setGroup(group);
                joinService.save(joinGroup);
                return ResJson.successJson("customer join group success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 查看用户是否已经参团
    @RequestMapping(value = "/api/user/isJoinGroup", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson isJoinGroup(@RequestBody JSONObject jsonObject){
        try {
            String token ;
            Long groupId ;
            try {
                token = jsonObject.getString("token");
                groupId = jsonObject.getLong("groupid");
                if (token == null || groupId == null){
                    return ResJson.errorRequestParam();
                }
            }catch (Exception e){
                return ResJson.errorRequestParam();
            }
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                Group group = null;
                if ((group = groupService.findOne(groupId)) == null){
                    return ResJson.failJson(4002,"group id error",null);
                }
                HashMap<String,Integer> map = new HashMap<>();
                if (joinService.findByCustomerAndGroup(customer, group) == null){
                    map.put("join_status",0);
                }else {
                    map.put("join_status",1);
                }
                return ResJson.successJson("get user join group state success",map);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    //获取用户参团活动列表
    @RequestMapping(value = "/api/user/getGroupList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson getGroupList(@RequestBody JSONObject jsonObject){
        try {
            String token ;
            Integer groupStatus ;
            try {
                token = jsonObject.getString("token");
                groupStatus = jsonObject.getInteger("group_status");
                if (token == null || groupStatus == null){
                    return ResJson.errorRequestParam();
                }
            }catch (Exception e){
                return ResJson.errorRequestParam();
            }
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token))!=null){
                List<Group> grouList = groupService.findAllByCustomer(customer);
                Iterator<Group> iterator = grouList.iterator();
                if (iterator.hasNext()){
                    if (iterator.next().getActivity().getStatus().intValue() != groupStatus.intValue()){
                        iterator.remove();
                    }
                }
                return ResJson.successJson("get user group list success",grouList);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

//    //获取商家其他拼团活动
//    @RequestMapping(value = "/api/user/getGroupRecommendList", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
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
//                return ResJson.successJson("get activity detail success",activityService.getAllGroupAct(merchant));
//            }else {
//                return ResJson.errorAccessToken();
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//            return ResJson.serverErrorJson(e.getMessage());
//        }
//    }
}
