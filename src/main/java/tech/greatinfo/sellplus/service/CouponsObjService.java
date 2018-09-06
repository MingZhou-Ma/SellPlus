package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.coupons.CouponsObj;
import tech.greatinfo.sellplus.repository.CouponsObjRepository;

/**
 * Created by Ericwyn on 18-9-6.
 */
@Service
public class CouponsObjService {
    @Autowired
    CouponsObjRepository objRepository;

    public CouponsObj save(CouponsObj obj){
        return objRepository.save(obj);
    }

    public CouponsObj findOne(Long id){
        return objRepository.findOne(id);
    }

    public Page<CouponsObj> findAll(int start, int num){
        return objRepository.findAll(new PageRequest(start,num));
    }

}
