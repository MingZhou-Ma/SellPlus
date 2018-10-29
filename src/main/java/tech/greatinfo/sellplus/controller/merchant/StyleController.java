package tech.greatinfo.sellplus.controller.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.greatinfo.sellplus.domain.Style;
import tech.greatinfo.sellplus.service.StyleService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

@RestController
public class StyleController {

    @Autowired
    StyleService styleService;

    @RequestMapping(value = "/api/mer/style/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson addStyle(@RequestParam(name = "token") String token,
                              Style style) {
        return styleService.addStyle(token, style);
    }

    @RequestMapping(value = "/api/mer/style/del", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson delStyle(@RequestParam(name = "token") String token,
                              Long styleId) {
        return styleService.delStyle(token, styleId);
    }

    // 修改
    @RequestMapping(value = "/api/mer/style/update",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson updateStyle(@RequestParam(name = "token") String token,
                                @ModelAttribute Style style ){
        return styleService.updateStyle(token, style);
    }

    @RequestMapping(value = "/api/mer/style/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson querySiteList(@RequestParam(name = "token") String token,
                                 @RequestParam(name = "start", defaultValue = "0") Integer start,
                                 @RequestParam(name = "num", defaultValue = "10") Integer num) {
        return styleService.queryStyleList(token, start, num);
    }


}
