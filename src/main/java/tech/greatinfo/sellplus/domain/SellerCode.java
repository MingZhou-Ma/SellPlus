package tech.greatinfo.sellplus.domain;

import javax.persistence.*;

/**
 * 销售渠道码
 */
@Entity
@Table(name = "seller_code")
public class SellerCode {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column
    private String scene;

    @Column
    private String page;

    @Column
    private String path;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",columnDefinition = "BIGINT COMMENT '二维码生成者'")
    private Customer customer;

    public SellerCode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScene() {
        return scene;
    }

    public void setScene(String scene) {
        this.scene = scene;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

//    @Id
//    @GeneratedValue
//    @PrimaryKeyJoinColumn
//    private Long id;
//
//    @Column(columnDefinition = "VARCHAR(100) COMMENT '渠道码名称'")
//    private String name;
//
//    @Column(columnDefinition = "VARCHAR(100) COMMENT '销售码图片路径'")
//    private String path;
//
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "customer_id", columnDefinition = "BIGINT COMMENT '所属的销售'")
//    private Customer customer;
//
//    public SellerCode() {
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getPath() {
//        return path;
//    }
//
//    public void setPath(String path) {
//        this.path = path;
//    }
//
//    public Customer getCustomer() {
//        return customer;
//    }
//
//    public void setCustomer(Customer customer) {
//        this.customer = customer;
//    }
}
