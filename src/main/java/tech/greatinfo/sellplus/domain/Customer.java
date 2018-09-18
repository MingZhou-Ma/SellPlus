package tech.greatinfo.sellplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import tech.greatinfo.sellplus.domain.intf.User;

/**
 * 微信用户
 * Created by Ericwyn on 18-7-20.
 */
@Entity
@Table(name = "customer")
public class Customer implements User, Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    // 微信 openid 不会超过 50
    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(50) COMMENT '微信 OPEN ID'")
    private String openid;

    @JsonIgnore
    @Transient
    private String sessionKey;

    @Column
    private String uid;

    @Column(columnDefinition = "VARCHAR(20) COMMENT '手机号码'")
    private String phone;

    @Column(columnDefinition = "VARCHAR(20) COMMENT '电话来源'")
    private String phoneOrigin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", columnDefinition = "BIGINT COMMENT '所属销售的外键'")
    private Seller seller;

    @Column(columnDefinition = "BIT COMMENT '是否是销售人员'")
    private Boolean bSell;

    @Column(columnDefinition = "BIT COMMENT '是否是老司机'")
    private Boolean frequenter;

    @Column(columnDefinition = "VARCHAR(20) COMMENT '销售渠道'")
    private String sellerChannel; // 格式：属于哪个销售的uuid+ ":" + 销售渠道名称  （例如：uuid:大众饭店）


//    @Column(columnDefinition = "VARCHAR(20) COMMENT '实名'")
//    private String name;
//
//    @Column(columnDefinition = "TINYINT(2) COMMENT '用户类型'")
//    private Integer type;
//
//    @Column(columnDefinition = "VARCHAR(20) COMMENT '用户来源'")
//    private String origin;
//
//    @Column(columnDefinition = "TIMESTAMP COMMENT '用户第一次使用小程序的时间，相当于注册时间吧，客户列表按时间排序要用到'")
//    private Date createTime;

    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneOrigin() {
        return phoneOrigin;
    }

    public void setPhoneOrigin(String phoneOrigin) {
        this.phoneOrigin = phoneOrigin;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Boolean getbSell() {
        return bSell;
    }

    public void setbSell(Boolean bSell) {
        this.bSell = bSell;
    }

    public Boolean getFrequenter() {
        return frequenter;
    }

    public void setFrequenter(Boolean frequenter) {
        this.frequenter = frequenter;
    }

    public String getSellerChannel() {
        return sellerChannel;
    }

    public void setSellerChannel(String sellerChannel) {
        this.sellerChannel = sellerChannel;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Customer
                && ((Customer) obj).getOpenid().equals(this.openid);
    }
}
