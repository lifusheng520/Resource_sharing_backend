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
class CampusresourcesharingApplicationTests {

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
        String content = "我是傻逼,曹尼玛，你妈必死，sdfa弱智，cnm，日你妈妈,你是不是傻";

        String dealString = IllegalWordDisposeUtil.hideIllegalWords(content, '*');
        System.out.println(content);
        System.out.println(dealString);

    }

}
