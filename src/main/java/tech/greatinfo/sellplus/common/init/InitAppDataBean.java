/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.common.init;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import tech.greatinfo.sellplus.common.exception.BizException;

/**     
* @Package：tech.greatinfo.sellplus.common.init   
* @ClassName：InitAppDataBean   
* @Description：   <p> InitAppDataBean 初始化 Application Bean - 初始化cache数据等 </p>
* @Author： - Jason   
* @CreatTime：2018年8月22日 上午11:49:25   
* @Modify By：   
* @ModifyTime：  2018年8月22日
* @Modify marker：   
* @version    V1.0
*/
//@Component  //注入bean即可  也可以配置到config 其实就是bean的实例化
public class InitAppDataBean implements InitializingBean, DisposableBean{
	
	private static Logger logger = LoggerFactory.getLogger(InitAppDataBean.class);

	
	@Override
	public void destroy() throws Exception {
		logger.info("销毁方法!");
	}

	
	@Override
	public void afterPropertiesSet() throws Exception {
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>InitAppDataBean Start >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		if (isValidateStartUp()) {//是否为合法启动
			initSysData();
			initBizData();
		}else {
			throw new BizException("非法的初始化启动!");
		}
		logger.info(">>>>>>>>>>>>>>>>>>>>>>>>>InitAppDataBean end <<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<");
	}

	/**
	 * @Description: 检测是否为合法启动
	 * @return Boolean
	 * @Autor: Jason
	 */
	private Boolean isValidateStartUp() {
		Boolean isValidate = false;
		return isValidate;
	}
	
	/**
	 * @Description: 初始化系统的数据
	 * @Autor: Jason
	 */
	private void initSysData() {
		//拿到cacheBean  callback init
	}
	
	
	/**
	 * @Description:初始化话业务的数据
	 * @Autor: Jason
	 */
	private void initBizData() {
		//拿到cacheBean callback init
	}
	
	
}
