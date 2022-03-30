package com.sharing;

import com.sharing.Utils.IllegalWordDisposeUtil;
import com.sharing.config.MyEmailSenderConfig;
import com.sharing.mapper.UserMapper;
import com.sharing.service.ResourceDetailService;
import com.sharing.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
//@MapperScan("com.sharing.mapper")
class CampusresourcemanagerApplicationTests {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyEmailSenderConfig sender;

    @Autowired
    private ResourceDetailService resourceDetailService;

    @Test
    void contextLoads() throws Exception {

        Integer integer = Integer.valueOf(" ");
        System.out.println(integer);

    }

}
