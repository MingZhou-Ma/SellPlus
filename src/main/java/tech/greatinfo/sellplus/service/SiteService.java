package tech.greatinfo.sellplus.service;

import tech.greatinfo.sellplus.domain.Site;
import tech.greatinfo.sellplus.utils.obj.ResJson;

public interface SiteService {

    ResJson addSite(String token, String siteName, String sitePic);

    ResJson querySiteList(String token, Integer start, Integer num);

}
