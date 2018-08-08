package tech.greatinfo.sellplus.domain.article;

import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import tech.greatinfo.sellplus.domain.Customer;

/**
 *
 * 阅读记录
 *
 * Created by Ericwyn on 18-8-8.
 */
public class ReadHistory {
    private static final long serialVersionUID = -1L;
    @Id
    @GeneratedValue
    @PrimaryKeyJoinColumn
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "custom_id",columnDefinition = "BIGINT COMMENT '阅读者'")
    private Customer mCustomer;

}
