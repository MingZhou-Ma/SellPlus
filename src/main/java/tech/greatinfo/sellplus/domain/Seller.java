package tech.greatinfo.sellplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tech.greatinfo.sellplus.domain.intf.User;

import javax.persistence.*;

/**
 *
 * 销售人员
 *
 * Created by Ericwyn on 18-8-8.
 */
@Entity
@Table(name = "seller")
public class Seller implements User {
    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    // Seller 的帐号
    @Column
    private String account;

    // Seller 的Key
    @JsonIgnore
    @Column
    private String sellerKey;

    // Seller 的 OPENID
//    @JsonIgnore
//    @Column
//    private String openId;

    // Seller 的联系名称
    @Column
    private String name;

    // Seller 的联系电话
    @Column
    private String phone;

    // Seller 微信
    @Column
    private String wechat;

    // 头像
    @Column
    private String pic;

    // Seller 个人简介
    @Column
    private String intro;

    public Seller() {
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

    public String getSellerKey() {
        return sellerKey;
    }

    public void setSellerKey(String sellerKey) {
        this.sellerKey = sellerKey;
    }

//    public String getOpenId() {
//        return openId;
//    }
//
//    public void setOpenId(String openId) {
//        this.openId = openId;
//    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getWechat() {
        return wechat;
    }

    public void setWechat(String wechat) {
        this.wechat = wechat;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Seller
                && this.getAccount().equals(((Seller) obj).getAccount())
                && this.getId().equals(((Seller) obj).getId());
    }
}
