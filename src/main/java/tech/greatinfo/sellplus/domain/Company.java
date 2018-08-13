package tech.greatinfo.sellplus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * 存储各种公司信息的表
 *
 * Created by Ericwyn on 18-8-13.
 */
@Entity
@Table(name = "CompanyInfo")
public class Company {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column
    private String k;

    @Column
    private String v;

    public Company() {
    }

    public Company(String key, String value) {
        this.k = key;
        this.v = value;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getK() {
        return k;
    }

    public void setK(String k) {
        this.k = k;
    }

    public String getV() {
        return v;
    }

    public void setV(String v) {
        this.v = v;
    }
}
