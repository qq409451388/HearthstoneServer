package com.poethan.hearthstoneclassic.domain;

import com.poethan.jear.jdbc.BaseDO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Table(name="users")
public class UserDO extends BaseDO<Long> {
    @Column(name = "user_name")
    private String userName;
}
