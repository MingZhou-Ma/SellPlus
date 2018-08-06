package tech.greatinfo.sellplus.domain;

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

/**
 * Created by Ericwyn on 18-7-23.
 */
@Entity
@Table(name = "activity")
public class Activity {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",columnDefinition = "BIGINT COMMENT '商品外键'")
    private Product product;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '活动标题'")
    private String headline;

    @Column(columnDefinition = "BIT COMMENT '是否是拼团活动, true 为拼团'")
    private Boolean isGroup;

    @Column(columnDefinition = "INT COMMENT '拼团目标人数'")
    private Integer groupNum;

    @Column(columnDefinition = "INT COMMENT '助力目标人数'")
    private Integer helpNum;

    @Column(columnDefinition = "TIMESTAMP COMMENT '开始时间'")
    private Date startDate;

    @Column(columnDefinition = "TIMESTAMP COMMENT '结束时间'")
    private Date endDate;

    @Column(columnDefinition = "DOUBLE COMMENT '拼团价格'")
    private Double groupPrice;

    // 判断活动状态, 0 为未开始, 1为正在进行, 2为已经过期
    @Transient
    private Integer status;

    public Activity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public void setGroupNum(Integer groupNum) {
        this.groupNum = groupNum;
    }

    public Integer getGroupNum() {
        return groupNum;
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

    public Double getGroupPrice() {
        return groupPrice;
    }

    public void setGroupPrice(Double groupPrice) {
        this.groupPrice = groupPrice;
    }

    public Integer getHelpNum() {
        return helpNum;
    }

    public void setHelpNum(Integer helpNum) {
        this.helpNum = helpNum;
    }

    public Boolean getGroup() {
        return isGroup;
    }

    public void setGroup(Boolean group) {
        isGroup = group;
    }

    public Integer getStatus(){
        long now = System.currentTimeMillis();
        if (now<this.startDate.getTime()){
            return 0;
        }else if (now >= this.startDate.getTime() && now <= this.endDate.getTime()){
            return 1;
        }else {
            return 2;
        }
    }
}
