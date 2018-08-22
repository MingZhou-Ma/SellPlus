/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月13日 下午6:08:01</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.domain.coupons.enums;

/**     
* @Package：tech.greatinfo.sellplus.domain.coupons.enums   
* @ClassName：CouponType   
* @Description：   <p> CouponType 优惠券类型</p>
* @Author： - Jason   
* @CreatTime：2018年8月13日 下午6:08:01   
* @Modify By：   
* @ModifyTime：  2018年8月13日
* @Modify marker：   
* @version    V1.0
*/
public enum CouponType {
	
	CASH(0,"现金券"), //现金券
	COUPON(1,"优惠券"),//优惠券
	PHYSICAL(4,"实物券"),//实物券
	OLDER_DIVER(4,"老司机券"),//老司机券
	//..etc
	;
     
	/**
	 * code
	 */
	private int code;
	
	/**
	 * 类型
	 */
    private String type;

    
	private CouponType(int code, String type) {
		this.code = code;
		this.type = type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getType() {
		return type;
	}
	
 	public void setType(String type) {
		this.type = type;
	}

}
