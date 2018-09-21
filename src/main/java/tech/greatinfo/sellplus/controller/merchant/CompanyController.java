package tech.greatinfo.sellplus.controller.merchant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tech.greatinfo.sellplus.domain.Company;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.service.*;
import tech.greatinfo.sellplus.utils.obj.ResJson;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * 公司设置的 API
 *
 * Created by Ericwyn on 18-8-7.
 */
@RestController
public class CompanyController {
    private static final Logger logger = LoggerFactory.getLogger(CompanyController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    MerchantService merchantService;

    @Autowired
    ProductService productService;

    @Autowired
    ActivityService activityService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CouponsService couModelService;

    @Autowired
    CouponsObjService couObjService;

    @Autowired
    CustomService customService;
    /**
     * 参数说明
     *
     *      banner1         banner图1 的url
     *      banner2
     *      banner3
     *      notify1         通知 1 的具体内通
     *      notify2
     *      notify3
     *      curtain         弹幕内容
     *      promotion       老司机推广人数达标阈值，达到这个值将可以领取一张老司机推广专属优惠卷
     *      coupon1         用来奖励给老司机的特殊优惠卷的卷 id
     *      coupon2         老司机用来发给新顾客的卷的卷 id
     *      diaryReadNum    用户分享阅读数量领卷下限
     *      diaryCoupon     心得分享的奖励优惠卷
     *      diaryIntervals  心得分享获得优惠券间隔时间
     *      token
     *
     * TODO 这里juan1 和 juan2 要和优惠卷主键关联，或者提交保存的时候加多一步验证
     *
     */
    @RequestMapping(value = "/api/mer/setMainInfo",method = RequestMethod.POST)
    public ResJson setMainInfo(@RequestParam(value = "banner1",required = false) String banner1,
                               @RequestParam(value = "banner2",required = false) String banner2,
                               @RequestParam(value = "banner3",required = false) String banner3,
                               @RequestParam(value = "notify1",required = false) String notify1,
                               @RequestParam(value = "notify2",required = false) String notify2,
                               @RequestParam(value = "notify3",required = false) String notify3,
                               @RequestParam(value = "curtain",required = false) String curtain,
                               @RequestParam(value = "promotion",required = false) String promotion,
                               @RequestParam(value = "coupon1",required = false) String coupon1,
                               @RequestParam(value = "coupon2",required = false) String coupon2,
                               @RequestParam(value = "diaryReadNum",required = false) Integer diaryReadNum,
                               @RequestParam(value = "diaryCoupon",required = false) String diaryCoupon,
                               @RequestParam(value = "diaryIntervals", required = false) Integer diaryIntervals,
                               @RequestParam(value = "token") String token){

        try {

            if (tokenService.getUserByToken(token) != null && tokenService.getUserByToken(token) instanceof Merchant){
                List<Company> list = new ArrayList<>();
                if (banner1 != null){
                    list.add(new Company("banner1",banner1));
                }
                if (banner2 != null){
                    list.add(new Company("banner2",banner2));
                }
                if (banner3 != null){
                    list.add(new Company("banner3",banner3));
                }
                if (notify1 != null){
                    list.add(new Company("notify1",notify1));
                }
                if (notify2 != null){
                    list.add(new Company("notify2",notify2));
                }
                if (notify3 != null){
                    list.add(new Company("notify3",notify3));
                }
                if (curtain != null){
                    list.add(new Company("curtain",curtain));
                }
                // TODO 卷的权限判断（是否存在，是否是数量无限制的优惠卷）
                if (promotion != null){
                    list.add(new Company("promotion",promotion));
                }
                if (coupon1 != null){
                    list.add(new Company("coupon1",coupon1));
                }
                if (coupon2 != null){
                    list.add(new Company("coupon1",coupon2));
                }
                if (diaryReadNum != null){
                    list.add(new Company("diaryReadNum", String.valueOf(diaryReadNum)));
                }
                //  卷的权限判断（是否存在，是否是数量无限制的优惠卷）
                if (diaryCoupon != null){
                    Coupon coupon = couModelService.findOne(Long.valueOf(diaryCoupon));
                    if (null != coupon) {
                        if (!coupon.getFinite()) {
                            list.add(new Company("diaryCoupon",diaryCoupon));
                        }
                    }
                }

                if (null != diaryIntervals) {
                    list.add(new Company("diaryIntervals", String.valueOf(diaryIntervals)));
                }

                companyService.saveMainInfo(list);
                return ResJson.successJson("set company info success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (Exception e){
            logger.error("/api/mer/setMainInfo -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    // 公司设置获取接口在 public Res Controller

    // 获取所有的客户
    @RequestMapping(value = "/api/mer/getMyCustomer", method = RequestMethod.POST)
    public ResJson getMyCustomer(@RequestParam("token") String token,
                                 @RequestParam(value = "start", defaultValue = "0") Integer start,
                                 @RequestParam(value = "num", defaultValue = "999") Integer num
    ) {
        try {
            Merchant merchant;
            if (tokenService.getUserByToken(token) != null && tokenService.getUserByToken(token) instanceof Merchant){
                return ResJson.successJson("get all customer success",
                        customService.findAll(new PageRequest(start,num)));
            } else {
                return ResJson.errorAccessToken();
            }
        } catch (Exception e) {
            logger.error("/api/sell/getMyCustomer -> ", e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }


}
