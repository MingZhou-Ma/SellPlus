package tech.greatinfo.sellplus.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.service.CustomService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.AccessToken;

import java.util.List;

/**
 * 描述：
 *
 * @author Badguy
 */
@Component
public class LotteryNumTask {

    @Autowired
    CustomService customService;

    @Autowired
    TokenService tokenService;

    /**
     * 每天凌晨0点将抽奖次数设置为初始值3
     */
    @Scheduled(cron = "0 0 0 * * ?")
    public void job() {
        List<Customer> list = customService.findAllCustomer();
        if (null != list && !list.isEmpty()) {
            for (Customer customer : list) {
                customer.setLotteryNum(3);
                customer.setHasLotteryNum(0);
                customService.save(customer);

                AccessToken accessToken = tokenService.getTokenByCustomOpenId(customer.getOpenid());
                if (null != accessToken) {
                    accessToken.setUser(customer);
                    tokenService.saveToken(accessToken);
                }

            }
            System.out.println("重置抽奖次数成功");
        }

    }
}
