package com.poethan.hearthstoneclassic.domain;

import com.poethan.jear.jdbc.AbstractDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name = "cs_yuqi_order")
public class YuqiOrderDO extends AbstractDO<Long> {
    @Column(name = "order_no")
    private String orderNo;
}
