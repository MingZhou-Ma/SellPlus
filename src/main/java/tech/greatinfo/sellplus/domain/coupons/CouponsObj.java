package tech.greatinfo.sellplus.domain.coupons;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

import tech.greatinfo.sellplus.domain.Customer;


/**
 * Created by Ericwyn on 18-9-6.
 */
@Entity
@Table(name = "CouponsObj")
public class CouponsObj {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;


    @Column(length = 32,columnDefinition = "VARCHAR(6) COMMENT '6位的优惠卷唯一码'")
    private String code;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id", columnDefinition = "BIGINT COMMENT '卷模板外键'")
    private Coupon coupon;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origin", columnDefinition = "BIGINT COMMENT '发卷人'")
    private Customer origin;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "own", columnDefinition = "BIGINT COMMENT '所属人'")
    private Customer own;

    @Column(name = "expired", columnDefinition = "BIT COMMENT '是否已经核销'")
    private Boolean expired;

    public CouponsObj() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Coupon getCoupons() {
        return coupon;
    }

    public void setCoupons(Coupon coupons) {
        this.coupon = coupons;
    }

    public Customer getOrigin() {
        return origin;
    }

    public void setOrigin(Customer origin) {
        this.origin = origin;
    }

    public Customer getOwn() {
        return own;
    }

    public void setOwn(Customer own) {
        this.own = own;
    }

    public Boolean getExpired() {
        return expired;
    }

    public void setExpired(Boolean expired) {
        this.expired = expired;
    }
}
