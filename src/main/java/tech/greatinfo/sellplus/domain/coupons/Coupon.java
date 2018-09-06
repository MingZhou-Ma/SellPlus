package tech.greatinfo.sellplus.domain.coupons;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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

    // 卷的描述
    @Column
    private String content;

    @Column(columnDefinition = "BIT COMMENT '是否是高级别的有限数量优惠卷'")
    private Boolean finite;

    @Column(columnDefinition = "INT COMMENT '优惠卷数量'")
    private Integer num;

    @Column(name = "startDate", columnDefinition = "TIMESTAMP COMMENT '开始的有效时间'")
    private Date startDate;

    @Column(name = "endDate", columnDefinition = "TIMESTAMP COMMENT '过期时间'")
    private Date endDate;

    public Coupon() {
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getFinite() {
        return finite;
    }

    public void setFinite(Boolean finite) {
        this.finite = finite;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
