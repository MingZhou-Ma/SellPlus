package tech.greatinfo.sellplus.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 老司机提现记录
 */
@Entity
@Table(name = "freq_withdraw")
public class FreqWithdraw {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '支付宝'")
    private String aLiPay;

    @Column(columnDefinition = "VARCHAR(100) COMMENT '真实姓名'")
    private String actualName;

    @Column(columnDefinition = "DOUBLE COMMENT '提现金额'")
    private Double withdrawalAmount;

    @Column(columnDefinition = "BIT COMMENT '是否交易完成'")
    private Boolean isSuccessTran;

    @Column(columnDefinition = "TIMESTAMP COMMENT '提现时间'")
    private Date withdrawalTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",columnDefinition = "BIGINT COMMENT '提现的对象'")
    private Customer customer;

    public FreqWithdraw() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getaLiPay() {
        return aLiPay;
    }

    public void setaLiPay(String aLiPay) {
        this.aLiPay = aLiPay;
    }

    public String getActualName() {
        return actualName;
    }

    public void setActualName(String actualName) {
        this.actualName = actualName;
    }

    public Double getWithdrawalAmount() {
        return withdrawalAmount;
    }

    public void setWithdrawalAmount(Double withdrawalAmount) {
        this.withdrawalAmount = withdrawalAmount;
    }

    public Boolean getSuccessTran() {
        return isSuccessTran;
    }

    public void setSuccessTran(Boolean successTran) {
        isSuccessTran = successTran;
    }

    public Date getWithdrawalTime() {
        return withdrawalTime;
    }

    public void setWithdrawalTime(Date withdrawalTime) {
        this.withdrawalTime = withdrawalTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
