package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.QRcode;
import tech.greatinfo.sellplus.repository.QRcodeRepository;
import tech.greatinfo.sellplus.service.PosterService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.util.HashMap;

/**
 * 海报管理
 */
@RestController
public class CusPosterController {

    private static final Logger logger = LoggerFactory.getLogger(CusPosterController.class);

    @Autowired
    PosterService posterService;

    @Autowired
    TokenService tokenService;

    @Autowired
    QRcodeRepository qRcodeRepository;

//    @RequestMapping(value = "/api/cus/poster/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
//    public ResJson querySiteList(@RequestParam(name = "token") String token,
//                                 @RequestParam(name = "type") Integer type,
//                                 @RequestParam(name = "isPoster") Integer isPoster) {
//        return posterService.findPosterList(token, type, isPoster);
//    }

    @RequestMapping(value = "/api/cus/poster/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson findPosterList(@RequestBody JSONObject jsonObject) {
        return posterService.findPosterList(jsonObject);
    }

    @RequestMapping(value = "/api/cus/poster/scanPoster", method = RequestMethod.POST)
    public ResJson scanSellerCode(@RequestBody JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            String scene = (String) ParamUtils.getFromJson(jsonObject, "scene", String.class);
            String type = (String) ParamUtils.getFromJson(jsonObject, "type", String.class);

            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }

            QRcode qRcode = qRcodeRepository.findBySceneAndType(scene, type);
            if (null == qRcode) {
                return ResJson.failJson(4000, "二维码不存在", null);
            }
            HashMap<String, String> map = new HashMap<>();
            map.put("uid", qRcode.getCustomer().getUid());
            if (StringUtils.isNotBlank(qRcode.getSellerChannel())) {
                map.put("id", qRcode.getSellerChannel().split("\\|")[0]);
            } else {
                map.put("id", "");
            }

            return ResJson.successJson("success", map);
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/cus/poster/scanPoster");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/cus/poster/scanPoster");
        } catch (Exception e) {
            logger.error("/api/cus/poster/scanPoster ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
