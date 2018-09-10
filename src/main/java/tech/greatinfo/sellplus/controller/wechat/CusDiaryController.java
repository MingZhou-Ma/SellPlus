package tech.greatinfo.sellplus.controller.wechat;

import com.alibaba.fastjson.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Diary;
import tech.greatinfo.sellplus.domain.coupons.Coupon;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.service.CompanyService;
import tech.greatinfo.sellplus.service.CouponsObjService;
import tech.greatinfo.sellplus.service.DiaryService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.ParamUtils;
import tech.greatinfo.sellplus.utils.exception.JsonParseException;
import tech.greatinfo.sellplus.utils.obj.ResJson;

/**
 *
 * 日记
 * 心得分享相关 API
 *
 * 心得分享相关流程
 *
 * 0 后台设定心得分享阅读量兑换奖品的阈值，以及用来奖励的优惠卷
 *
 * 1 前端接受用户的心得分享内容，用户的分享图片
 * 2 前端为用户生成一个唯一的 diary-id
 * 3 前端通过 diary-id 来生成一个唯一的链接
 * 4 前端调用小程序码分享接口，来生成这个链接对应的小程序码
 * 5 前端发送 diary-id 和 token 给后台的记录接口，后台记录 diary-id 和用户，方便统计小程序码识别量这样的数据  /api/cus/genDiary
 * 6 新用户通过小程序码点击进入程序主页（或者一个特定的中转页面）
 * 7 前端获取 diary-id 和新用户的 token，发送到后台的记录接口       /api/cus/readDiary
 *
 * 至此心得分享流程完成
 *
 * Created by Ericwyn on 18-9-8.
 */
@RestController
public class CusDiaryController {
    private static final Logger logger = LoggerFactory.getLogger(CusDiaryController.class);

    @Autowired
    TokenService tokenService;

    @Autowired
    DiaryService diaryService;

    @Autowired
    CompanyService companyService;

    @Autowired
    CouponsObjService couponsObjService;

    /**
     * 记录前端生成的 diary-id 和 用户 A，然后台记录，用户 A 发布了一篇标记为 diary-id 的日志
     * /api/cus/genDiary
     * POST
     *      did     diary-id
     *      token   用户 token
     *
     * @return
     */
    @RequestMapping(value = "/api/cus/genDiary",method = RequestMethod.POST)
    public ResJson generalDiary(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            String diaryId = (String) ParamUtils.getFromJson(jsonObject,"did",String.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Diary diary = new Diary();
                diary.setDiaryId(diaryId);
                diary.setCustomer(customer);
                diary.setGeneral(false);
                diary.setReadHistory("");
                diary.setGeneralTime(null);
                diaryService.save(diary);
                return ResJson.successJson("save diary success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/beFreq");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/beFreq");
        }catch (Exception e){
            logger.error("/api/freq/beFreq -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    /**
     * 阅读一篇心得分享
     *
     *  POST
     *          token       当前用户的 token
     *          did         阅读的心得分享的 id
     *
     * @param jsonObject
     * @return
     */
    @RequestMapping(value = "/api/cus/readDiary",method = RequestMethod.POST)
    public ResJson readDiary(@RequestBody JSONObject jsonObject){
        try {
            String token = (String) ParamUtils.getFromJson(jsonObject,"token", String.class);
            String diaryId = (String) ParamUtils.getFromJson(jsonObject,"did",String.class);
            Customer customer;
            if ((customer = (Customer) tokenService.getUserByToken(token)) != null){
                Diary diary ;
                if ((diary = diaryService.findOne(diaryId)) == null){
                    return ResJson.failJson(-1, "diary id error", null);
                }
                String read = diary.getReadHistory();
                int count = read.split(",").length;
                // 自己无法阅读自己的心得分享
                if (!customer.equals(diary.getCustomer())
                        && !read.contains(customer.getOpenid())
                        && count < companyService.getDiaryReadNum()){
                    // TODO 数据库锁，read history 会有同步问题
                    diary.setReadHistory(read+customer.getOpenid()+",");
                    diaryService.save(diary);
                    count++;
                    // 达到兑换标准，新线程完成卷的兑换
                    if (!diary.isGeneral() && count >= companyService.getDiaryReadNum()){
                        // 在兑换优惠券线程开启之前判断是否在领取优惠券的间隔时间内
                        // 查询发布该海报的用户的最近一条已经兑换过优惠券的记录
                        boolean flag = true; // 是否允许兑换的标志
                        Diary d = diaryService.findFirstByCustomerAndGeneralOrderByGeneralTimeDesc(diary.getCustomer(), true);
                        if (null != d) {
                            Date generalTime = d.getGeneralTime();
                            if (null != generalTime) {
                                //通过时间秒毫秒数判断两个时间的间隔天数，可以做成工具类
                                int days = (int) ((new Date().getTime() - generalTime.getTime()) / (1000 * 3600 * 24));
                                if (days < companyService.getDiaryIntervals()) {
                                    // 不允许兑换了
                                    flag = false;
                                }
                            }
                        }
                        if (flag) {
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    generalCoupon(diaryId);
                                }
                            }).start();
                        }
                    }
                }
                return ResJson.successJson("read diary success");
            }else {
                return ResJson.errorAccessToken();
            }
        }catch (JsonParseException jse){
            logger.info(jse.getMessage()+" -> /api/freq/beFreq");
            return ResJson.errorRequestParam(jse.getMessage()+" -> /api/freq/beFreq");
        }catch (Exception e){
            logger.error("/api/freq/beFreq -> ",e.getMessage());
            e.printStackTrace();
            return ResJson.serverErrorJson(e.getMessage());
        }
    }

    @Transactional
    public synchronized void generalCoupon(String diaryId){
        Diary diary = diaryService.findOne(diaryId);
        if (!diary.isGeneral() && diary.getReadHistory().split(",").length >= companyService.getDiaryReadNum()){
            Coupon coupon = companyService.getDiaryCoupon();
            if (coupon == null){
                logger.info("尚未设置心得分享的奖励优惠卷");
            }else {
                CouponsObj couponsObj = new CouponsObj();
                couponsObj.setCoupon(coupon);
                couponsObj.setCode(couponsObjService.getRandomCouponCode());
                couponsObj.setOwn(diary.getCustomer());
                couponsObj.setNote("心得分享奖励优惠卷");
                couponsObj.setGeneralTime(new Date());
                couponsObjService.save(couponsObj);
                diary.setGeneral(true);
                diary.setGeneralTime(new Date()); // 设置获取优惠券的时间
                diaryService.save(diary);
            }
        }
    }
}
