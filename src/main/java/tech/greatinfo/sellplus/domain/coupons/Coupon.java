/**
 * <html>
 * <body>
 *  <P> Copyright Guangzhou Wanguo info-tech co,ltd.</p>
 *  <p> All rights reserved.</p>
 *  <p> Created on 2018年8月13日 下午5:31:20</p>
 *  <p> Created by WanGuo@Wanguo.com </p>
 *  </body>
 * </html>
 */
package tech.greatinfo.sellplus.domain.coupons;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import tech.greatinfo.sellplus.domain.coupons.enums.CouponState;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponType;

/**     
* @Package：tech.greatinfo.sellplus.domain.coupons   
* @ClassName：Coupon   
* @Description：   <p>  优惠券 - 设计参考 -  https://blog.csdn.net/usst_lidawei/article/details/79494386</p>
* @Author： - Jason   
* @CreatTime：2018年8月13日 下午5:31:20   
* @Modify By：   
* @ModifyTime：  2018年8月13日
* @Modify marker：   
* @version    V1.0
*/
@Entity
@Table(name = "coupon")
public class Coupon implements Serializable{
	
	private static final long serialVersionUID = 5372997961291123290L;

	@Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;
	
	/**
	 * 优惠券名称
	 */
	private String name;
	
	/**
	 * 优惠券规则
	 */
	private String rule;
	
	/**
	 * 优惠券类型
	 */
    @Column(columnDefinition = "TINYINT(1) COMMENT '优惠券类型'" )
	private CouponType couponType;
	
	/**
	 * 优惠券状态 
	 */
    @Column(columnDefinition = "TINYINT(1) COMMENT '优惠券状态 '" )
	private  CouponState couponState;
	

	/**
	 * 描述备注
	 */
	@Column(columnDefinition = "VARCHAR(20) COMMENT 'DESC'")
	private String remark;
	
	/**
	* Coupon. 默认未使用 
	 */
    public Coupon(){
    	couponState = CouponState.CHARGED;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRule() {
        return rule;
    }

    public void setRule(String rule) {
        this.rule = rule;
    }

    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CouponType getCouponType() {
		return couponType;
	}

	public void setCouponType(CouponType couponType) {
		this.couponType = couponType;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public CouponState getCouponState() {
		return couponState;
	}

	public void setCouponState(CouponState couponState) {
		this.couponState = couponState;
	}
	
    
}
