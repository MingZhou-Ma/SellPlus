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
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import tech.greatinfo.sellplus.domain.coupons.enums.CouponState;
import tech.greatinfo.sellplus.domain.coupons.enums.CouponType;

/**     
* @Package：tech.greatinfo.sellplus.domain.coupons   
* @ClassName：Coupon   
* @Description：   <p>  优惠券 - 设计参考 - 
* https://blog.csdn.net/usst_lidawei/article/details/79494386
* https://blog.csdn.net/egworkspace/article/details/80414953
* jpa的基本使用 {@link https://www.cnblogs.com/a8457013/p/7753575.html}
* </p>
* @Author： - Jason   
* @CreatTime：2018年8月13日 下午5:31:20   
* @Modify By：   
* @ModifyTime：  2018年8月13日
* @Modify marker：   
* @version    V1.0
*/
@Entity
@Table(name = "coupon")
@GenericGenerator(name="idGenerator", strategy="uuid") //hibernate的注解-生成32位UUID
public class Coupon implements Serializable{
	
	private static final long serialVersionUID = 5372997961291123290L;

	/**
	 * 主键id
	 */
	@Id
	@PrimaryKeyJoinColumn
	@GeneratedValue(generator="idGenerator")
	@Column(length = 32,columnDefinition = "VARCHAR(32) COMMENT '主键id'")
    private String id;
	

	/**
	 * 商家ID<br/>
	 */
	@GeneratedValue(generator="idGenerator")
	@Column(length = 32,columnDefinition = "VARCHAR(32) COMMENT '商家ID'")
    private String vendorId;
	
	
	@GeneratedValue(generator="idGenerator")
	@Column(length = 32,columnDefinition = "VARCHAR(32) COMMENT '销售人员'")
    private String salesmanId;
	
	/**
	 * 优惠券名称<br/>
	 */
	@Column(columnDefinition = "VARCHAR(32) COMMENT '优惠券名称'" )
	private String name;
	
    
    /**
     *活动编码<br/>
     **/
    @Column(columnDefinition = "VARCHAR(32) COMMENT '优惠券名称'" )
    private String actNo;
    
    /**
     *活动名称<br/>
     **/
    @Column(columnDefinition = "VARCHAR(32) COMMENT '活动名称'" )
    private String actName;
    
    /**
     * 优惠券编码 - 优惠券券码 <br/>
     */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '优惠券券码'" )
    private String cpCode;
	
	/**
	 * 优惠金额<br/>
	 */
    @Column(columnDefinition = "INTEGER COMMENT '优惠金额'" )
	private BigDecimal amount;
	
	/**
	 * 实际使用金额<br/>
	 */
    @Column(columnDefinition = "INTEGER COMMENT '优惠金额'" )
	private BigDecimal useValue;
	
	/**
	 * 优惠券规则<br/>
	 */
    @Column(columnDefinition = "VARCHAR(32) COMMENT '优惠券规则'" )
	private String rule;
	
	/**
	 * 优惠券类型<br/>
	 */
    @Column(columnDefinition = "TINYINT(2) COMMENT '优惠券类型'" )
    @Enumerated(EnumType.ORDINAL)  //枚举类型
	private CouponType couponType;
	
	/**
	 * 优惠券状态 <br/>
	 */
    @Column(columnDefinition = "TINYINT(2) COMMENT '优惠券状态 '" )
    @Enumerated(EnumType.ORDINAL) //枚举类型
	private  CouponState couponState;
    
    /**
     * 发券时间<br/>
     */
	@Column(columnDefinition = "TIMESTAMP COMMENT '发券时间'" )
    private Date sendCouponDate;
    
    /**
     * 使用券的时间<br/>
     */
	@Column(columnDefinition = "TIMESTAMP COMMENT '使用券时间'" )
    private Date usedCouponDate;
    
    /**
     * 作废券时间<br/>
     */
	@Column(columnDefinition = "TIMESTAMP COMMENT '作废券时间'" )
    private Date validCouponDate;
	
	/**
     * 优惠券生效日期<br/>
     */
    @Column(columnDefinition = "TIMESTAMP COMMENT '生效日期'" )
    private Date startDate;
    
    /**
     * 优惠券结束日期<br/>
     */
    @Column(columnDefinition = "TIMESTAMP COMMENT '结束日期'" )
    private Date endDate;

	/**
	 * 优惠券描述备注<br/>
	 */
	@Column(columnDefinition = "VARCHAR(50) COMMENT '描述备注'")
	private String remark;

    /**
     *创建人<br/>
     **/
	@Column(columnDefinition = "VARCHAR(32) COMMENT '创建人'" )
    private String creator;
	
	 /**
     *创建日期<br/>
     **/
    @CreatedDate
	@Column(columnDefinition = "TIMESTAMP COMMENT '创建日期'" )
    private Date createDate;

    /**
     *修改日期<br/>
     **/
    @LastModifiedDate
	@Column(columnDefinition = "TIMESTAMP COMMENT '修改日期'" )
    private Date modifyDate;
	
	
	
	/**
	* Coupon. 默认未使用 
	 */
    public Coupon(){
    	couponState = CouponState.FRESH;
    }
    
	public String getSalesmanId() {
		return salesmanId;
	}

	public void setSalesmanId(String salesmanId) {
		this.salesmanId = salesmanId;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
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

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	
	public String getVendorId() {
		return vendorId;
	}

	public void setVendorId(String vendorId) {
		this.vendorId = vendorId;
	}

	public String getActNo() {
		return actNo;
	}

	public void setActNo(String actNo) {
		this.actNo = actNo;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getCpCode() {
		return cpCode;
	}

	public void setCpCode(String cpCode) {
		this.cpCode = cpCode;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getUseValue() {
		return useValue;
	}

	public void setUseValue(BigDecimal useValue) {
		this.useValue = useValue;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public Date getSendCouponDate() {
		return sendCouponDate;
	}

	public void setSendCouponDate(Date sendCouponDate) {
		this.sendCouponDate = sendCouponDate;
	}

	public Date getUsedCouponDate() {
		return usedCouponDate;
	}

	public void setUsedCouponDate(Date usedCouponDate) {
		this.usedCouponDate = usedCouponDate;
	}

	public Date getValidCouponDate() {
		return validCouponDate;
	}

	public void setValidCouponDate(Date validCouponDate) {
		this.validCouponDate = validCouponDate;
	}

	public String getCreator() {
		return creator;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}
    
}
