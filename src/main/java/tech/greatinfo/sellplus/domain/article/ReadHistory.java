package tech.greatinfo.sellplus.domain.article;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import tech.greatinfo.sellplus.domain.Customer;

/**
 *
 * 阅读记录
 *
 * Created by Ericwyn on 18-8-8.
 */
@Entity
@Table(name = "ReadHistory")
public class ReadHistory {
    private static final long serialVersionUID = -1L;
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "custom_id",columnDefinition = "BIGINT COMMENT '阅读者'")
    private Customer custom;

    @Column(columnDefinition = "TIMESTAMP COMMENT '阅读时间'")
    private Date readTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "article_id",columnDefinition = "BIGINT COMMENT '阅读的文章的外键'")
    private Article article;

    public ReadHistory() {
    }

    @Column(columnDefinition = "DOUBLE COMMENT '拼团价格'")

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getCustom() {
        return custom;
    }

    public void setCustom(Customer custom) {
        this.custom = custom;
    }

    public Date getReadTime() {
        return readTime;
    }

    public void setReadTime(Date readTime) {
        this.readTime = readTime;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }
}
