package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.article.Article;
import tech.greatinfo.sellplus.domain.article.ReadHistory;
import tech.greatinfo.sellplus.service.ArticleService;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.ReadHistoryService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 用户端的活动相关接口
 *
 * Created by Ericwyn on 18-8-13.
 */
@RestController
public class CusArticleController {

    private static final Logger logger = LoggerFactory.getLogger(CusArticleController.class);

    @Autowired
    ArticleService articleService;

    @Autowired
    CustomService customService;

    @Autowired
    TokenService tokenService;

    @Autowired
    ReadHistoryService readHistoryService;

    /**
     * 用户增加阅读历史
     *
     * POST
     *      token
     *      articleid 文章的 id
     *
     * TODO，这里的话用户阅读一次就一定会载入，可能对服务器不太友好，后期尝试短期内多个同样的阅读，记录在内存或者 redis 当中
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/read",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson findActivity(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token",String.class);
            Long articleId = (Long) ParamUtils.getFromJsonWithDefault(jsonObject, "articleid",0,Long.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Article article ;
                if ((article = articleService.findOne(articleId))!=null){
                    ReadHistory readHistory = new ReadHistory();
                    readHistory.setCustom(customer);
                    readHistory.setArticle(article);
                    readHistory.setReadTime(new Date());
                    readHistoryService.save(readHistory);
                    return ResJson.successJson("save readHistory success");
                }else {
                    return ResJson.failJson(-1,"article id error",null);
                }
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> api/cus/read");
            return ResJson.errorRequestParam(jse.getMessage()+" -> api/cus/read");
        }catch (Exception e){
            logger.error("api/cus/read -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
