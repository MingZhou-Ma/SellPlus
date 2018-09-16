package tech.greatinfo.sellplus.domain.coupons;

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

import tech.greatinfo.sellplus.domain.Seller;

/**
 * Created by Ericwyn on 18-9-16.
 */
@Entity
@Table(name = "CouponsHistory")
public class CouponsHistory {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_obj_id", columnDefinition = "BIGINT COMMENT '优惠卷'")
    private CouponsObj couponObj;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", columnDefinition = "BIGINT COMMENT '核销的销售'")
    private Seller seller;

    @Column
    private Date date;

    public CouponsHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CouponsObj getCouponObj() {
        return couponObj;
    }

    public void setCouponObj(CouponsObj couponObj) {
        this.couponObj = couponObj;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
