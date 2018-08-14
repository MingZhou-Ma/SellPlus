package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.SqlResultSetMapping;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.ProductService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 微信用户 商品相关接口
 *
 * Created by Ericwyn on 18-8-13.
 */
@RestController
public class CusProResController {
    @Autowired
    TokenService tokenService;

    @Autowired
    ProductService productService;

    // 用户 获取产品列表
    @RequestMapping(value = "/api/cus/listProduct",method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson findActivity(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            if (tokenService.getUserByToken(token) != null){
                // TODO 分页查询
                return ResJson.successJson("get product list success",productService.findAllByPages(0,999));
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

    // 商品详情接口在 public Res controller



}
