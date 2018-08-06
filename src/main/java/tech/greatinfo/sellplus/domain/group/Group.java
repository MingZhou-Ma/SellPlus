package tech.greatinfo.sellplus.domain.group;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import tech.greatinfo.sellplus.domain.Activity;
import tech.greatinfo.sellplus.domain.Customer;

/**
 *
 * 开图表  顾客
 *
 * Created by Ericwyn on 18-7-23.
 */
@Entity
@Table(name = "groupAct")
public class Group {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "activity_id",columnDefinition = "BIGINT COMMENT '活动外键'")
    private Activity activity;

//    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id",columnDefinition = "BIGINT COMMENT '开团人外键'")
    private Customer customer;

    public Group() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
