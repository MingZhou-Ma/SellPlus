package tech.greatinfo.sellplus.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.StyleLabel;
import tech.greatinfo.sellplus.repository.StyleLabelRepository;
import tech.greatinfo.sellplus.service.StyleLabelService;
import tech.greatinfo.sellplus.service.TokenService;
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
public class StyleLabelServiceImpl implements StyleLabelService {

    @Autowired
    StyleLabelRepository styleLabelRepository;

    @Autowired
    TokenService tokenService;

    @Override
    public ResJson addStyleLabel(String token, StyleLabel styleLabel) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            if (null == styleLabel.getLabelName()) {
                return ResJson.failJson(4000, "请输入标签名", null);
            }
            List<StyleLabel> list = styleLabelRepository.findAll();
            if (null != list && !list.isEmpty()) {
                StyleLabel label = list.get(0);
                label.setLabelName(label.getLabelName() + "," + styleLabel.getLabelName());
                styleLabelRepository.save(label);
            } else {
                styleLabelRepository.save(styleLabel);
            }
            return ResJson.successJson("add styleLabel success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson delStyleLabel(String token, Long styleLabelId) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            if (null == styleLabelId) {
                return ResJson.failJson(4000, "请输入风采标签id", null);
            }
            StyleLabel styleLabel = styleLabelRepository.findOne(styleLabelId);
            if (null == styleLabel) {
                return ResJson.failJson(4000, "风采标签不存在", null);
            }
            styleLabelRepository.delete(styleLabel);
            return ResJson.successJson("delete style success", null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    public void updateStyleLabel(StyleLabel oldEntity, StyleLabel newEntity) {
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
        styleLabelRepository.saveAndFlush(oldEntity);
    }

    @Override
    public ResJson updateStyleLabel(String token, StyleLabel styleLabel) {
        try {
            if (tokenService.getUserByToken(token) != null) {
                if (styleLabel.getId() == null) {
                    return ResJson.failJson(7004, "styleLabel id error", null);
                }
                StyleLabel oldStyleLabel;
                if ((oldStyleLabel = styleLabelRepository.findOne(styleLabel.getId())) == null) {
                    return ResJson.failJson(7003, "无法更新, 权限错误", null);
                }
                updateStyleLabel(oldStyleLabel, styleLabel);
                return ResJson.successJson("update StyleLabel success");
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson queryStyleLabelList(String token, Integer start, Integer num) {
        try {
            Merchant merchant = (Merchant) tokenService.getUserByToken(token);
            if (null == merchant) {
                return ResJson.errorAccessToken();
            }
            //Page<Style> page = styleRepository.findAll(new PageRequest(start, num));
            Page<StyleLabel> page = styleLabelRepository.findAll(new PageRequest(start, num));
            return ResJson.successJson("query styleLabel list success", page);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson findStyleLabelList() {
        try {
            //String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            //Integer type = (Integer) ParamUtils.getFromJson(jsonObject, "type", Integer.class);
//            Customer customer = (Customer) tokenService.getUserByToken(token);
//            if (null == customer) {
//                return ResJson.errorAccessToken();
//            }
            List<StyleLabel> list = styleLabelRepository.findAll();
            return ResJson.successJson("find styleLabel list success", list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

}
