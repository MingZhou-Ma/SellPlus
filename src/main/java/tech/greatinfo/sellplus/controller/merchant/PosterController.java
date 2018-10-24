package tech.greatinfo.sellplus.controller.merchant;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import tech.greatinfo.sellplus.domain.Poster;
import tech.greatinfo.sellplus.service.PosterService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 海报管理
 */
@RestController
public class PosterController {

    @Autowired
    PosterService posterService;

    @Autowired
    TokenService tokenService;

    @RequestMapping(value = "/api/mer/poster/add", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson addArticle(@RequestParam(name = "token") String token,
                              Poster poster) {
        return posterService.addPoster(token, poster);
    }

    @RequestMapping(value = "/api/mei/poster/list", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson querySiteList(@RequestParam(name = "token") String token,
                                 @RequestParam(name = "type") Integer type,
                                 @RequestParam(name = "start", defaultValue = "0") Integer start,
                                 @RequestParam(name = "num", defaultValue = "10") Integer num) {
        return posterService.queryPosterList(token, type, start, num);
    }

    // 修改海报
    @RequestMapping(value = "/api/mer/poster/update",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson updatePoster(@RequestParam(name = "token") String token,
                                      @ModelAttribute Poster poster ){
        return posterService.updatePoster(token, poster);
    }

    // 删除海报
    @RequestMapping(value = "/api/mer/poster/del",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson delPoster(@RequestParam(name = "token") String token,
                               @RequestParam(name = "posterId") Long posterId){
        return posterService.deletePoster(token, posterId);
    }


}
