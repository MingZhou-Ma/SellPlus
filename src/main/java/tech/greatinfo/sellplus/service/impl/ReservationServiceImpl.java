package tech.greatinfo.sellplus.service.impl;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Reservation;
import tech.greatinfo.sellplus.repository.ReservationRepository;
import tech.greatinfo.sellplus.service.ReservationService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.PhoneUtil;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

@Service
public class ReservationServiceImpl implements ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationServiceImpl.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    public ResJson reservation(JSONObject jsonObject) {
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject, "token", String.class);
            String num = (String) ParamUtils.getFromJson(jsonObject, "num", String.class);
            String name = (String) ParamUtils.getFromJson(jsonObject, "name", String.class);
            String phone = (String) ParamUtils.getFromJson(jsonObject, "phone", String.class);
            String remark = (String) ParamUtils.getFromJson(jsonObject, "remark", String.class);

            Customer customer = (Customer) tokenService.getUserByToken(token);
            if (null == customer) {
                return ResJson.errorAccessToken();
            }
            if (!PhoneUtil.checkCellphone(phone)) {
                return ResJson.failJson(4001, "error phone", null);
            }

            Reservation reservation = new Reservation();
            reservation.setNum(num);
            reservation.setName(name);
            reservation.setPhone(phone);
            reservation.setRemark(remark);
            reservation.setCustomer(customer);
            reservationRepository.save(reservation);

            //发送短信

            return ResJson.successJson("reservation success");

        } catch (JsonParseException jse) {
            logger.info(jse.getMessage() + " -> /api/cus/reservation");
            return ResJson.errorRequestParam(jse.getMessage() + " -> /api/cus/reservation");
        } catch (Exception e) {
            logger.error("/api/cus/reservation -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
