package tech.greatinfo.sellplus.service;

import com.alibaba.fastjson.JSONObject;
import tech.greatinfo.sellplus.utils.obj.ResJson;

public interface ReservationService {

    ResJson reservation(JSONObject jsonObject);
}
