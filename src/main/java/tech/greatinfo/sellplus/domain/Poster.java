package tech.greatinfo.sellplus.domain;

import javax.persistence.*;

/**
 * 海报中心
 */
@Entity
@Table(name = "poster")
public class Poster {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '朋友圈文案'")
    private String copyWriting;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '海报地址'")
    private String pic;

    @Column(columnDefinition = "INT COMMENT '海报类型'")
    private Integer type;

    public Poster() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCopyWriting() {
        return copyWriting;
    }

    public void setCopyWriting(String copyWriting) {
        this.copyWriting = copyWriting;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
