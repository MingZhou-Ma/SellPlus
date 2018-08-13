package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import tech.greatinfo.sellplus.domain.Company;
import tech.greatinfo.sellplus.repository.CompanyRepository;

/**
 *
 * Created by Ericwyn on 18-8-13.
 */
@Service
public class CompanyService {
    @Autowired
    CompanyRepository companyRepository;

    public void saveMainInfo(List<Company> list){
        companyRepository.save(list);
    }

    public Company findByKey(String key){
        return companyRepository.findByK(key);
    }

}
