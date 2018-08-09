package tech.greatinfo.sellplus.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tech.greatinfo.sellplus.domain.article.Article;
import tech.greatinfo.sellplus.service.ArticleService;
import tech.greatinfo.sellplus.service.MerchantService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * Created by Ericwyn on 18-8-9.
 */
@RestController
public class ArticleResController {
    @Autowired
    ArticleService articleService;

    @Autowired
    TokenService tokenService;

    @Autowired
    MerchantService merchantService;

    // 文章获取接口在 PublicResController

    // 增加文章
    @RequestMapping(value = "/api/mer/addArticle",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson addArticle(@RequestParam(name = "token") String token,
                               @ModelAttribute Article article){
        try {
            if (tokenService.getUserByToken(token) != null){
                return ResJson.successJson("add Article Success", articleService.save(article));
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 删除文章
    @RequestMapping(value = "/api/mer/delArticle",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson addActivity(@RequestParam(name = "token") String token,
                               @RequestParam(name = "articleid") Long articleId){
        try {
            if (tokenService.getUserByToken(token) != null){
                articleService.delete(articleId);
                return ResJson.successJson("delete success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
