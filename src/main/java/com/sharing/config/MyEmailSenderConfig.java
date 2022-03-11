package com.sharing.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSendException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

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
     * 发送邮件给用户
     *
     * @param to      用户邮箱号
     * @param title   邮箱标题
     * @param message 邮箱内容消息
     * @return 邮箱发送是否成功，成功:true，否则:false
     */
    public boolean sendEmail(String to, String title, String message) {
        this.email.setFrom(this.username);
        this.email.setTo(to);
        this.email.setSubject(title);
        this.email.setText(message);

        // 捕获用户邮箱号接收的异常，550：用户邮箱开启了邮箱保护，发送失败
        try {
            this.sender.send(this.email);
        } catch (MailSendException e) {
            e.printStackTrace();
            return false;
        }
        return true;
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
    public boolean sendEmail(String to, String title, String message, int verificationCode) {
        this.email.setFrom(this.username);
        this.email.setTo(to);
        this.email.setSubject("白给网 | " + title);
        StringBuffer content = new StringBuffer();
        content.append(message).append("\n")
                .append("您的验证码是:【").append(verificationCode)
                .append("】，此验证码三分钟有效，请尽快使用！");
        this.email.setText(content.toString());

        // 捕获用户邮箱号接收的异常，550：用户邮箱开启了邮箱保护，发送失败
        try {
            this.sender.send(this.email);
        } catch (MailSendException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
