package tech.greatinfo.sellplus.service;

import tech.greatinfo.sellplus.domain.StyleLabel;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 描述：
 *
 * @author Badguy
 */
public interface StyleLabelService {

    ResJson addStyleLabel(String token, StyleLabel styleLabel);

    ResJson delStyleLabel(String token, Long styleLabelId);

    ResJson updateStyleLabel(String token, StyleLabel styleLabel);

    ResJson queryStyleLabelList(String token, Integer start, Integer num);

    ResJson findStyleLabelList();
}
