package tech.greatinfo.sellplus.service;

import tech.greatinfo.sellplus.domain.Site;
import tech.greatinfo.sellplus.utils.obj.ResJson;

public interface SiteService {

    ResJson addSite(String token, Site site);

    ResJson delSite(String token, Long siteId);

    ResJson updateSite(String token, Site site);

    ResJson querySiteList(String token, Integer start, Integer num);

    ResJson findSiteList();

}
