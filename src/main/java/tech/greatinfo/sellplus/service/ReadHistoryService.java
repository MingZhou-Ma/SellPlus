package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.Seller;
import tech.greatinfo.sellplus.domain.article.ReadHistory;
import tech.greatinfo.sellplus.repository.ReadHistoryRepository;

/**
 * Created by Ericwyn on 18-8-29.
 */
@Service
public class ReadHistoryService {
    @Autowired
    ReadHistoryRepository repository;

    public Page<ReadHistory> findAllSellerOrderByReadTimeDesc(Seller seller, Pageable pageable){
        return repository.findAllByCustom_SellerOrderByReadTimeDesc(seller,pageable);
    }

    public ReadHistory save(ReadHistory readHistory){
        return repository.save(readHistory);
    }

}
