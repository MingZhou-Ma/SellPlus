package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.Merchant;
import tech.greatinfo.sellplus.repository.MerchantRepository;


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
        // TODO 有bug，得先判断是否列表为空
        return merchantRepository.findAll().get(0);
    }
}
