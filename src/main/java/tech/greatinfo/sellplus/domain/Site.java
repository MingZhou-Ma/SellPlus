package tech.greatinfo.sellplus.domain;

import javax.persistence.*;

/**
 * 场地管理
 */
@Entity
@Table(name = "site")
public class Site {

    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '场地名称'")
    private String siteName;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '场地图'")
    private String sitePic;

    public Site() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSiteName() {
        return siteName;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getSitePic() {
        return sitePic;
    }

    public void setSitePic(String sitePic) {
        this.sitePic = sitePic;
    }
}
