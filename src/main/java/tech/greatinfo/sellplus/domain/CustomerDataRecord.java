package tech.greatinfo.sellplus.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 用户数据记录
 */
@Entity
@Table(name = "customer_data_record")
public class CustomerDataRecord implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column(columnDefinition = "VARCHAR(20) COMMENT '昵称'")
    private String nickname;

    @Column(columnDefinition = "VARCHAR(20) COMMENT '手机号码'")
    private String phone;

    @Column(columnDefinition = "VARCHAR(20) COMMENT '请求url'")
    private String url;

    @Column(columnDefinition = "TIMESTAMP COMMENT '请求数据时间'")
    private Date time;

    public CustomerDataRecord() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }


}
