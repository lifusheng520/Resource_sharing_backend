package com.sharing.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * 资源评论实体类
 *
 * @author 李福生
 * @date 2022-3-26
 * @time 上午 09:30
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentSupport {
    private Integer id;
    private Integer commentId;
    private Integer userId;
    private Integer amount; // 计算属性
}
