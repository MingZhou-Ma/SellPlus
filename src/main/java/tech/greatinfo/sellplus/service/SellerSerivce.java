package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.repository.SellerRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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

    public Seller findOne(Long id){
        return sellerRepository.findOne(id);
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

    public void updateSeller(Seller oldEntity, Seller newEntity){
        Field[] fields = newEntity.getClass().getDeclaredFields();
        for (Field field:fields){
            try {
                boolean access = field.isAccessible();
                if(!access) field.setAccessible(true);
                Object o = field.get(newEntity);
                //静态变量不操作,这样的话才不会报错
                if (o!=null && !Modifier.isStatic(field.getModifiers())){
                    field.set(oldEntity,o);
                }
                if(!access) field.setAccessible(false);
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        sellerRepository.saveAndFlush(oldEntity);
    }
}
