package tech.greatinfo.sellplus.service;

import com.alibaba.fastjson.JSONObject;
import tech.greatinfo.sellplus.utils.obj.ResJson;

public interface SellerCodeService {

    ResJson addSellerCode(JSONObject jsonObject);
    ResJson addSellerCode(String token, Long sellerCodeId, String name, String path);

    ResJson getSellerCodeList(JSONObject jsonObject);
}
