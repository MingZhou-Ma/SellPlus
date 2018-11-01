package tech.greatinfo.sellplus.domain;

import javax.persistence.*;

/**
 * 风采标签
 */
@Entity
@Table(name = "StyleLabel")
public class StyleLabel {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '风采标签名'")
    private String labelName;

    public StyleLabel() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
