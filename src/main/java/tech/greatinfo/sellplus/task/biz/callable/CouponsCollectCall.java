/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.task.biz.callable;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import tech.greatinfo.sellplus.task.biz.vo.TaskResultInfo;

/**     
* @Package：tech.greatinfo.sellplus.task.biz.callable   
* @ClassName：CouponsCollectCall   
* @Description：   <p> CouponsCollectCall </p>
* @Author： - Jason   
* @CreatTime：2018年8月22日 下午12:28:29   
* @Modify By：   
* @ModifyTime：  2018年8月22日
* @Modify marker：   
* @version    V1.0
*/
public class CouponsCollectCall implements Callable<TaskResultInfo>{

	private static Logger logger = LoggerFactory.getLogger(CouponsCollectCall.class);
	
	/**
	 * 多线程处理...  T submit 结果集返回
	 * 
	 * 异常的放到队列处理 -- 超过次数不再处理
	 */
	@Override
	public TaskResultInfo call() throws Exception {
		logger.debug("-----------------------CouponsCollectCall start -----------------");
		TaskResultInfo taskResultInfo = new TaskResultInfo();
		taskResultInfo.setTaskResult("CouponsCollectCall Has Called");
		taskResultInfo.setCollectCallMsg("Biz业务进行处理call");
		logger.info("CouponsCollectCall Has Callded");
		return taskResultInfo;
	}

}
