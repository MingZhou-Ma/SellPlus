package tech.greatinfo.sellplus.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.Poster;
import tech.greatinfo.sellplus.repository.PosterRepository;
import tech.greatinfo.sellplus.service.PosterService;
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
public class PosterServiceImpl implements PosterService {

    @Autowired
    TokenService tokenService;

    @Autowired
    PosterRepository posterRepository;

    /**
     * 添加海报
     *
     * @param token
     * @param poster
     * @return
     */
    @Override
    public ResJson addPoster(String token, Poster poster) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            if (null == poster.getIsPoster()) {
                return ResJson.failJson(4000, "请选择海报或文案", null);
            }
            if (poster.getIsPoster() == 0) {
                if (StringUtils.isEmpty(poster.getCopyWriting())) {
                    return ResJson.failJson(4000, "请输入文案", null);
                }
            } else {
                if (StringUtils.isEmpty(poster.getPic())) {
                    return ResJson.failJson(4000, "请选择图片", null);
                }
            }
            if (null == poster.getType()) {
                return ResJson.failJson(4000, "请输入类型", null);
            }
            posterRepository.save(poster);
            return ResJson.successJson("add poster success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 根据类型和是否为海报查询海报列表
     *
     * @param token
     * @param type
     * @param isPoster
     * @param start
     * @param num
     * @return
     */
    @Override
    public ResJson queryPosterList(String token, Integer type, Integer isPoster, Integer start, Integer num) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            //Page<Poster> page = posterRepository.findAllByType(type, new PageRequest(start, num));
            Page<Poster> page = posterRepository.findAllByTypeAndIsPoster(type, isPoster, new PageRequest(start, num));
            return ResJson.successJson("query poster list success", page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    public void updatePoster(Poster oldEntity, Poster newEntity) {
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
        posterRepository.saveAndFlush(oldEntity);
    }

    @Override
    public ResJson updatePoster(String token, Poster poster) {
        try {
            if (tokenService.getUserByToken(token) != null) {
                if (poster.getId() == null) {
                    return ResJson.failJson(7004, "poster id error", null);
                }
                Poster oldPoster;
                if ((oldPoster = posterRepository.findOne(poster.getId())) == null) {
                    return ResJson.failJson(7003, "无法更新, 权限错误", null);
                }
                updatePoster(oldPoster, poster);
                return ResJson.successJson("update Poster success");
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson deletePoster(String token, Long posterId) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            if (null == posterId) {
                return ResJson.failJson(4000, "请输入海报id", null);
            }
            if (null == posterRepository.getOne(posterId)) {
                return ResJson.failJson(4000, "海报不存在", null);
            }
            posterRepository.delete(posterId);
            return ResJson.successJson("delete poster success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


    @Override
    public ResJson findPosterList(JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            Integer type = (Integer) ParamUtils.getFromJson(jsonObject, "type", Integer.class);
            Integer isPoster = (Integer) ParamUtils.getFromJson(jsonObject, "isPoster", Integer.class);
            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }
            List<Poster> list = posterRepository.findAllByTypeAndIsPoster(type, isPoster);
            return ResJson.successJson("find poster list success", list);
        } catch (JsonParseException jse) {
            return ResJson.errorRequestParam(jse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
