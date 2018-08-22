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

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import tech.greatinfo.sellplus.common.vo.RespBody;

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

	/**
	 * @Description: SQLException 
	 * @param request
	 * @param e
	 * @return RespBody
	 * @Autor: Jason
	 */
	@ExceptionHandler(value = SQLException.class)
	@ResponseBody
	public RespBody sqlExceptionHandler(HttpServletRequest request, SQLException e) {
		RespBody respBody = new RespBody();
		respBody.addError(e.getMessage());
		return respBody;
	}
	
	/**
	 * @Description: RuntimeException 运行时异常处理
	 * @param request
	 * @param e
	 * @return RespBody
	 * @Autor: Jason
	 */
	@ExceptionHandler(value = RuntimeException.class)
	@ResponseBody
	public RespBody runtimeExceptionHandler(HttpServletRequest request, RuntimeException e) {
		RespBody respBody = new RespBody();
		respBody.addError(e.getMessage());
		return respBody;
	}
	
	
	/**
	 * @Description: SystemException 自定义异常的统一处理
	 * @param request
	 * @param e
	 * @return RespBody
	 * @Autor: Jason
	 */
	@ExceptionHandler(value = SystemException.class)
	@ResponseBody
	public RespBody systemExceptionHandler(HttpServletRequest request, SystemException e) {
		RespBody respBody = new RespBody();
		respBody.addError(e.getMessage());
		return respBody;
	}
	
	
	/**
	 * @Description: BizException 自定义的异常处理
	 * @param request
	 * @param e
	 * @return RespBody
	 * @Autor: Jason
	 */
	@ExceptionHandler(value = BizException.class)
	@ResponseBody
	public RespBody bizExceptionHandler(HttpServletRequest request, BizException e) {
		RespBody respBody = new RespBody();
		respBody.addError(e.getMessage());
		return respBody;
	}
	

}