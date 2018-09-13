package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.repository.MerchantRepository;

import java.util.List;


/**
 * Created by Ericwyn on 18-7-27.
 */
@Service
public class MerchantService {
    @Autowired
    MerchantRepository merchantRepository;

    public void save(Merchant merchant){
        merchantRepository.save(merchant);
    }

    public Merchant findOne(Long id){
        return merchantRepository.findOne(id);
    }

    public Merchant findByAccountAndPassword(String account, String password){
        return merchantRepository.findByAccountAndPassword(account,password);
    }

    public Merchant getMainMerchant(){
        List<Merchant> list = merchantRepository.findAll();
        // 如果列表为空，直接get(0)会报空指针异常
        if (null != list && !list.isEmpty()) {
            return list.get(0);
        }
        return null;
    }
}
