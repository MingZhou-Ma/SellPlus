package tech.greatinfo.sellplus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
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
    @PrimaryKeyJoinColumn
    @Column(length = 32,columnDefinition = "VARCHAR(32) COMMENT '主键 key'")
    private String k;

    @Column
    private String v;

    public Company() {
    }

    public Company(String key, String value) {
        this.k = key;
        this.v = value;
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
