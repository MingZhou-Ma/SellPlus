package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import tech.greatinfo.sellplus.domain.article.Article;
import tech.greatinfo.sellplus.repository.ArticleRepository;

/**
 * Created by Ericwyn on 18-7-27.
 */
@Service
public class ArticleService {
    @Autowired
    ArticleRepository articleRepository;

    public Article save(Article article){
        return articleRepository.save(article);
    }

    public Article findOne(Long id){
        return articleRepository.findOne(id);
    }

    public Page<Article> findByPage(int start, int num){
        return articleRepository.findAll(new PageRequest(start,num));
    }

    public void delete(Long id){
        articleRepository.delete(id);
    }
}
