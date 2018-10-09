package tech.greatinfo.sellplus.service;

import com.alibaba.fastjson.JSONObject;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.io.IOException;

public interface SellerCodeService {

    String getSellerCode(Customer customer, String token, String scene, String page) throws IOException;

    ResJson getSellerCodeList(JSONObject jsonObject);
}
