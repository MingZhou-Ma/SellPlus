package tech.greatinfo.sellplus.controller.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.service.StyleLabelService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 场地管理
 */
@RestController
public class CusStyleLabelController {

    @Autowired
    StyleLabelService styleLabelService;

    @RequestMapping(value = "/api/cus/styleLabel/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson findStyleList() {
        return styleLabelService.findStyleLabelList();
    }

}
