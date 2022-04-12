package com.sharing.config;

import com.sharing.service.FavouriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

@Configuration
@EnableScheduling
public class SystemScheduleConfig {

    @Value("${deleted.resource.record.timeout}")
    private int timeOutDays;

    @Autowired
    private FavouriteService favouriteService;

    //cron(秒 分 时 日 月 周 年) *:所有值
    //    从properties配置文件中读取设置
//    @Scheduled(cron = "${deleted.resource.clear.timer.setting}")
    public void clearDeletedResource() {
        int i = this.favouriteService.clearOvertimeDeletedRecord(this.timeOutDays);
        System.out.println(i);

    }

}
