package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.domain.coupons.CouponsHistory;
import tech.greatinfo.sellplus.repository.CouponsHistoryRepository;

/**
 * Created by Ericwyn on 18-9-16.
 */
@Service
public class CouponsHistoryService {
    @Autowired
    CouponsHistoryRepository repository;

    public Page<CouponsHistory> getHistoryBySeller(Seller seller,int start, int num){
        return repository.findAllBySeller(seller,new PageRequest(start, num));
    }

    public Page<CouponsHistory> findAll(int start, int num){
        return repository.findAll(new PageRequest(start, num));
    }
}
