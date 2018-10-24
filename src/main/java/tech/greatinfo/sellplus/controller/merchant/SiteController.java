package tech.greatinfo.sellplus.controller.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.service.SiteService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 场地管理
 */
@RestController
public class SiteController {

    @Autowired
    SiteService siteService;

    @RequestMapping(value = "/api/mer/site/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson addArticle(@RequestParam(name = "token") String token,
                              @RequestParam(name = "siteName") String siteName,
                              @RequestParam(name = "sitePic") String sitePic) {
        return siteService.addSite(token, siteName, sitePic);
    }

    @RequestMapping(value = "/api/mei/site/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson querySiteList(@RequestParam(name = "token") String token,
                                 @RequestParam(name = "start", defaultValue = "0") Integer start,
                                 @RequestParam(name = "num", defaultValue = "10") Integer num) {
        return siteService.querySiteList(token, start, num);
    }


}
