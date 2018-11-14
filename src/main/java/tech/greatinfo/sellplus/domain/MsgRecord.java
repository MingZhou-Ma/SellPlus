package tech.greatinfo.sellplus.domain;

import javax.persistence.*;
import java.util.Date;

/**
 * 短信记录
 */
@Entity
@Table(name = "MsgRecord")
public class MsgRecord {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column(columnDefinition = "INT COMMENT '发送条数'")
    private Integer num;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '发送内容'")
    private String content;

    @Column(columnDefinition = "TIMESTAMP COMMENT '发送时间'")
    private Date sendTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",columnDefinition = "BIGINT COMMENT '预约人'")
    private Customer customer;


    public MsgRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
