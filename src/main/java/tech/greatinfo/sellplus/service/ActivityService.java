package tech.greatinfo.sellplus.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.List;

import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.repository.ActivityRepository;

/**
 * Created by Ericwyn on 18-7-27.
 */
@Service
public class ActivityService {
    @Autowired
    ActivityRepository activityRepository;

    public void save(Activity activity){
        activityRepository.save(activity);
    }

    public Activity findOne(Long id){
        return activityRepository.findOne(id);
    }

    public Page<Activity> getAllGroupAct(int start, int num){
        return activityRepository.getAllByIsGroupTrue(new PageRequest(start,num));
    }

    public Page<Activity> getAllHelpAct(int start, int num){
        return activityRepository.getAllByIsGroupFalse(new PageRequest(start,num));
    }

//    public Page<Activity> findAllByMerchant(Merchant merchant, int start, int num){
//        return activityRepository.findAllByMerchant(merchant, new PageRequest(start, num));
//    }

    public void deleteActivity(Long activityId){
        activityRepository.delete(activityId);
    }

    public void updateActivity(Activity oldEntity, Activity newEntity){
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
        activityRepository.saveAndFlush(oldEntity);
    }

}
