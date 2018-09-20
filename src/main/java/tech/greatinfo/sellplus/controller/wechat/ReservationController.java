package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.service.ReservationService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * 用户预约API
 */
@RestController
public class ReservationController {

    //private static final Logger logger = LoggerFactory.getLogger(ReservationController.class);

    @Autowired
    ReservationService reservationService;

    /**
     * 预约
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/reservation", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public ResJson reservation(@RequestBody JSONObject jsonObject) {
        return reservationService.reservation(jsonObject);
    }


}
