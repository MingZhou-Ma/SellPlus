/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.task.constant;

/**     
* @Package：tech.greatinfo.sellplus.task.constant   
* @ClassName：TaskConstant   
* @Description：   <p> TaskConstant </p>
* @Author： - Jason   
* @CreatTime：2018年8月22日 下午12:13:14   
* @Modify By：   
* @ModifyTime：  2018年8月22日
* @Modify marker：   
* @version    V1.0
*/
public class TaskConstant {
	
	/**
	 * 每核Cpu负载的最大线程队列数
	 */
	public static final float POOL_SIZE = 1.5f;

	/**
	 * 线程
	 */
	public static final int threadNum;

	/**
	 * 静态代码块初始化数据-CPU核心数
	 */
	static {
		//cpu核数
		int cpuNums = Runtime.getRuntime().availableProcessors();
		float MathNum = cpuNums * TaskConstant.POOL_SIZE; //1.5倍扩充
		threadNum = (int) MathNum; //可以支持的线程数
	}

}
