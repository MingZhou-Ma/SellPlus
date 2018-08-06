package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import tech.greatinfo.sellplus.domain.Product;
import tech.greatinfo.sellplus.repository.ProductRepository;


/**
 * Created by Ericwyn on 18-7-27.
 */
@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public void save(Product product){
        productRepository.save(product);
    }

    public Product findOne(Long id){
        return productRepository.findOne(id);
    }

    public Page<Product> findAllByPages(int start, int num){
        return productRepository.findAll(new PageRequest(start, num));
    }

    public void update(Product oldProduct, Product product){
        Field[] fields = product.getClass().getDeclaredFields();
        for (Field field:fields){
            try {
                boolean access = field.isAccessible();
                if(!access) field.setAccessible(true);
                Object o = field.get(product);
                //静态变量不操作,这样的话才不会报错
                if (o!=null && !Modifier.isStatic(field.getModifiers())){
                    field.set(oldProduct,o);
                }
                if(!access) field.setAccessible(false);
            }catch (IllegalAccessException e){
                e.printStackTrace();
            }
        }
        productRepository.saveAndFlush(oldProduct);
    }

    public void deleteProduct(Long productId){
        productRepository.delete(productId);
    }

}
