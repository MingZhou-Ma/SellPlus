package tech.greatinfo.sellplus.domain.group;

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
 * Created by Ericwyn on 18-7-23.
 */
@Entity
@Table(name = "joinGroup")
public class JoinGroup {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "group_id",columnDefinition="BIGINT COMMENT '拼团外键'")
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",columnDefinition="BIGINT COMMENT '用户外键'")
    private Customer customer;

    public JoinGroup() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
