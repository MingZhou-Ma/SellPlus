package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.domain.coupons.CouponsHistory;
import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.repository.CouponsHistoryRepository;
import tech.greatinfo.sellplus.repository.CouponsObjRepository;
import tech.greatinfo.sellplus.utils.SendSmsUtil;
import tech.greatinfo.sellplus.utils.obj.AccessToken;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

/**
 * Created by Ericwyn on 18-9-6.
 */
@Service
public class CouponsObjService {
    @Autowired
    CouponsObjRepository objRepository;

    @Autowired
    CouponsHistoryRepository historyRepository;

    @Autowired
    CompanyService companyService;

    @Autowired
    CustomService customService;

    @Autowired
    TokenService tokenService;

    @Value("${company}")
    private String company;

    public CouponsObj save(CouponsObj obj){
        return objRepository.save(obj);
    }

    public void save(List<CouponsObj> obj){
        objRepository.save(obj);
    }

    public CouponsObj findOne(Long id){
        return objRepository.findOne(id);
    }

    public Page<CouponsObj> findAll(int start, int num){
        return objRepository.findAll(new PageRequest(start,num));
    }

    public void deleteByMode(tech.greatinfo.sellplus.domain.coupons.Coupon model){
        objRepository.deleteAllByCoupon(model);
    }

    public Long countAllByOrigin(Customer origin){
        return objRepository.countAllByOrigin(origin);
    }

    public Long countAllByOriginAndExpiredTrue(Customer origin){
        return objRepository.countAllByOriginAndExpiredTrue(origin);
    }

    public Page<CouponsObj> getAllByOwn(Customer own,int start,int num){
        return objRepository.getAllByOwn(own,new PageRequest(start,num));
    }

    public Page<CouponsObj> getAllByOwnAndUnUsed(Customer own,int start,int num){
        return objRepository.getAllByOwnAndExpiredFalse(own,new PageRequest(start,num));
    }

    public Page<CouponsObj> getAllByOwnAndUsed(Customer own,int start,int num){
        return objRepository.getAllByOwnAndExpiredTrue(own,new PageRequest(start,num));
    }

    public List<CouponsObj> findFirst3ByOrigin(Customer origin) {
        return objRepository.findFirst3ByOrigin(origin);
    }

    public List<CouponsObj> findAllByOrigin(Customer origin) {
        return objRepository.findAllByOrigin(origin);
    }


    public CouponsObj findByCode(String code){
        return objRepository.findByCode(code);
    }

    public CouponsObj findByOriginAndOwn(Customer origin, Customer own) {
        return objRepository.findByOriginAndOwn(origin, own);
    }

    public CouponsObj findByOwnAndId(Customer own, Long id) {
        return objRepository.findByOwnAndId(own, id);
    }

    @Transactional
    @Modifying
    public void writeOffCoupons(CouponsObj coupon, Seller seller){
        coupon.setExpired(true);
        objRepository.save(coupon);
        CouponsHistory history = new CouponsHistory();
        history.setCouponObj(coupon);
        history.setDate(new Date());
        history.setSeller(seller);
        historyRepository.save(history);

        // 核销的券为老司机券，有奖金加成
        if (coupon.getCoupon() == companyService.getFreqCoupon()) {
            Customer origin = coupon.getOrigin();
            if (null != origin) {
                origin.setFreqBonus(origin.getFreqBonus() + companyService.getFreqBonus());
                customService.save(origin);

                AccessToken accessToken = tokenService.getTokenByCustomOpenId(origin.getOpenid());
                accessToken.setUser(origin);
                tokenService.saveToken(accessToken);

                //发送短信
                SendSmsUtil.writeOffFreqCouponSendSms(origin.getPhone(), coupon.getOwn().getNickname(), company);
            }
        }
    }

    /**
     * 生成卷 code ，由 大小写，和 0 ~ 9 组成的 6 位数字
     * @return
     */
    public String getRandomCouponCode(){
        int temp;
        char ch;
        String code = "";
        for (int i=0;i<6;i++){
            temp = (int)(Math.random()*3);
            switch (temp){
                case 0:
                    // 数字
                    code += (int)(Math.random()*9)+1;
                    break;
                case 1:
                    // 小写
                    // 去除 L l O o 这些容易混淆的
                    ch = (char)((int)(Math.random()*26)+65);
                    while (ch == 'l' || ch == 'o'){
                        ch = (char)((int)(Math.random()*26)+97);
                    }
                    code += ch;
                    break;
                case 2:
                    // 大写
                    ch = (char)((int)(Math.random()*26)+97);
                    while (ch == 'L' || ch == 'O' || ch == 'I'){
                        ch = (char)((int)(Math.random()*26)+97);
                    }
                    code += ch;
                    break;
            }
        }
        if (objRepository.findByCode(code)!=null){
            return getRandomCouponCode();
        }
        return code;
    }
}
