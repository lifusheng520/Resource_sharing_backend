package com.sharing;

import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.sharing.Utils.MyTokenUtil;
import com.sharing.Utils.ResponseCode;
import com.sharing.Utils.ResultFormatUtil;
import com.sharing.config.MyEmailSenderConfig;
import com.sharing.config.SystemScheduleConfig;
import com.sharing.mapper.UserMapper;
import com.sharing.pojo.User;
import com.sharing.pojo.UserInfo;
import com.sharing.service.UserService;
import com.sharing.serviceImpl.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

@SpringBootTest
//@MapperScan("com.sharing.mapper")
class CampusresourcesharingApplicationTests {

    @Autowired
    private UserService service;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private MyEmailSenderConfig sender;

    @Test
    void contextLoads() throws Exception {
        List<Integer> list = new ArrayList<>();
        for(int i = 1; i <= 10; i++){
            list.add(i);
        }

        System.out.println(list.subList(1,10));
        System.out.println(list.size());



    }

}
