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

/**
* @Package：tech.greatinfo.sellplus.common.exception   
* @ClassName：SystemException   
* @Description：   <p> 系统异常 </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午4:20:11   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
 */
public class SystemException extends RuntimeException {
	
	private static final long serialVersionUID = 1401593546385403720L;

	public SystemException() {
		super();
	}

	public SystemException(String message) {
		super(message);
	}

	public SystemException(Throwable cause) {
		super(cause);
	}

	public SystemException(String message, Throwable cause) {
		super(message, cause);
	}
}
