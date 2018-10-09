package tech.greatinfo.sellplus.domain;

import javax.persistence.*;

/**
 * Created by Ericwyn on 18-7-31.
 */
@Entity
@Table(name = "qrcode")
public class QRcode {
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

    @Column
    private String type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",columnDefinition = "BIGINT COMMENT '二维码生成者'")
    private Customer customer;

    public QRcode() {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
