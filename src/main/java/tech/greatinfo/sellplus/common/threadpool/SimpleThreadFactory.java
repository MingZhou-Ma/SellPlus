/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.common.threadpool;

import org.slf4j.LoggerFactory;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

/**     
* @Package：tech.greatinfo.sellplus.common.threadpool   
* @ClassName：SimpleThreadFactory   
* @Description：   <p> SimpleThreadFactory - hreadFactory简单实现,改写自DefaultThreadFactory,使线程具有更有意义的名称. </p>
* @Author： - Jason   
* @CreatTime：2018年8月22日 下午12:22:14   
* @Modify By：   
* @ModifyTime：  2018年8月22日
* @Modify marker：   
* @version    V1.0
*/
public class SimpleThreadFactory implements ThreadFactory{

	protected static org.slf4j.Logger logger = LoggerFactory.getLogger(SimpleThreadFactory.class);
	
	static final AtomicInteger poolNumber = new AtomicInteger(1); //线程池number
	
	final ThreadGroup group; //线程组
	final AtomicInteger threadNumber = new AtomicInteger(1); //线程号
	final String namePrefix; //前缀名
	
	
	public SimpleThreadFactory() {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup(); //线程组
		namePrefix = "pool-" + poolNumber.getAndIncrement() + "-thread-";
	}
	
	
	public SimpleThreadFactory(String threadPoolName) {
		SecurityManager s = System.getSecurityManager();
		group = (s != null) ? s.getThreadGroup() : Thread.currentThread().getThreadGroup();
		namePrefix = threadPoolName + "-" + poolNumber.getAndIncrement() + "-thread-";
	}

	
	@Override
	public Thread newThread(Runnable r) {
		Thread t = new Thread(group, r, namePrefix + threadNumber.getAndIncrement(), 0);
		if (t.isDaemon()) {
			t.setDaemon(false);
		}
		if (t.getPriority() != Thread.NORM_PRIORITY) {
			t.setPriority(Thread.NORM_PRIORITY);
		}
		return t;
	}

}
