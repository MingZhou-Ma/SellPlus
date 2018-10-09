package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.repository.SellerCodeRepository;
import tech.greatinfo.sellplus.service.SellerCodeService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.WeChatUtils;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 销售用户的销售码
 */
@RestController
public class SellerCodeController {

    private static final Logger logger = LoggerFactory.getLogger(SellerCodeController.class);

    private final SellerCodeService sellerCodeService;

    private final TokenService tokenService;

    private final SellerCodeRepository sellerCodeRepository;

    @Autowired
    public SellerCodeController(SellerCodeService sellerCodeService, TokenService tokenService, SellerCodeRepository sellerCodeRepository) {
        this.sellerCodeService = sellerCodeService;
        this.tokenService = tokenService;
        this.sellerCodeRepository = sellerCodeRepository;
    }

    @RequestMapping(value = "/api/sellerCode/addSellerCode", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson addSellerCode(@RequestBody JSONObject jsonObject) {
        try {
            String token = jsonObject.getString("token");
            String page = jsonObject.getString("page");
            String scene = jsonObject.getString("scene");
            if (token == null && page == null && scene == null) {
                return ResJson.errorRequestParam();
            }
            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }
            if (!customer.getbSell()){
                return ResJson.failJson(-1,"没有 seller 权限",null);
            }

            if (null != sellerCodeRepository.findByScene(scene)) {
                return ResJson.failJson(4000, "该销售码名称已存在", null);
            }

            String accessToken = WeChatUtils.getAccessToken();
            if (null == accessToken) {
                return ResJson.successJson("get access token error");
            }

            String path = sellerCodeService.getSellerCode(customer, accessToken, scene, page);
            if (null == path) {
                return ResJson.successJson("get SellerCode from wechat fail");
            }
            if (path.contains("errcode")) {
                return ResJson.failJson(5000, "get SellerCode fail, error : " + path, null);
            }
            /*HashMap<String, String> map = new HashMap<>();
            map.put("path", path);*/
            return ResJson.successJson("get SellerCode success");

        } catch (Exception e) {
            logger.error("/api/sellerCode/addSellerCode -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @RequestMapping(value = "/api/sellerCode/getSellerCodeList", method = RequestMethod.POST)
    public ResJson getSellerCodeList(@RequestBody JSONObject jsonObject) {
        return sellerCodeService.getSellerCodeList(jsonObject);
    }
}
