package tech.greatinfo.sellplus.domain;

import javax.persistence.*;

/**
 *
 * 商品表
 * Created by Ericwyn on 18-7-23.
 */
@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '商品题图'")
    private String pic;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '商品名称'")
    private String name;

    @Column(columnDefinition = "VARCHAR(140) COMMENT '商品简介(短)'")
    private String intro;

    @Column(columnDefinition = "TEXT COMMENT '商品描述(长)'")
    private String depiction;

    @Column(columnDefinition = "DOUBLE COMMENT '商品价格'")
    private Double price;

    @Transient
    private String[] picList;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '标签'")
    private String label;

    public Product() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getDepiction() {
        return depiction;
    }

    public void setDepiction(String depiction) {
        this.depiction = depiction;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String[] getPicList() {
        return pic.split(",");
    }

    public void setPicList(String[] picList) {
        this.picList = picList;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
