/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月14日 下午3:56:28</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.common.exception;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
* @Package：tech.greatinfo.sellplus.common.exception   
* @ClassName：GlobalExceptionResolver   
* @Description：   <p> 全局异常处理器 - respbody-</p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午4:19:33   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
 */
@ControllerAdvice 
public class GlobalExceptionResolver {
	
	private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionResolver.class);

	/**
	* GlobalExceptionHanlder. - 全局异常处理器
	 */
	public GlobalExceptionResolver() {
		logger.info("启用全局异常处理器...");
	}

	@ExceptionHandler(value = SQLException.class)
	@ResponseBody
	public Map<String, String> sqlExceptionHandler(HttpServletRequest request, SQLException e) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("statusCode", "100005");
		return map;
	}

}