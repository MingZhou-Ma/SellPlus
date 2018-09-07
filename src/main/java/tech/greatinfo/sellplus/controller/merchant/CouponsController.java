package tech.greatinfo.sellplus.controller.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

import tech.greatinfo.sellplus.config.converter.StringToDateConverter;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.service.CouponsObjService;
import tech.greatinfo.sellplus.service.CouponsService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 * Created by Ericwyn on 18-9-6.
 */
@RestController
public class CouponsController {
    private static final Logger logger = LoggerFactory.getLogger(CouponsController.class);

    @Autowired
    CouponsService modelService;

    @Autowired
    CouponsObjService objService;

    @Autowired
    TokenService tokenService;
    // 获取全部优惠卷模板信息

    @InitBinder
    public void intDate(WebDataBinder dataBinder){
        dataBinder.addCustomFormatter(new StringToDateConverter("yyyy-MM-dd hh:mm:ss"));
//        dataBinder.addCustomFormatter(new StringToBooleanConverter());
    }

    /**
     * 新增优惠卷模板
     *
     * POST
     *      token
     *      content     str     卷的描述
     *      finite      bool    是否高级别的无线优惠卷
     *      num         int     优惠卷的数量（只有 finite 为 true 的时候，这个参数才是有效的）
     *      startDate   date    yyyy-MM-dd hh:mm:ss 有效期开始时间
     *      endDate     date    格式同上，有效期的结束时间
     *
     * @param token
     * @param coupons
     * @return
     */
    @RequestMapping(value = "/api/mer/addCouponModel",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson addProduct(@RequestParam(name = "token") String token,
                              @ModelAttribute Coupon coupons){
        try {
            Merchant merchant;
            if ((merchant = (Merchant) tokenService.getUserByToken(token)) != null){
                if (coupons.getFinite()){
                    int num = coupons.getNum();
                    List<CouponsObj> list = new ArrayList<>(num);
                    for (int i=0;i<num;i++){
                        // 新建 num 个尚未发出去的卷
                        CouponsObj obj = new CouponsObj();
                        obj.setCode(objService.getRandomCouponCode());
                        obj.setCoupons(coupons);
                        obj.setExpired(false);
                        objService.save(obj);
                    }
                    objService.save(list);
                }
                modelService.save(coupons);
                return ResJson.successJson("add coupons success", coupons);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/addCouponModel -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 获取全部的优惠卷模板
     *
     * TODO 改为分页查询
     *
     * @param token
     * @return
     */
    @RequestMapping(value = "/api/mer/getCouponModel",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson getCouponModel(@RequestParam(name = "token") String token,
                              @RequestParam(name = "start",defaultValue = "0") Integer start,
                              @RequestParam(name = "end",defaultValue = "999") Integer num){
        try {
            Merchant merchant;
            if ((merchant = (Merchant) tokenService.getUserByToken(token)) != null){
                Page<Coupon> all = modelService.findAll(start, num);
                return ResJson.successJson("get all coupons model success",all);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/getCouponModel -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 获取全部优惠卷实体
     * POST
     *      token
     *      start
     *      end
     *
     * @param token
     * @param start
     * @param num
     * @return
     */
    @RequestMapping(value = "/api/mer/getCouponObj",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson getCouponObj(@RequestParam(name = "token") String token,
                              @RequestParam(name = "start",defaultValue = "0") Integer start,
                              @RequestParam(name = "end",defaultValue = "999") Integer num){
        try {
            Merchant merchant;
            if ((merchant = (Merchant) tokenService.getUserByToken(token)) != null){
                Page<CouponsObj> all = objService.findAll(start, num);
                return ResJson.successJson("get all coupons object success",all);
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/getCouponObj -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     *
     * 删除某个优惠卷模板
     * POST
     *      token
     *      cid     str     优惠卷模板 id
     *
     * @param token
     * @param cid
     * @return
     */
    @RequestMapping(value = "/api/mer/delCouponModel",method = RequestMethod.POST,produces = "application/json; charset=utf-8")
    public ResJson getCouponObj(@RequestParam(name = "token") String token,
                                @RequestParam(name = "cid") Long cid){
        try {
            Merchant merchant;
            if ((merchant = (Merchant) tokenService.getUserByToken(token)) != null){
                Coupon coupon = modelService.findOne(cid);
                // TODO 最好改为级联删除
                if (coupon != null){
                    objService.deleteByMode(coupon);
                    modelService.delete(coupon);
                    return ResJson.successJson("delete success");
                }else {
                    return ResJson.failJson(-1,"coupon id error",null);
                }
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/getCouponObj -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }
}
