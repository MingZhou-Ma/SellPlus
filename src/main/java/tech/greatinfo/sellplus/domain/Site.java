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

    @Column(columnDefinition = "VARCHAR(255) COMMENT '场地详细地址'")
    private String siteAddress;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '场地图'")
    private String sitePic;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '纬度'")
    private String latitude;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '经度'")
    private String longitude;

    @Column(columnDefinition = "TEXT COMMENT '场地描述(长)'")
    private String description;

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

    public String getSiteAddress() {
        return siteAddress;
    }

    public void setSiteAddress(String siteAddress) {
        this.siteAddress = siteAddress;
    }

    public String getSitePic() {
        return sitePic;
    }

    public void setSitePic(String sitePic) {
        this.sitePic = sitePic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}
