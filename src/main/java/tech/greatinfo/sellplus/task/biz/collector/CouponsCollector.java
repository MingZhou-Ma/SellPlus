/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.task.biz.collector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**     
* @Package：tech.greatinfo.sellplus.task.biz.collector   
* @ClassName：CouponsCollector   
* @Description：   <p> CouponsCollector 需要处理的数据的采集
* https://www.cnblogs.com/zhaoyan001/p/7049627.html
* https://www.cnblogs.com/sunxucool/p/3156898.html
* https://www.cnblogs.com/liaojie970/p/8080595.html
* </p>
* @Author： - Jason   
* @CreatTime：2018年8月22日 下午12:12:03   
* @Modify By：   
* @ModifyTime：  2018年8月22日
* @Modify marker：   
* @version    V1.0
*/
public class CouponsCollector {

	public static Logger logger = LoggerFactory.getLogger(CouponsCollector.class);
	
	/**
	 * name 线程区分
	 */
	public static String COLLECTOR_NAME = "CouponsCollector";
	
	
	public void startCollector() {
//		int threadNum = TaskConstant.threadNum;
//
//		//开始采集数据
//		if (threadNum > 0) {
//			//创建一个定长线程池，可控制线程最大并发数，超出的线程会在队列中等待
//			ExecutorService collectExec = Executors.newFixedThreadPool(threadNum, new SimpleThreadFactory(COLLECTOR_NAME));
//
//			List<FutureTask<TaskResultInfo>> taskList = new ArrayList<FutureTask<TaskResultInfo>>();
//
//			CouponsCollectCall couponsCollectCall = new CouponsCollectCall();
//
//			FutureTask<TaskResultInfo> couponsCollectCallTask = new FutureTask<TaskResultInfo>(couponsCollectCall);//callable
//
//
//			/*collectExec.execute(new Runnable() { //exectue是没有返回值的  runnable
//
//				@Override
//				public void run() {
//
//				}
//			});*/
//
//			taskList.add(couponsCollectCallTask);
//			collectExec.submit(couponsCollectCallTask);//submit 有返回值,Runnable接口
//
//			/**
//			 * result
//			 */
//			for (FutureTask<TaskResultInfo> futureTask : taskList) {
//				try {
//					logger.info(futureTask.get().getCollectCallMsg());
//				} catch (InterruptedException e) {
//					e.printStackTrace();
//				} catch (ExecutionException e) {
//					e.printStackTrace();
//				}
//
//			}
//			collectExec.shutdown();
//		}
		
	}
}
