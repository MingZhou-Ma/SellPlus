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

import tech.greatinfo.sellplus.common.constants.ExceptionConstant;

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

	 /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;
    
    
    /**
     * mysql数据库异常
     */
    public static final SystemException MYSQL_EXXCEPTION = new SystemException(ExceptionConstant.SYS_MYSQL_EXXCEPTION, "Mysql数据库异常");
    
    
    /*
     * BizException 业务异常
     */
    public SystemException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public SystemException() {
        super();
    }

    public SystemException(String message, Throwable cause) {
        super(message, cause);
    }

    public SystemException(Throwable cause) {
        super(cause);
    }

    public SystemException(String message) {
        super(message);
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }

    /**
     * @Description: 实例化异常
     * @param msgFormat
     * @param args
     * @return BizException
     * @Autor: Jason
     */
    public BizException newInstance(String msgFormat, Object... args) {
        return new BizException(this.code, msgFormat, args);
    }
}
