package tech.greatinfo.sellplus.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.persistence.Transient;

import tech.greatinfo.sellplus.domain.intf.User;

/**
 *
 * 微信用户
 * Created by Ericwyn on 18-7-20.
 */
@Entity
@Table(name = "customer")
public class Customer implements User, Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    // 微信 openid 不会超过 50
    @JsonIgnore
    @Column(length = 50)
    private String openid;

    @JsonIgnore
    @Transient
    private String sessionKey;



    public Customer() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getSessionKey() {
        return sessionKey;
    }

    public void setSessionKey(String sessionKey) {
        this.sessionKey = sessionKey;
    }
}
