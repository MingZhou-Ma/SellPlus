package tech.greatinfo.sellplus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * Diary，用户日志
 *
 * Created by Ericwyn on 18-9-8.
 */
@Entity
@Table(name = "diary")
public class Diary {
    @Id
    @PrimaryKeyJoinColumn
    @Column(length = 32,columnDefinition = "VARCHAR(32) COMMENT '主键 key'")
    private String diaryId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",columnDefinition = "BIGINT COMMENT '发表人'")
    private Customer customer;

    // 记录阅读过的用户的 openid，只记录前特定个数
    @Column
    private String readHistory;

    // 标明这篇心得分享的阅读量是否已经被兑换成了优惠卷
    @Column(name = "general", columnDefinition = "BIT COMMENT '是否已经兑换优惠卷'")
    private boolean general;

    public Diary() {
    }

    public String getDiaryId() {
        return diaryId;
    }

    public void setDiaryId(String diaryId) {
        this.diaryId = diaryId;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public String getReadHistory() {
        return readHistory;
    }

    public void setReadHistory(String readHistory) {
        this.readHistory = readHistory;
    }

    public boolean isGeneral() {
        return general;
    }

    public void setGeneral(boolean general) {
        this.general = general;
    }
}
