/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.config.quartz;

import org.quartz.Trigger;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.CronTriggerFactoryBean;
import org.springframework.scheduling.quartz.MethodInvokingJobDetailFactoryBean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import java.io.IOException;

import tech.greatinfo.sellplus.task.biz.taskitem.CouponsTask;

/**     
* @Package：tech.greatinfo.sellplus.config.quartz   
* @ClassName：QuartzConfig   
* @Description：   <p> QuartzConfig 定时任务调度配置</p>
* @Author： - Jason   
* @CreatTime：2018年8月22日 下午3:07:37   
* @Modify By：   
* @ModifyTime：  2018年8月22日
* @Modify marker：   
* @version    V1.0
*/
@Configuration
@ConditionalOnProperty(name="enabled", havingValue ="true",prefix="quartz",matchIfMissing=false)
public class QuartzConfig {

	private static Logger logger = LoggerFactory.getLogger(QuartzConfig.class);
	
	private static String TARGET_METHOD = "startUp";
	
	/**
	 * 延迟启动 - 默认60秒
	 */
	@Value("${quartz.scheduler.startupDelay:60}")
    private Integer startupDelay;
	
	/**
	 * 核心池大小 - 默认5
	 */
	@Value("${quartz.executor.corePoolSize:5}")
    private Integer corePoolSize;
	
	/**
	 * 保持时间 - 默认300
	 */
	@Value("${quartz.executor.keepAliveSeconds:300}")
    private Integer keepAliveSeconds;
	
	/**
	 * 最大池大小-默认10
	 */
	@Value("${quartz.executor.maxPoolSize:10}")
    private Integer maxPoolSize;
	
	/**
	 * 队列容量-默认25
	 */
	@Value("${quartz.executor.queueCapacity:25}")
    private Integer queueCapacity;
	
	//-----------------------BizCronExpression-----------------------------------------
	
	@Value("${quartz.cronExpression.couponCronExpression}")
    private String couponCronExpression;
	
	//----------------------------------------------------------------
	
	/**
	 * @Description: SchedulerFactoryBean 
	 * @param couponTrigger
	 * @return
	 * @throws IOException SchedulerFactoryBean
	 * @Autor: Jason
	 */
	@Bean
    public SchedulerFactoryBean schedulerFactoryBean(Trigger couponTrigger) throws IOException {
        SchedulerFactoryBean factoryBean = new SchedulerFactoryBean();
        //用于quartz集群,QuartzScheduler 启动时更新己存在的Job，这样就不用每次修改targetObject后删除qrtz_job_details表对应记录了
        factoryBean.setOverwriteExistingJobs(true);
        //任务调度监听类
        // 延时启动，应用启动60秒后  
        factoryBean.setStartupDelay(startupDelay); 
        
        /**
         * taskExecutor - customer
         */
        factoryBean.setTaskExecutor(bulidThreadPoolTaskExecutor());//TaskExecutor
        // 注册触发器  
        factoryBean.setTriggers(couponTrigger);
        logger.info("add trigger.....");
        return factoryBean;
    }
	
	
	/**
	 * @Description: taskExecutor 
	 * @return ThreadPoolTaskExecutor
	 * @Autor: Jason
	 */
	@Bean(name="taskExecutor")
	public ThreadPoolTaskExecutor bulidThreadPoolTaskExecutor() {
		ThreadPoolTaskExecutor threadPoolTaskExecutor = new ThreadPoolTaskExecutor();
		threadPoolTaskExecutor.setCorePoolSize(corePoolSize);//5
		threadPoolTaskExecutor.setKeepAliveSeconds(keepAliveSeconds);
		threadPoolTaskExecutor.setMaxPoolSize(maxPoolSize);
		threadPoolTaskExecutor.setQueueCapacity(queueCapacity);
		return threadPoolTaskExecutor;
	}
	
	/**
	 * @Description: jobDetail 
	 * @param couponsTask    需要执行的任务
	 * @return MethodInvokingJobDetailFactoryBean
	 * @Autor: Jason
	 */
	@Bean(name="couponJobDetail")
	public MethodInvokingJobDetailFactoryBean bulidMethodInvokingJobDetailFactoryBean(CouponsTask couponsTask) {
		MethodInvokingJobDetailFactoryBean couponJob = new MethodInvokingJobDetailFactoryBean();
		couponJob.setTargetObject(couponsTask);//任务
		couponJob.setTargetMethod(TARGET_METHOD);//启动方法
		//concurrent：对于相同的JobDetail,当指定多个Trigger时,很可能第一个job完成之前,第二个job就开始了.
		//concurrent设为false,多个job不会并发运行,第二个job将不会在第一个job完成之前开始.默认为true
		couponJob.setConcurrent(false);
		return couponJob;
	}
	
	/**
	 * @Description: 优惠券触发器
	 * @param couponJobDetail
	 * @return CronTriggerFactoryBean
	 * @Autor: Jason
	 */
	@Bean(name="couponTrigger")
	public CronTriggerFactoryBean bulidCronTriggerFactoryBean(MethodInvokingJobDetailFactoryBean couponJobDetail) {
		CronTriggerFactoryBean couponTrigger = new CronTriggerFactoryBean();
		couponTrigger.setJobDetail(couponJobDetail.getObject());  
		couponTrigger.setCronExpression(couponCronExpression);// 初始时的cron表达式
		couponTrigger.setName("couponTrigger");// trigger的name
		return couponTrigger;
	}
	
	/**
	 * @Description: QuartzInitializerListener
	 * @return QuartzInitializerListener
	 * @Autor: Jason
	 */
	@Bean
    public QuartzInitializerListener executorListener() {
		logger.info("QuartZ had listened !");
        return new QuartzInitializerListener();
    }
	
}
