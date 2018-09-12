/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.task.biz.vo;

/**     
* @Package：tech.greatinfo.sellplus.task.biz.vo   
* @ClassName：TaskResultInfo   
* @Description：   <p> 任务结果信息</p>
* @Author： - Jason   
* @CreatTime：2018年8月22日 下午12:29:28   
* @Modify By：   
* @ModifyTime：  2018年8月22日
* @Modify marker：   
* @version    V1.0
*/
public class TaskResultInfo {

	/**
	 * 任务结果对象
	 */
	private Object taskResult;

	/**
	 * 采集描述信息
	 */
	private String collectCallMsg;

	public Object getTaskResult() {
		return taskResult;
	}

	public void setTaskResult(Object taskResult) {
		this.taskResult = taskResult;
	}

	public String getCollectCallMsg() {
		return collectCallMsg;
	}

	public void setCollectCallMsg(String collectCallMsg) {
		this.collectCallMsg = collectCallMsg;
	}

	/**  
	* TaskResultInfo.   
	*/  
	public TaskResultInfo() {
		super();
	}
	
	
}
