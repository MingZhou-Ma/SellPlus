package tech.greatinfo.sellplus.domain;

/**
 * 描述：
 *
 * @author Badguy
 */
//@Entity
//@Table(name = "lottery")
public class Lottery {

//    @Id
//    @GeneratedValue
//    @PrimaryKeyJoinColumn
    //private Long id;         //奖品Id

    //@Column(columnDefinition = "VARCHAR(255) COMMENT '奖品名称'")
    private String name;    //奖品名称

    private Long couponsId;

    //@Column(columnDefinition = "DOUBLE COMMENT '概率'")
    private Double prob;    //获奖概率

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "coupon_id", columnDefinition = "BIGINT COMMENT '卷模板外键'")


    public Lottery(Double prob) {
        this.prob = prob;
    }

    public Lottery(String name, Long couponsId, Double prob) {
        this.name = name;
        this.couponsId = couponsId;
        this.prob = prob;
    }

    /*public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(Long couponsId) {
        this.couponsId = couponsId;
    }

    public Double getProb() {
        return prob;
    }

    public void setProb(Double prob) {
        this.prob = prob;
    }


}
