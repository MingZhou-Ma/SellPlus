package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.SellerSerivce;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * Created by Ericwyn on 18-8-14.
 */
@RestController
public class CusSellerResController {
    @Autowired
    private TokenService tokenService;

    @Autowired
    private SellerSerivce sellerSerivce;

    @Autowired
    CustomService customService;

    // 绑定成为 Seller
    @RequestMapping(value = "/api/cus/beSeller",method = RequestMethod.POST)
    public ResJson beSeller(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            String selleAccount = (String) ParamUtils.getFromJson(jsonObject,"account", String.class);
            String key = (String) ParamUtils.getFromJson(jsonObject, "key", String.class);
            String name = (String) ParamUtils.getFromJson(jsonObject, "name", String.class);
            String phone = (String) ParamUtils.getFromJson(jsonObject, "phone", String.class);
            Customer customer ;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Seller seller;
                if ((seller = sellerSerivce.findByAccountAndSellerKey(selleAccount, key)) != null
                        && (seller.getOpenId() == null || seller.getOpenId().equals(""))){
                    // 保存 seller 信息
                    seller.setOpenId(customer.getOpenid());
                    seller.setName(name);
                    seller.setPhone(phone);
                    sellerSerivce.save(seller);
                    // 成为 Seller
                    customer.setbSell(true);
                    customer.setSeller(seller);
                    customer.setUid(customer.getUid());
                    customService.save(customer);
                    return ResJson.successJson("set seller info success");
                }else {
                    return ResJson.failJson(-1,"seller info error",null);
                }
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jpe){
            return ResJson.errorRequestParam(jpe.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     *
     * 绑定上级 Seller
     *
     * 所有人默认的绑定都是id为 1 的默认 seller
     * 而 seller 本身，在成为 seller 的时候，绑定的 seller 就是自己了
     *
     * 所有人的分享页面都带有自己帐号的 uuid，前端发送 token 和 uid 来调用绑定接口
     * 若是某个页面没有 uid 的话，该参数需要发送为 “null”
     *
     * 回溯寻找 Seller 的时候， 只会回溯到 uid 的用户， 如果 uid 的用户没有绑定 Seller
     *      那么当前用户和 uid 用户都会绑定默认的 Seller
     *
     * 如果 uid 的用户已经绑定了非默认 Seller ， 那么当前用户就绑定同一个 Seller
     *
     * 如果之前已经绑定了默认 Seller，当前可以绑定新的 Seller
     * 如果之前已经有绑定 Seller ， 那么不做处理
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/bindSeller",method = RequestMethod.POST)
    public ResJson bindSeller(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            String uid = (String) ParamUtils.getFromJson(jsonObject,"uid", String.class);

            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                // 已经绑定了非默认 Seller
                if (customer.getSeller() != null
                        && !customer.getSeller().equals(sellerSerivce.getDefaultSeller())){
                    return ResJson.successJson("已经绑定了非默认 Seller");
                }else {
                    Seller seller;
                    Customer preCustomer;
                    if ((preCustomer = customService.getByUid(uid)) != null){
                        if ((seller = preCustomer.getSeller()) != null){
                            customer.setSeller(seller);
                            customService.save(customer);
                        }else {
                            preCustomer.setSeller(sellerSerivce.getDefaultSeller());
                            customer.setSeller(sellerSerivce.getDefaultSeller());
                            customService.save(preCustomer);
                            customService.save(customer);
                        }
                    }else {
                        customer.setSeller(sellerSerivce.getDefaultSeller());
                        customService.save(customer);
                    }
                }
                return null;
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jpe){
            return ResJson.errorRequestParam(jpe.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 查看自己的 Seller 信息
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/showMySeller",method = RequestMethod.POST)
    public ResJson showMySeller(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Seller res;
                if ((res = customer.getSeller()) == null){
                    res = sellerSerivce.getDefaultSeller();
                }
                return ResJson.successJson("get seller info success", res);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jpe){
            return ResJson.errorRequestParam(jpe.getMessage());
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // TODO 查看用户是不是 Seller



}
