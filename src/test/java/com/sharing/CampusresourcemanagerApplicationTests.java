package com.sharing;

import com.sharing.Utils.IllegalWordDisposeUtil;
import com.sharing.config.MyEmailSenderConfig;
import com.sharing.mapper.UserMapper;
import com.sharing.pojo.Focus;
import com.sharing.pojo.UserAndResource;
import com.sharing.service.FocusService;
import com.sharing.service.ResourceDetailService;
import com.sharing.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
//@MapperScan("com.sharing.mapper")
class CampusresourcemanagerApplicationTests {

    @Autowired
    private FocusService focusService;

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
//
//        List<UserAndResource> list = this.resourceDetailService.getFocusUserResourceByUserId(1);
//        for (UserAndResource item : list) {
//            System.out.println("++++++++++++++++++++++++++++++++++++++++++++++");
//            System.out.println(item.getUserInfo());
//            System.out.println(item.getResource());
//
//        }


    }

}
