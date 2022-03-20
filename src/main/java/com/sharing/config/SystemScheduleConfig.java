package com.sharing.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 系统定时任务配置
 *
 * @author 李福生
 * @date 2022-3-20
 * @time 上午 11:17
 */
//
//@Configuration
//@EnableScheduling
public class SystemScheduleConfig {

    //cron(秒 分 时 日 月 周 年) *:所有值
//    @Scheduled(cron = "0 0 3 * * ?")
//    @Scheduled(cron = "* * * * * ?")
//    public void test(){
//        System.out.println("每秒一次：" + System.currentTimeMillis());
//    }

}
