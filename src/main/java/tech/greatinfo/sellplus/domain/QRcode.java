package tech.greatinfo.sellplus.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

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
    private String scence;

    @Column
    private String page;

    @Column
    private String path;

    public QRcode() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getScence() {
        return scence;
    }

    public void setScence(String scence) {
        this.scence = scence;
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
}
