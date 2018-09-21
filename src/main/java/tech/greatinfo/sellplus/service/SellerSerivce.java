package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.repository.SellerRepository;

/**
 * Created by Ericwyn on 18-8-14.
 */
@Service
public class SellerSerivce {
    @Autowired
    SellerRepository sellerRepository;

    private Seller defaultSeller;

    @Autowired
    CustomService customService;

    public Seller findByAccountAndSellerKey(String account, String key) {
        return sellerRepository.findByAccountAndSellerKey(account, key);
    }

    public Seller save(Seller seller) {
        return sellerRepository.save(seller);
    }

//    public void deleteSeller(Long sellerId) {
//        List<Customer> customers = customService.findBySeller(sellerId);
//        for (Customer customer:customers){
//            //customService.deleteActivity(activity.getId());
//            customService.
//        }
//        sellerRepository.delete(sellerId);
//    }

    public Page<Seller> findAllByPages(int start, int num) {
        return sellerRepository.findAll(new PageRequest(start, num));
    }

    /**
     * 返回默认的 seller ， 默认 seller 是 id 为1 的 Seller
     *
     * @return
     */
    public Seller getDefaultSeller() {
        if (defaultSeller == null) {
            defaultSeller = sellerRepository.findOne(1L);
        }
        return defaultSeller;
    }
}
