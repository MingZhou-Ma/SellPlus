/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月14日 下午1:50:35</p>
 *  <p> Created by Jason </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.domain.coupons;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

/**     
* @Package：tech.greatinfo.sellplus.domain.coupons   
* @ClassName：CashForCoupon   
* @Description：   <p> CashForCoupon 现金券的券实体 </p>
* @Author： - Jason   
* @CreatTime：2018年8月14日 下午1:50:35   
* @Modify By：   
* @ModifyTime：  2018年8月14日
* @Modify marker：   
* @version    V1.0
*/
@Entity
@Table(name = "cashCoupon")
@GenericGenerator(name="idGenerator", strategy="uuid") 
public class CashForCoupon {
	
	
	/**
	 * 券实体 id - 唯一标志
	 */
	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(generator="idGenerator")
	@Column(length = 32,columnDefinition = "VARCHAR(32) COMMENT '主键id'")
    private String id;
	
	
	/**
	 * 用户的id
	 */
	@GeneratedValue(generator="idGenerator")
	@Column(length = 32,columnDefinition = "VARCHAR(32) COMMENT '用户id'")
    private String userId;
	
	
	//... service id
	/**
	 * 
	 */
	@GeneratedValue(generator="idGenerator")
	@Column(length = 32,columnDefinition = "VARCHAR(32) COMMENT '服务id'")
    private String serviceId;

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getServiceId() {
		return serviceId;
	}


	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	
	
	//abs id
	 
	
	//
	
	
	//前端展示
	
	
	
	

}
