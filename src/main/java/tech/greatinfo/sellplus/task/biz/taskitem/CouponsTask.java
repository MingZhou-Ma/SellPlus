/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.task.biz.taskitem;

import java.util.Calendar;
import java.util.concurrent.atomic.AtomicLong;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;

import tech.greatinfo.sellplus.task.biz.collector.CouponsCollector;

/**     
* @Package：tech.greatinfo.sellplus.task.biz.taskitem   
* @ClassName：CouponTask   
* @Description：   <p> CouponsTask 优惠券定时过期处理task</p>
* @Author： - Jason   
* @CreatTime：2018年8月22日 下午12:07:45   
* @Modify By：   
* @ModifyTime：  2018年8月22日
* @Modify marker：   
* @version    V1.0
*/
@Component
@EnableScheduling //task任务 
@ConditionalOnProperty(name="enabled", havingValue ="true",prefix="quartz",matchIfMissing=false)
public class CouponsTask {

	private static Logger logger = LoggerFactory.getLogger(CouponsTask.class);
	
	/**
	 * 计数器
	 */
	private final AtomicLong counter = new AtomicLong();
	
	//@Autowired
	//private CouponService CouponService;
	
	public void startUp(){
		long count = counter.incrementAndGet();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 个定时任务开始对优惠券进行处理......");
		}
		Long statrTime = Calendar.getInstance().getTimeInMillis();
		
		
		//-----------------------------biz 业务处理开始--------------------------
		CouponsCollector couponsCollector = new CouponsCollector();
		couponsCollector.startCollector(); //开启处理
		//-----------------------------biz 业务处理结束--------------------------
		
		
		Long endTime = Calendar.getInstance().getTimeInMillis();
		if (logger.isInfoEnabled()) {
			logger.info("第 " + count + " 个定时任务开始对优惠券进行处理结束 ,耗费时间" + ( endTime - statrTime ) + " Millis");
		}
	}
	
}
