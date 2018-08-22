/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月13日 下午6:19:31</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.domain.coupons.enums;

/**     
* @Package：tech.greatinfo.sellplus.domain.coupons.enums   
* @ClassName：CouponState   
* @Description：   <p> CouponState </p>
* @Author： - Jason   
* @CreatTime：2018年8月13日 下午6:19:31   
* @Modify By：   
* @ModifyTime：  2018年8月13日
* @Modify marker：   
* @version    V1.0
*/
public enum CouponState {
	 
	FRESH(0,"未使用"), //未使用
    OUT(1,"已发放"), //已发放
    USED(2,"已使用"), //已使用
    CHARGED(3,"已对账"), //已对账
    EXPIRE(4,"已过期");//已过期
     
	/**
	 * 状态码
	 */
	private int code;
	
	/**
	 * 状态
	 */
    private String state;
    
	private CouponState(int code, String state) {
		this.code = code;
		this.state = state;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getState() {
		return state;
	}
	
 	public void setState(String type) {
		this.state = type;
	}

}
