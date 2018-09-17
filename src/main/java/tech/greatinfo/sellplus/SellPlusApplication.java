package tech.greatinfo.sellplus;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tech.greatinfo.sellplus.domain.Customer;
import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.utils.obj.AccessToken;

@SpringBootApplication
public class SellPlusApplication {

    public static void main(String[] args) {
        TokenService tokenService = new TokenService();
        //测试用户
        AccessToken token = new AccessToken();
        Customer customer = new Customer();
        customer.setId(1L);
        customer.setOpenid("openidTest");
        customer.setUid("3f1217d51a264f5eb34b527c6fdc78e4");
        customer.setSessionKey("sessionKeyTest");
        customer.setbSell(true);
        Seller seller = new Seller();
        seller.setId(5L);
        customer.setSeller(seller);
        token.setUser(customer);
        token.setUuid("testtoken");
        tokenService.saveToken(token);
//
//
//        // 测试商户
        AccessToken token2 = new AccessToken();
        Merchant merchant = new Merchant();
        merchant.setId(1L);
        merchant.setAccount("rootroot");
        merchant.setPassword("0242c0436daa4c241ca8a793764b7dfb50c223121bb844cf49be670a3af4dd18");

        token2.setUser(merchant);
        token2.setUuid("mertoken");
        tokenService.saveToken(token2);
        SpringApplication.run(SellPlusApplication.class, args);
    }
}
