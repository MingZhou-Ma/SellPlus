package tech.greatinfo.sellplus.common.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextRefreshedEvent;

/**
* @Package：tech.greatinfo.sellplus.common.listener   
* @ClassName：ApplicationContextListener   
* @Description：   <p> 监听容器 可以用来做一些数据的初始化等 </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午5:08:46   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
 */
@Configuration
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent> {

    private static final Logger logger = LoggerFactory.getLogger(ApplicationContextListener.class);

    long startTime = 0;
    
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(null == contextRefreshedEvent.getApplicationContext().getParent()) {
        	logger.info("开始初始化Ehcache Reids缓存数据配置信息等...");
        	init();//初始化方法  可以用来初始化redis缓存等 ..
        	logger.info(">>>> 喜大普奔 恭喜您 您的容器Context初始化成功.<<<");
        }
    }
    
    
    /**
     * @Description: init() 初始化方法
     * @Autor: Jason
     */
    public void init() {
    	startTime = System.currentTimeMillis();//开始计时
    	try {
			Thread.sleep(88);
		} catch (InterruptedException e) {
		}
    	long lastTime = System.currentTimeMillis() - startTime;
    	logger.info("初始化缓存数据Ehcache Reids配置信息等完毕... 耗时:{}ms",lastTime);
    	
    }

}
