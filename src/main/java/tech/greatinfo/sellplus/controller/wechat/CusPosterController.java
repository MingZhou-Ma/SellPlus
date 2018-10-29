package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.service.PosterService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 海报管理
 */
@RestController
public class CusPosterController {

    @Autowired
    PosterService posterService;

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

}
