package tech.greatinfo.sellplus.service;

import com.alibaba.fastjson.JSONObject;
import tech.greatinfo.sellplus.domain.Style;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface StyleService {

    ResJson addStyle(String token, Style style);

    ResJson delStyle(String token, Long styleId);

    ResJson updateStyle(String token, Style style);

    ResJson queryStyleList(String token, Integer type, Integer start, Integer num);

    ResJson findStyleList(JSONObject jsonObject);
}
