package tech.greatinfo.sellplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tech.greatinfo.sellplus.domain.intf.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

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

    @Column(columnDefinition = "VARCHAR(20) COMMENT '昵称'")
    private String nickname;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "seller_id", columnDefinition = "BIGINT COMMENT '所属销售的外键'")
    private Seller seller;

    @Column(columnDefinition = "BIT COMMENT '是否是销售人员'")
    private Boolean bSell;

    @Column(columnDefinition = "BIT COMMENT '是否是老司机'")
    private Boolean frequenter;

    @Column(columnDefinition = "DOUBLE COMMENT '老司机的奖金总数'")
    private Double freqBonus;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '销售渠道'")
    private String sellerChannel; // 格式：属于哪个销售的uuid+ ":" + 销售渠道名称  （例如：uuid:大众饭店）

    @Column(columnDefinition = "VARCHAR(255) COMMENT '用户第一次访问记录'")
    private String accessRecord;

    @Column(columnDefinition = "TIMESTAMP COMMENT '用户第一次使用小程序的时间，相当于注册时间吧，客户列表按时间排序要用到'")
    private Date createTime;

    @Column(columnDefinition = "BIT COMMENT '是否同步到通讯录'")
    private Boolean bSync;

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

    public Double getFreqBonus() {
        return freqBonus;
    }

    public void setFreqBonus(Double freqBonus) {
        this.freqBonus = freqBonus;
    }

    public String getSellerChannel() {
        return sellerChannel;
    }

    public void setSellerChannel(String sellerChannel) {
        this.sellerChannel = sellerChannel;
    }

    public String getAccessRecord() {
        return accessRecord;
    }

    public void setAccessRecord(String accessRecord) {
        this.accessRecord = accessRecord;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Boolean getbSync() {
        return bSync;
    }

    public void setbSync(Boolean bSync) {
        this.bSync = bSync;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", openid='" + openid + '\'' +
                ", sessionKey='" + sessionKey + '\'' +
                ", uid='" + uid + '\'' +
                ", phone='" + phone + '\'' +
                ", phoneOrigin='" + phoneOrigin + '\'' +
                ", seller=" + seller +
                ", bSell=" + bSell +
                ", frequenter=" + frequenter +
                ", sellerChannel='" + sellerChannel + '\'' +
                ", accessRecord='" + accessRecord + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Customer
                && ((Customer) obj).getOpenid().equals(this.openid);
    }
}
