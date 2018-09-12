package tech.greatinfo.sellplus.domain.help;

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
 * 好友助力表
 *
 * Created by Ericwyn on 18-7-23.
 */
@Entity
@Table(name = "helpHistory")
public class HelpHistory {
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    //    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "help_id",columnDefinition = "BIGINT COMMENT '用户的助力活动'")
    private Help help;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id",columnDefinition="BIGINT COMMENT '用户外键'")
    private Customer customer;

    public HelpHistory() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Help getHelp() {
        return help;
    }

    public void setHelp(Help help) {
        this.help = help;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
