package com.poethan.hearthstoneclassic.api;

import com.poethan.hearthstoneclassic.domain.YuqiOrderDO;
import com.poethan.jear.jdbc.JdbcDAO;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/user")
@RestController
public class UserApi {
    @Resource
    private JdbcDAO jdbcDAO;

    @GetMapping("/byid")
    public String getUser(Long id) {
        return jdbcDAO.findById(YuqiOrderDO.class, id).toString();
    }
}
