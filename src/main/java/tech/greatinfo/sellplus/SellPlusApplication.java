package tech.greatinfo.sellplus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.service.MerchantService;
import tech.greatinfo.sellplus.service.TokenService;
import tech.greatinfo.sellplus.util.obj.AccessToken;

@SpringBootApplication
public class SellPlusApplication {
    @Autowired
    private static MerchantService merchantService;

    public static void main(String[] args) {
        TokenService tokenService = new TokenService();
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
