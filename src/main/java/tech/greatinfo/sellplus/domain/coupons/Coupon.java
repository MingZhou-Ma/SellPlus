package tech.greatinfo.sellplus.domain.coupons;

import javax.persistence.*;

/**
 *
 * 优惠卷模型
 * 标记优惠卷的基本信息
 *
 * Created by Ericwyn on 18-9-6.
 */
@Entity
@Table(name = "Coupons")
public class Coupon {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    // 卷的标题
    @Column
    private String title;

    @Column(name = "amount", columnDefinition = "VARCHAR(255) COMMENT '金额'")
    private String amount;

    @Column(columnDefinition = "TEXT COMMENT '券描述(长)'")
    private String description;

    public Coupon() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
