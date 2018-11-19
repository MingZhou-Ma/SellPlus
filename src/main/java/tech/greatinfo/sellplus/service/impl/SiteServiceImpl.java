package tech.greatinfo.sellplus.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.Site;
import tech.greatinfo.sellplus.repository.SiteRepository;
import tech.greatinfo.sellplus.service.SiteService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

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

    @Override
    public ResJson addSite(String token, Site site) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            if (null == site.getSiteName()) {
                return ResJson.failJson(4000, "请输入场地名", null);
            }
            if (StringUtils.isEmpty(site.getSiteAddress())) {
                return ResJson.failJson(4000, "请输入场地详细地址", null);
            }
            if (StringUtils.isEmpty(site.getLatitude())) {
                return ResJson.failJson(4000, "请输入纬度", null);
            }
            if (StringUtils.isEmpty(site.getLongitude())) {
                return ResJson.failJson(4000, "请输入经度", null);
            }
            if (null == site.getSitePic()) {
                return ResJson.failJson(4000, "请选择头图", null);
            }
            if (StringUtils.isEmpty(site.getDescription())) {
                return ResJson.failJson(4000, "请输入场地描述", null);
            }
            siteRepository.save(site);
            return ResJson.successJson("add site success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson delSite(String token, Long siteId) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            if (null == siteId) {
                return ResJson.failJson(4000, "请输入场地id", null);
            }
            Site site = siteRepository.findOne(siteId);
            if (null == site) {
                return ResJson.failJson(4000, "场地不存在", null);
            }
            siteRepository.delete(site);
            return ResJson.successJson("delete site success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    public void updateSite(Site oldEntity, Site newEntity) {
        Field[] fields = newEntity.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                boolean access = field.isAccessible();
                if (!access) field.setAccessible(true);
                Object o = field.get(newEntity);
                //静态变量不操作,这样的话才不会报错
                if (o != null && !Modifier.isStatic(field.getModifiers())) {
                    field.set(oldEntity, o);
                }
                if (!access) field.setAccessible(false);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        siteRepository.saveAndFlush(oldEntity);
    }

    @Override
    public ResJson updateSite(String token, Site site) {
        try {
            if (tokenService.getUserByToken(token) != null) {
                if (site.getId() == null) {
                    return ResJson.failJson(7004, "site id error", null);
                }
                Site oldSite;
                if ((oldSite = siteRepository.findOne(site.getId())) == null) {
                    return ResJson.failJson(7003, "无法更新, 权限错误", null);
                }
                updateSite(oldSite, site);
                return ResJson.successJson("update Site success");
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 场地列表
     *
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
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson findSiteList() {
        try {
            List<Site> list = siteRepository.findAll();
            return ResJson.successJson("find site list success", list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson findSiteDetail(JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            Long siteId = (Long) ParamUtils.getFromJson(jsonObject, "siteId", Long.class);
            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }
            Site site = siteRepository.findOne(siteId);
            if (null == site) {
                return ResJson.failJson(4000, "场地不存在", null);
            }
            return ResJson.successJson("find site detail success", site);
        } catch (JsonParseException jse) {
            return ResJson.errorRequestParam(jse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
