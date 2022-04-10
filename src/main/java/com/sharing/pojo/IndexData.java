package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * 首页数据存储实体类，所有用户打开页面都访问本类中的数据
 * 使用缓存， 需要被二级缓存的对象必须要实现java的序列化接口
 *
 * @author 李福生
 * @date 2022-3-21
 * @time 上午 10:52
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class IndexData implements Serializable {

    /**
     * 学科类别
     */
    public static final String[] discipline;

    static {
        discipline = new String[]{"全部", "法学", "工学", "管理学", "计算机科学", "教育学", "经济学", "军事学", "理学", "历史学", "农学", "文学", "医学", "艺术学", "哲学"};
    }

    private int userNumber;
    private int resourceNumber;
    private int downloadTimes;

    private List<UserAndResource> resources;

}
