package tech.greatinfo.sellplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.greatinfo.sellplus.service.ArticleService;
import tech.greatinfo.sellplus.util.obj.ResJson;

/**
 *
 * 一些公共调用接口, 例如营销文章的获取
 *
 * Created by Ericwyn on 18-8-9.
 */
@RestController
public class PublicResController {

    @Autowired
    ArticleService articleService;

    @RequestMapping(value = "/api/pub/listArticle", method = RequestMethod.POST)
    public ResJson listArticle(@RequestParam(value = "start",defaultValue = "0") Integer start,
                               @RequestParam(value = "num", defaultValue = "10") Integer num){
        try {
            return ResJson.successJson("list Article success", articleService.findByPage(start, num));
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
