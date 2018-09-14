package tech.greatinfo.sellplus.domain.article;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

/**
 *
 * 文章表
 *
 * Created by Ericwyn on 18-8-8.
 */
@Entity
@Table(name = "article")
public class Article {

    private static final long serialVersionUID = -1L;
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @Column(columnDefinition = "VARCHAR(50) COMMENT '文章标题'")
    private String title;

    @Column(columnDefinition = "TEXT COMMENT '文章内容'")
    private String content;

    @Column(columnDefinition = "VARCHAR(50) COMMENT '文章作者'")
    private String author;

    @Column(columnDefinition = "TIMESTAMP COMMENT '创建时间'")
    private Date createDate;

    @Column(columnDefinition = "TEXT COMMENT '文章简介'")
    private String intro;

    @Column(columnDefinition = "VARCHAR(255) COMMENT '提图'")
    private String pic;

    public Article() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
