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

    private String couponsId;

    //@Column(columnDefinition = "DOUBLE COMMENT '概率'")
    private String prob;    //获奖概率

    //@ManyToOne(fetch = FetchType.EAGER)
    //@JoinColumn(name = "coupon_id", columnDefinition = "BIGINT COMMENT '卷模板外键'")

    public Lottery(String name, String couponsId, String prob) {
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

    public String getCouponsId() {
        return couponsId;
    }

    public void setCouponsId(String couponsId) {
        this.couponsId = couponsId;
    }

    public String getProb() {
        return prob;
    }

    public void setProb(String prob) {
        this.prob = prob;
    }

    @Override
    public String toString() {
        return "Lottery{" +
                "name='" + name + '\'' +
                ", couponsId='" + couponsId + '\'' +
                ", prob='" + prob + '\'' +
                '}';
    }
}
