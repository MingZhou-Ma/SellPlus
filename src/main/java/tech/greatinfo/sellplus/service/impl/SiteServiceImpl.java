package tech.greatinfo.sellplus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.Site;
import tech.greatinfo.sellplus.repository.SiteRepository;
import tech.greatinfo.sellplus.service.SiteService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    SiteRepository siteRepository;

    @Autowired
    TokenService tokenService;

    /**
     * 添加场地
     * @param token
     * @param siteName
     * @param sitePic
     * @return
     @Override
     */
    public ResJson addSite(String token, String siteName, String sitePic) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            Site site = new Site();
            site.setSiteName(siteName);
            site.setSitePic(sitePic);
            siteRepository.save(site);
            return ResJson.successJson("add site success", null);
        } catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 场地列表
     * @param token
     * @param start
     * @param num
     * @return
     */
    @Override
    public ResJson querySiteList(String token, Integer start, Integer num) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            Page<Site> page = siteRepository.findAll(new PageRequest(start, num));
            return ResJson.successJson("query site list success", page);
        } catch (Exception e){
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
