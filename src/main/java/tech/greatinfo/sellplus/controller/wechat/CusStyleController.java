package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.service.StyleService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 场地管理
 */
@RestController
public class CusStyleController {

    @Autowired
    StyleService styleService;

    @RequestMapping(value = "/api/cus/style/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson findStyleList(@RequestBody JSONObject jsonObject) {
        return styleService.findStyleList(jsonObject);
    }

}
