package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.ActivityService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.SellerSerivce;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.WeChatUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.AccessToken;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 普通用户的 API
 *
 * Created by Ericwyn on 18-8-9.
 */
@RestController
public class CustomerResController {

    public static OkHttpClient client = WeChatUtils.client;

    private static final String appid = "wx0ad95240d57cb5ee";
    private static final String appsecret="07618c31603772e3836d003d2262c87c";

    @Autowired
    ActivityService activityService;

    @Autowired
    CustomService customService;

    @Autowired
    TokenService tokenService;

    @Autowired
    SellerSerivce sellerSerivce;

    // 阅读一篇文章


    // 微信用户登录
    @RequestMapping(value = "/api/user/login",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson wechatLogin(@RequestBody JSONObject jsonParam) {
        String code = jsonParam.getString("code");
        String errMsg = jsonParam.getString("errMsg");
        System.out.println("code:"+code+" errMsg:"+errMsg);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid="+appid+"&secret="+appsecret+"&grant_type=authorization_code&js_code="+code;
        Request request = new Request.Builder()
                .url(url)
                .header("content-type","application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()){
                String resp = response.body().string();
                if (resp.contains("session_key") && resp.contains("openid")){
                    JSONObject obj = JSON.parseObject(resp);
                    Customer customer = null;
                    // 看看有没有旧的已经登录了的 token (但是 sessionkey 已经过期了)
                    AccessToken token = null;
                    if ((token = tokenService.getTokenByCustomOpenId(obj.getString("openid"))) != null){
                        //如果已经能够在 token map 里面找到就证明之前已经登录过了, 所以也一定存入到数据库了
                        token.refresh();
                        ((Customer)token.getUser()).setSessionKey(obj.getString("session_key"));
                        HashMap<String, String> map = new HashMap<String, String>() ;
                        map.put("accessToken",token.getUuid());
                        map.put("uid",((Customer) token.getUser()).getUid());
                        return ResJson.successJson("login Success",map);
                    }

                    // 如果没有旧的已经登录了的 token 的话
                    if ((customer = customService.getByOpenId(obj.getString("openid")))!=null){
                        customer.setSessionKey(obj.getString("session_key"));
                    }else {
                        customer = new Customer();
                        customer.setOpenid(obj.getString("openid"));
                        customer.setSessionKey(obj.getString("session_key"));
                        customer.setUid(UUID.randomUUID().toString().replaceAll("-",""));
                        customer.setbSell(false);
                        customer.setSeller(sellerSerivce.getDefaultSeller());
                        customService.save(customer);
                    }
                    token = new AccessToken();
                    token.setUser(customer);
                    tokenService.saveToken(token);
                    HashMap<String, String> map = new HashMap<String, String>() ;
                    map.put("accessToken",token.getUuid());
                    map.put("uid", customer.getUid());
                    return ResJson.successJson("login Success",map);
                }else {
                    return ResJson.failJson(-1,"获取 openid 失败",null);
                }
            }else {
                return ResJson.failJson(-1,"获取 openid 失败",null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return ResJson.serverErrorJson("无法请求远程 openid");
        }
    }

    // 上传手机号码 (号码上传多次 ????)
    @RequestMapping(value = "/api/cus/updataInfo")
    public ResJson updateInfo(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Customer Customer = (Customer) ParamUtils.getFromJson(jsonObject,"customer",Customer.class);
            // TODO具体的绑定逻辑
            return null;
        }catch (JsonParseException jpe){
            return ResJson.errorRequestParam(jpe.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 获取用户的信息

    // 获取列表

    // 获取

}
