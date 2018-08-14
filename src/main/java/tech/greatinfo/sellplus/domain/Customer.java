package tech.greatinfo.sellplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

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
 *
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

    @Column(columnDefinition = "VARCHAR(32) COMMENT '用户识别码'")
    private String uid;

    @Column(columnDefinition = "VARCHAR(20) COMMENT '手机号码'")
    private String phone;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id",columnDefinition = "BIGINT COMMENT '所属销售的外键'")
    private Seller seller;

    @Column(columnDefinition = "BIT COMMENT '是否是销售人员'",updatable = false)
    private boolean bSell;

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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public boolean isbSell() {
        return bSell;
    }

    public void setbSell(boolean bSell) {
        this.bSell = bSell;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
