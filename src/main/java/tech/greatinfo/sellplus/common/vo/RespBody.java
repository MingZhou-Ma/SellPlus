/**
 * <html>
 * <body>
 *  <P> Copyright China GuangZhou WanGuo tech-info co,ltd</p>
 *  <p> All rights reserved.</p>
 *  <p> Created by wubin@wanguo.com</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.common.vo;


import java.io.Serializable;

/**
* @Package：tech.greatinfo.sellplus.common.vo   
* @ClassName：RespBody   
* @Description：   <p> api接口响应 </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午5:17:33   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
 */
public class RespBody implements Serializable{

	private static final long serialVersionUID = -6599447507957097341L;
	
	/**
	 * 状态
	 */
	private Status status;
	
	/**
	 * 状态码
	 */
	private Integer code;
	
	/**
	 * 结果
	 */
	private Object result;
	
	/**
	 * 消息描述
	 */
	private String message;

	public RespBody() {
		super();
	}

	public RespBody(Status status) {
		super();
		this.status = status;
	}

	public RespBody(Status status, String message) {
		super();
		this.status = status;
		this.message = message;
	}

	public RespBody(Status status, Object result) {
		super();
		this.status = status;
		this.result = result;
	}

	public RespBody(Status status, Object result, String message) {
		super();
		this.status = status;
		this.result = result;
		this.message = message;
	}
	
	/**  
	* RespBody. 
	* @param status
	* @param code
	* @param message  
	*/  
	public RespBody(Status status, Integer code, String message) {
		super();
		this.status = status;
		this.code = code;
		this.message = message;
	}


	/**
	 * 结果类型信息
	 */
	public enum Status {
		OK, ERROR, FAIL,EXCEPTION
	}

	/**
	 * 添加成功结果信息
	 */
	public void addOK(String message) {
		this.message = message;
		this.status = Status.OK;
	}

	/**
	 * 添加成功结果信息
	 */
	public void addOK(Object result, String message) {
		this.code=200;
		this.message = message;
		this.status = Status.OK;
		this.result = result;
	}

	/**
	 * 添加错误消息
	 */
	public void addError(String message) {
		this.message = message;
		this.status = Status.ERROR;
	}
	
	/**
	 * @Description: 异常信息
	 * @param message
	 * @param code void
	 * @Autor: Jason
	 */
	public void addError(String message,Integer code) {
		this.message = message;
		this.code = code; 
		this.status = Status.ERROR;
	}
	
	/**
	 * @Description: 添加异常信息
	 * @param message 
	 * @Autor: Jason
	 */
	public void addException(String message) {
		this.message = message;
		this.status = Status.EXCEPTION;
	}
	
	/**
	 * @Description: 异常处理
	 * @param message
	 * @param code 
	 * @Autor: Jason
	 */
	public void addException(String message,Integer code) {
		this.message = message;
		this.code = code;
		this.status = Status.EXCEPTION;
	}
	
	public void addFail(String message) {
		this.message = message;
		this.status = Status.ERROR;
	}
	
	public void addFail(String message,Integer code) {
		this.message = message;
		this.code = code;
		this.status = Status.ERROR;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCode() {
		return code;
	}
	
	public void setCode(Integer code) {
		this.code = code;
	}

	
}
