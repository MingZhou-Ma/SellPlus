package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.article.Article;
import tech.greatinfo.sellplus.repository.ArticleRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

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
        //return articleRepository.findAll(new PageRequest(start,num));
        return articleRepository.findAllByOrderByCreateDateDesc(new PageRequest(start,num));
    }

    public void delete(Long id){
        articleRepository.delete(id);
    }

    public void updateArticle(Article oldEntity, Article newEntity){
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
        articleRepository.saveAndFlush(oldEntity);
    }
}
