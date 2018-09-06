package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.coupons.Coupons;
import tech.greatinfo.sellplus.repository.CouponsRepository;

/**
 * Created by Ericwyn on 18-9-6.
 */
@Service
public class CouponsService {
    @Autowired
    CouponsRepository couponsRepository;

    public Coupons save(Coupons coupons){
        return couponsRepository.save(coupons);
    }

    public Coupons findOne(Long id){
        return couponsRepository.findOne(id);
    }

    public Page<Coupons> findAll(int start, int num){
        return couponsRepository.findAll(new PageRequest(start,num));
    }
}
