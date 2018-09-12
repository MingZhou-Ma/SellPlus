package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.repository.SellerRepository;

/**
 *
 *
 * Created by Ericwyn on 18-8-14.
 */
@Service
public class SellerSerivce {
    @Autowired
    SellerRepository sellerRepository;

    private Seller defaultSeller;

    public Seller findByAccountAndSellerKey(String account, String key){
        return sellerRepository.findByAccountAndSellerKey(account, key);
    }

    public Seller save(Seller seller){
        return sellerRepository.save(seller);
    }

    /**
     * 返回默认的 seller ， 默认 seller 是 id 为1 的 Seller
     * @return
     */
    public Seller getDefaultSeller(){
        if (defaultSeller == null){
            defaultSeller = sellerRepository.findOne(1L);
        }
        return defaultSeller;
    }
}
