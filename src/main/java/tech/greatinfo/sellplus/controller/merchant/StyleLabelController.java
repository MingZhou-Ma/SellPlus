package tech.greatinfo.sellplus.controller.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.greatinfo.sellplus.domain.StyleLabel;
import tech.greatinfo.sellplus.service.StyleLabelService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

@RestController
public class StyleLabelController {

    @Autowired
    StyleLabelService styleLabelService;

    @RequestMapping(value = "/api/mer/styleLabel/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson addStyle(@RequestParam(name = "token") String token,
                              StyleLabel styleLabel) {
        return styleLabelService.addStyleLabel(token, styleLabel);
    }

    @RequestMapping(value = "/api/mer/styleLabel/del", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson delStyle(@RequestParam(name = "token") String token,
                              Long styleLabelId) {
        return styleLabelService.delStyleLabel(token, styleLabelId);
    }

    // 修改
    @RequestMapping(value = "/api/mer/styleLabel/update",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson updateStyle(@RequestParam(name = "token") String token,
                                @ModelAttribute StyleLabel styleLabel){
        return styleLabelService.updateStyleLabel(token, styleLabel);
    }

    @RequestMapping(value = "/api/mer/styleLabel/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson querySiteList(@RequestParam(name = "token") String token,
                                 @RequestParam(name = "start", defaultValue = "0") Integer start,
                                 @RequestParam(name = "num", defaultValue = "10") Integer num) {
        return styleLabelService.queryStyleLabelList(token, start, num);
    }


}
