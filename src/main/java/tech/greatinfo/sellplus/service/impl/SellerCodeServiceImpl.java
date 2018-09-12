package tech.greatinfo.sellplus.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.SellerCode;
import tech.greatinfo.sellplus.repository.SellerCodeRepository;
import tech.greatinfo.sellplus.service.SellerCodeService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.util.List;

@Service
public class SellerCodeServiceImpl implements SellerCodeService {

    private static final Logger logger = LoggerFactory.getLogger(SellerCodeServiceImpl.class);

    private final TokenService tokenService;

    private final SellerCodeRepository sellerCodeRepository;

    @Autowired
    public SellerCodeServiceImpl(TokenService tokenService, SellerCodeRepository sellerCodeRepository) {
        this.tokenService = tokenService;
        this.sellerCodeRepository = sellerCodeRepository;
    }

    /**
     * 添加我的销售码
     *
     * @param jsonObject
     * @return
     */
    @Override
    public ResJson addSellerCode(JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            String name = (String) ParamUtils.getFromJson(jsonObject, "name", String.class);

            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }

            SellerCode sellerCode = new SellerCode();
            sellerCode.setName(name);
            sellerCode.setCustomer(customer);
            sellerCodeRepository.save(sellerCode);

            return ResJson.successJson("add sellerCode success", sellerCode);  // 销售码格式：销售uuid/渠道码名称
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> api/cus/listActivity");
            return ResJson.errorRequestParam(jse.getMessage() + " -> api/cus/listActivity");
        } catch (Exception e) {
            logger.error("api/cus/listActivity -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Override
    public ResJson addSellerCode(String token, Long sellerCodeId, String name, String path) {
        Customer customer = (Customer) tokenService.getUserByToken(token);
        if (null == customer) {
            return ResJson.errorAccessToken();
        }

        SellerCode sellerCode = new SellerCode();
        sellerCode.setId(sellerCodeId);
        sellerCode.setName(name);
        sellerCode.setCustomer(customer);
        sellerCode.setPath(path);
        sellerCodeRepository.save(sellerCode);

        return ResJson.successJson("add sellerCode success", path);

    }

    @Override
    public ResJson getSellerCodeList(JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);

            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }

            List<SellerCode> list = sellerCodeRepository.findAllByCustomer(customer);
            return ResJson.successJson("add sellerCode success", list);
        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> api/cus/listActivity");
            return ResJson.errorRequestParam(jse.getMessage() + " -> api/cus/listActivity");
        } catch (Exception e) {
            logger.error("api/cus/listActivity -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
