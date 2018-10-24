package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Product;
import tech.greatinfo.sellplus.repository.ProductRepository;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;


/**
 * Created by Ericwyn on 18-7-27.
 */
@Service
public class ProductService {
    @Autowired
    private ActivityService activityService;

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
        // TODO 这里应该改写成级联删除而不是手动维护
        List<Activity> activities = activityService.findByProduct(productId);
        for (Activity activity:activities){
            activityService.deleteActivity(activity.getId());
        }

        productRepository.delete(productId);
    }

}
