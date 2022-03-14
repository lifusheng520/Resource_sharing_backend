package com.sharing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.Random;

/**
 * 邮件发送器
 *
 * @author 李福生
 * @date 2022-3-11
 * @time 下午 01:12
 */
@Component
public class MyEmailSenderConfig {
    /**
     * 邮箱验证的有效时间（单位秒s）
     */
    public static final int VERIFY_VALID_TIME = 60 * 5;
    /**
     * java邮件发送器
     */
    @Autowired
    private JavaMailSender sender;
    /**
     * 邮件发送人
     */
    @Value("${spring.mail.username}")
    private String username;
    /**
     * 邮件实体类
     */
    private SimpleMailMessage email = new SimpleMailMessage();

    /**
     * 产生一个bit位数的随机验证码
     *
     * @param bit 随机验证码的位数，且位数不能小于或等于 0
     * @return 一个bit位的随机验证码
     */
    public static String generateVerifyCode(int bit) {
        if (bit <= 0)
            return null;

        double seed = new Random().nextDouble();
        String value = String.valueOf(seed);
        return value.substring(2, 2 + bit);
    }

    /**
     * 发送邮件给用户
     *
     * @param to      用户邮箱地址
     * @param title   邮箱标题
     * @param message 邮箱内容消息
     */
    public void sendEmail(String to, String title, String message) throws MailSendException {
        this.email.setFrom(this.username);
        this.email.setTo(to);
        this.email.setSubject(title);
        this.email.setText(message);
        this.sender.send(this.email);
    }

    /**
     * 发送邮箱验证码，重载发送邮件方法
     *
     * @param to               收件人邮箱号
     * @param title            邮箱标题
     * @param message          邮箱内容消息
     * @param verificationCode 验证码
     * @return 邮箱发送是否成功，成功:true，否则:false
     */
    public boolean sendEmail(String to, String title, String message, String verificationCode) {
        String emailTitle = "白给网 | " + title;
        StringBuffer content = new StringBuffer();
        content
                .append("您正在")
                .append(message)
                .append("，本次验证码做身份验证使用，切勿泄露给他人~~~")
                .append("\n")
                .append("您的验证码是:【")
                .append(verificationCode)
                .append("】，此验证码")
                .append(MyEmailSenderConfig.VERIFY_VALID_TIME / 60)
                .append("钟有效，请尽快使用！");

        // 捕获用户邮箱号接收的异常，550：用户邮箱开启了邮箱保护，发送失败
        try {
            this.sendEmail(to, emailTitle, content.toString());
        } catch (MailSendException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
