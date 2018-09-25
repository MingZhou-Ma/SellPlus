package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.*;
import tech.greatinfo.sellplus.utils.*;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.AccessToken;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.UUID;

/**
 * 普通用户的 API
 * <p>
 * Created by Ericwyn on 18-8-9.
 */
@RestController
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);

    public static OkHttpClient client = WeChatUtils.client;

    /*private static final String appid = "wxa305d3fc1a539d2d";
    private static final String appsecret = "c6535d168b4688517a46a543b5cb9161";*/
    @Value("${appid}")
    private String appid;

    @Value("${appsecret}")
    private String appsecret;


    @Autowired
    ActivityService activityService;

    @Autowired
    CustomService customService;

    @Autowired
    TokenService tokenService;

    @Autowired
    SellerSerivce sellerSerivce;

    @Autowired
    QRcodeService qrService;

    // 阅读一篇文章


    // 微信用户登录

    /**
     * 微信用户登录
     * <p>
     * POST
     * code        前台调用微信接口返回
     * errMsg      同上
     *
     * @param jsonParam
     * @return
     */
    @RequestMapping(value = "/api/cus/login", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson wechatLogin(@RequestBody JSONObject jsonParam) {
        String code = jsonParam.getString("code");
        String errMsg = jsonParam.getString("errMsg");
        System.out.println("code:" + code + " errMsg:" + errMsg);
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + appid + "&secret=" + appsecret + "&grant_type=authorization_code&js_code=" + code;
        Request request = new Request.Builder()
                .url(url)
                .header("content-type", "application/json")
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                String resp = response.body().string();
                if (resp.contains("session_key") && resp.contains("openid")) {
                    JSONObject obj = JSON.parseObject(resp);
                    Customer customer = null;
                    // 看看有没有旧的已经登录了的 token (但是 sessionkey 已经过期了)
                    AccessToken token = null;
                    if ((token = tokenService.getTokenByCustomOpenId(obj.getString("openid"))) != null) {
                        //如果已经能够在 token map 里面找到就证明之前已经登录过了, 所以也一定存入到数据库了
                        token.refresh();
                        ((Customer) token.getUser()).setSessionKey(obj.getString("session_key"));

                        // 更新sessionKey，不然授权手机号无法解密
                        token.setUser(token.getUser());
                        tokenService.saveToken(token);

                        HashMap<String, String> map = new HashMap<String, String>();
                        map.put("accessToken", token.getUuid());
                        map.put("uid", ((Customer) token.getUser()).getUid());
                        return ResJson.successJson("login Success", map);
                    }

                    // 如果没有旧的已经登录了的 token 的话
                    if ((customer = customService.getByOpenId(obj.getString("openid"))) != null) {
                        customer.setSessionKey(obj.getString("session_key"));
                    } else {
                        customer = new Customer();
                        customer.setOpenid(obj.getString("openid"));
                        customer.setSessionKey(obj.getString("session_key"));
                        customer.setUid(UUID.randomUUID().toString().replaceAll("-", ""));
                        customer.setbSell(false);
                        customer.setFrequenter(false);
                        customer.setSeller(sellerSerivce.getDefaultSeller());
                        /*customer.setType(1);  // 用户类型：1代表潜在客户   2代表老客户
                        customer.setOrigin(""); // 用户来源：默认什么鬼
                        */
                        customer.setCreateTime(new Date());
                        customService.save(customer);
                    }
                    token = new AccessToken(true);
                    token.setUser(customer);
                    tokenService.saveToken(token);
//                    token = tokenService.saveToken(customer);
                    HashMap<String, String> map = new HashMap<>();
                    map.put("accessToken", token.getUuid());
                    map.put("uid", customer.getUid());
                    return ResJson.successJson("login Success", map);
                } else {
                    return ResJson.failJson(-1, "获取 openid 失败", null);
                }
            } else {
                return ResJson.failJson(-1, "获取 openid 失败", null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("api/cus/listActivity -> ", e.getMessage());
            return ResJson.serverErrorJson("无法请求远程 openid");
        }
    }

    /**
     * 该接口用来检查 accessToken 是否已经过期
     * <p>
     * POST
     * token 旧的 accessToken
     * <p>
     * 只会返回状态而不会返回新的 accessToken
     * 若要能够返回新的 accessToken 的话需要修改登录接口， 返回 AES128 双重加密的可逆 accessToken
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/checkToken", method = RequestMethod.POST)
    public ResJson checkToken(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            HashMap<String, Integer> map = new HashMap<>();
            if (tokenService.getUserByToken(token) != null) {
                return ResJson.successJson("token good");
            } else {
                return ResJson.errorAccessToken();
//                String openid;
//                if ((openid = tokenService.getOpenIdFromOldToken(token)) != null){
//                    System.out.println("oldToken"+token);
//                    System.out.println("get new Access Token");
//                   HashMap<String,String> resMap = new HashMap<>();
//                    Customer customer = customService.getByOpenId(openid);
//                    System.out.println("find old customer");
//                    AccessToken newToken = tokenService.saveToken(customer);
//                    resMap.put("accessToken",newToken.getUuid());
//                    return ResJson.failJson(-1, "token new", resMap);
//                }else {
//                    return ResJson.errorAccessToken();
//                }
            }
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/cus/checkToken");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/cus/checkToken");
        } catch (Exception e) {
            logger.error("/api/cus/checkToken -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 授权用户手机号码
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/getPhone", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson getCustomerPhone(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            String encryptedData = (String) ParamUtils.getFromJson(jsonObject, "encryptedData", String.class);
            String iv = (String) ParamUtils.getFromJson(jsonObject, "iv", String.class);

            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }

            System.out.println(encryptedData);
            System.out.println(iv);
            System.out.println(customer.getSessionKey());

            String decrypt = AES.wxDecrypt(encryptedData, customer.getSessionKey(), iv);
            System.out.println(decrypt);

            JSONObject decryptJsonObject = JSON.parseObject(decrypt);
            String phoneNumber = decryptJsonObject.get("phoneNumber").toString();
            System.out.println(phoneNumber);
            customer.setPhone(phoneNumber);
            customService.save(customer);

            AccessToken accessToken = tokenService.getToken(token);
            accessToken.setUser(customer);
            tokenService.saveToken(accessToken);


            return ResJson.successJson("getPhone Success", decrypt);
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/cus/getPhone");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/cus/getPhone");
        } catch (Exception e) {
            logger.error("/api/cus/getPhone -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 判断客户是否授权手机号
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/isAuthPhone", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson isAuthPhone(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);

            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }

            System.out.println(customer.toString());

            if (StringUtils.isEmpty(customer.getPhone())) {
                return ResJson.successJson("not auth", false);
            } else {
                return ResJson.successJson("auth", true);
            }

        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/cus/isAuthPhone");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/cus/isAuthPhone");
        } catch (Exception e) {
            logger.error("/api/cus/isAuthPhone", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    /**
     * 用户上传个人手机号码, 阅读营销文章时候可用
     * <p>
     * POST
     * token   用户登录凭证
     * phone   电话号码
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/setCustomerInfo")
    public ResJson updateInfo(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            String phone = (String) ParamUtils.getFromJson(jsonObject, "phone", String.class);
            String phoneOrigin = (String) ParamUtils.getFromJson(jsonObject, "phoneOrigin", String.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null) {
                // 手机号码的验证
                if (!PhoneUtil.isPhone(phone)) {
                    return ResJson.failJson(1111, "error phone", null);
                }
                customer.setPhone(phone);
                customer.setPhoneOrigin(phoneOrigin);
                customService.save(customer);
                return ResJson.successJson("set CustomerInfo Success");
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/cus/setCustomerInfo");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/cus/setCustomerInfo");
        } catch (Exception e) {
            logger.error("/api/cus/setPhone -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 获取用户信息
     * <p>
     * POST
     * token
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/getCusInfo")
    public ResJson getCusInfo(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null) {
                return ResJson.successJson("get customer info success", customer);
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/cus/getCusInfo");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/cus/getCusInfo");
        } catch (Exception e) {
            logger.error("/api/cus/getCusInfo -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 获取二维码图片接口
     * <p>
     * 参数
     * token
     * //title   海报标题
     * page    跳转的页面
     * scene   跳转所带参数
     *
     * @param response
     * @param jsonObject
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/api/cus/getQRcode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson getQRCode(HttpServletResponse response, @RequestBody JSONObject jsonObject) throws IOException {
        try {
            String token;
            //String title;
            String page;
            String scene;
            try {
                token = jsonObject.getString("token");
                //title = jsonObject.getString("title");
                page = jsonObject.getString("page");
                scene = jsonObject.getString("scene");
                //if (token == null && title == null && page == null && scene == null){
                if (token == null && page == null && scene == null) {
                    return ResJson.errorRequestParam();
                }
            } catch (Exception e) {
                return ResJson.errorRequestParam();
            }
            Customer customer = null;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null) {
                String accessToken = WeChatUtils.getAccessToken();
                if (accessToken != null) {
                    String path = qrService.getQRCode(customer, accessToken, scene, page);
                    if (path != null) {
                        if (path.contains("errcode")) {
                            return ResJson.failJson(5000, "get QR code fail, error : " + path, null);
                        }
                        HashMap<String, String> map = new HashMap<>();
                        map.put("path", path);
                        return ResJson.successJson("get QR code success", map);
                    } else {
                        return ResJson.successJson("get QRcode from wechat fail");
                    }
                } else {
                    return ResJson.successJson("get access token error");
                }
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            logger.error("/api/cus/getQRcode -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
