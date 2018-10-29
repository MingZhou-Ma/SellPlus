package tech.greatinfo.sellplus.controller.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.greatinfo.sellplus.domain.Site;
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
    public ResJson addSite(@RequestParam(name = "token") String token,
                           Site site) {
        return siteService.addSite(token, site);
    }

    @RequestMapping(value = "/api/mer/site/del", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson delSite(@RequestParam(name = "token") String token,
                           Long siteId) {
        return siteService.delSite(token, siteId);
    }

    // 修改
    @RequestMapping(value = "/api/mer/site/update", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson updateSite(@RequestParam(name = "token") String token,
                              @ModelAttribute Site site) {
        return siteService.updateSite(token, site);
    }

    @RequestMapping(value = "/api/mer/site/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson querySiteList(@RequestParam(name = "token") String token,
                                 @RequestParam(name = "start", defaultValue = "0") Integer start,
                                 @RequestParam(name = "num", defaultValue = "10") Integer num) {
        return siteService.querySiteList(token, start, num);
    }


}
