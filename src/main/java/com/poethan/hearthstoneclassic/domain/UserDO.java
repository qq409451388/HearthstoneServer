package com.poethan.hearthstoneclassic.domain;

import com.poethan.hearthstoneclassic.constants.UserConstants;
import com.poethan.jear.jdbc.BaseDO;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
    @Column(name = "pass_word")
    private String passWord;
    @Column(name = "email")
    private String email;
    @Column(name = "status")
    private Integer status;

    public UserDO() {
        this.status = UserConstants.STATUS_INIT;
    }
}