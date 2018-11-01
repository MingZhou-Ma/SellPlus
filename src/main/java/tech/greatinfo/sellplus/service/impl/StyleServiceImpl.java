package tech.greatinfo.sellplus.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.Style;
import tech.greatinfo.sellplus.repository.StyleRepository;
import tech.greatinfo.sellplus.service.StyleService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;
import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
@Service
public class StyleServiceImpl implements StyleService {

    @Autowired
    StyleRepository styleRepository;

    @Autowired
    TokenService tokenService;

    @Override
    public ResJson addStyle(String token, Style style) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            if (null == style.getTitle()) {
                return ResJson.failJson(4000, "请输入标题", null);
            }
            if (null == style.getPic()) {
                return ResJson.failJson(4000, "请选择头图", null);
            }
            if (null == style.getDescription()) {
                return ResJson.failJson(4000, "请选择描述", null);
            }
            if (null == style.getType()) {
                return ResJson.failJson(4000, "请选择类型", null);
            }
            style.setTime(new Date());
            styleRepository.save(style);
            return ResJson.successJson("add style success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson delStyle(String token, Long styleId) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            if (null == styleId) {
                return ResJson.failJson(4000, "请输入风采id", null);
            }
            Style style = styleRepository.findOne(styleId);
            if (null == style) {
                return ResJson.failJson(4000, "风采不存在", null);
            }
            styleRepository.delete(style);
            return ResJson.successJson("delete style success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    public void updateStyle(Style oldEntity, Style newEntity) {
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
        styleRepository.saveAndFlush(oldEntity);
    }

    @Override
    public ResJson updateStyle(String token, Style style) {
        try {
            if (tokenService.getUserByToken(token) != null) {
                if (style.getId() == null) {
                    return ResJson.failJson(7004, "style id error", null);
                }
                Style oldStyle;
                if ((oldStyle = styleRepository.findOne(style.getId())) == null) {
                    return ResJson.failJson(7003, "无法更新, 权限错误", null);
                }
                updateStyle(oldStyle, style);
                return ResJson.successJson("update Style success");
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson queryStyleList(String token, Integer start, Integer num) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            Page<Style> page = styleRepository.findAll(new PageRequest(start, num));
            return ResJson.successJson("query style list success", page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson findStyleList(JSONObject jsonObject) {
        try {
            //String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            Integer type = (Integer) ParamUtils.getFromJson(jsonObject, "type", Integer.class);
//            Customer customer = (Customer) tokenService.getUserByToken(token);
//            if (null == customer) {
//                return ResJson.errorAccessToken();
//            }
            List<Style> list = styleRepository.findAllByType(type);
            return ResJson.successJson("find style list success", list);
        } catch (JsonParseException jse) {
            return ResJson.errorRequestParam(jse.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
