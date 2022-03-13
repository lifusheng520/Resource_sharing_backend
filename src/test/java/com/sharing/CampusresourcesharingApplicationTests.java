package com.sharing;

import com.nimbusds.jose.crypto.RSASSAVerifier;
import com.sharing.Utils.MyTokenUtil;
import com.sharing.config.MyEmailSenderConfig;
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
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.Random;

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
//        int id = 1;
//        String username = "admin";
//        String token = MyTokenUtil.build(1, username);
//        System.out.println(token);

//        String s = "eyJhbGciOiJSUzI1NiJ9.eyJpc3MiOiJiYWlnZWkuY29tLmxmcyIsInN1YiI6ImxmcyIsImlkIjoxLCJleHAiOjE2NDY0NzYyMjEsInVzZXJuYW1lIjoiYWRtaW4ifQ.Z6N21SysKpdGWHUCNMuWCwGAmcuWvjR4uUi7OpC7eBt1xsExFJzAQUN5lXbsrhWXWo3mOrVZlVekuR4Q0XFn0XT316W9GDlQQXEWdOfC6Uo7C08tbHi6IuTRIc1UNtmzexoJOPA_UIxg3jBidh4O18FGFR5neoLBhOr3DhDjf1rUhHHWoESSkjx0VouVZxvxzX5wY7lqeGvwYb9Y9Fbmp42uimjj1SqlhSe0NCcnBkpZe3XMCfUKAzqKULsyMGswsdoAURIcnTS139nD_BBkbEMATeiFpnvS2vWHe-g9kg073EInhnFboXfT7hEHm1DY_gZlOF48SCZCLz3MAWDAsg";
//        Map<String, String> map = MyTokenUtil.parse(s);
//        System.out.println(map);
//
//        String encode = new BCryptPasswordEncoder().encode("123456");
////        System.out.println(encode);
//
//        User user = this.service.getUserByName("assfad");
//        System.out.println(user == null);

//        User user = new User();
//        user.setUsername("username");
//        user.setPassword(encode);
//        user.setEnabled(1);
//        int i = this.service.register(user);
//        System.out.println(i);

//        org.springframework.mail.MailSendException

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        System.out.println(authentication);

//        Random random = new Random();
//        double value = random.nextDouble();
//        int vc = (int) (value * 1000000);

//        Date date = new Date();
//        int i = this.userMapper.insertTime(date);
//
//        System.out.println(i);

        UserInfo admin = this.service.getUserInfo("asd");
        System.out.println(admin);


    }

}
