package tech.greatinfo.sellplus.domain;

import javax.persistence.*;

/**
 * 海报中心
 */
@Entity
@Table(name = "LotteryShare")
public class LotteryShare {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "origin", columnDefinition = "BIGINT COMMENT '分享人'")
    private Customer origin;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "own", columnDefinition = "BIGINT COMMENT '领取人'")
    private Customer own;


    public LotteryShare() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Customer getOrigin() {
        return origin;
    }

    public void setOrigin(Customer origin) {
        this.origin = origin;
    }

    public Customer getOwn() {
        return own;
    }

    public void setOwn(Customer own) {
        this.own = own;
    }
}
