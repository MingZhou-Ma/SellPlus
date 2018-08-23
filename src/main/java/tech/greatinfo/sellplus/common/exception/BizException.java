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
* @ClassName：BizException   
* @Description：   <p> 业务异常基类 - 统一的异常码的处理 </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午4:18:59   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
 */
public class BizException extends RuntimeException {

    private static final long serialVersionUID = -5875371379845226068L;
    
    /**
     * 会话超时　获取session时
     */
    public static final BizException SESSION_IS_OUT_TIME = new BizException(ExceptionConstant.BIZ_SESSION_IS_OUT_TIME, "Seesion会话超时");
    
    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    
    /*
     * BizException 业务异常
     */
    public BizException(int code, String msgFormat, Object... args) {
        super(String.format(msgFormat, args));
        this.code = code;
        this.msg = String.format(msgFormat, args);
    }

    public BizException() {
        super();
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }

    public BizException(String message) {
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
