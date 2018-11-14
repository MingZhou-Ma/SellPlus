package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.greatinfo.sellplus.service.SiteService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 场地管理
 */
@RestController
public class CusSiteController {

    @Autowired
    SiteService siteService;

    @RequestMapping(value = "/api/cus/site/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson findSiteList() {
        return siteService.findSiteList();
    }

    @RequestMapping(value = "/api/cus/site/detail", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson findSiteDetail(@RequestBody JSONObject jsonObject) {
        return siteService.findSiteDetail(jsonObject);
    }

}
