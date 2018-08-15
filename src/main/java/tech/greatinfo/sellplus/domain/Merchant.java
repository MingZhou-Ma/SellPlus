package tech.greatinfo.sellplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import tech.greatinfo.sellplus.domain.intf.User;

/**
 *
 * 商家
 *
 * Created by Ericwyn on 18-7-20.
 */
@Entity
@Table(name = "merchant")
public class Merchant implements User {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    @Column(name = "id")
    private Long id;

    @JsonIgnore
    @Column(length = 50,columnDefinition = "VARCHAR(50) COMMENT '商家帐号'")
    private String account;

    @JsonIgnore
    @Column(columnDefinition = "VARCHAR(100) COMMENT '商家密码'")
    private String password;

//    @Column(columnDefinition = "VARCHAR(100) COMMENT '商家名称'")
//    private String name;
//
    @Column(columnDefinition = "VARCHAR(100) COMMENT '店铺名称'")
    private String shopName;
//
//    @Column(columnDefinition = "VARCHAR(255) COMMENT '营业执照'")
//    private String business;
//
    @Column(columnDefinition = "VARCHAR(20) COMMENT '商家电话'")
    private String phone;
//
//    @Column(columnDefinition = "BIT COMMENT '商家性别,true 为男性, false 为女性'")
//    private Boolean gender;
//
//    @Column(columnDefinition = "BIT COMMENT '是否已经审核'")
//    private Boolean audit;
//
//    @Column(columnDefinition = "VARCHAR(50) COMMENT '商家 x 坐标'")
//    private String xAxis;
//
//    @Column(columnDefinition = "VARCHAR(50) COMMENT '商家 y 坐标'")
//    private String yAxis;
//
    @Column(columnDefinition = "TEXT COMMENT '商家具体地址'")
    private String location;

    public Merchant() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

//    public String getBusiness() {
//        return business;
//    }
//
//    public void setBusiness(String business) {
//        this.business = business;
//    }
//
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
//
//    public Boolean getGender() {
//        return gender;
//    }
//
//    public void setGender(Boolean gender) {
//        this.gender = gender;
//    }
//
//    public Boolean getAudit() {
//        return audit;
//    }
//
//    public void setAudit(Boolean audit) {
//        this.audit = audit;
//    }
//
//    public String getxAxis() {
//        return xAxis;
//    }
//
//    public void setxAxis(String xAxis) {
//        this.xAxis = xAxis;
//    }
//
//    public String getyAxis() {
//        return yAxis;
//    }
//
//    public void setyAxis(String yAxis) {
//        this.yAxis = yAxis;
//    }
//
    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Merchant
                && ((Merchant) o).getId().equals(this.getId())
                && ((Merchant) o).getAccount().equals(this.getAccount());
    }
}
