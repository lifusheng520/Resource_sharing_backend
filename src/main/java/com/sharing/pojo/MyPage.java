package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


/**
 * 自己编写的内容分页包装类
 *
 * @param <T> 需要分页的对象类型 T
 * @author 李福生
 * @date 2022-3-18
 * @time 上午 11:14
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MyPage<T> {
    private int total;
    private int currentPage;
    private int pageSize;
    private List<T> pageList;

    /**
     * 获取页面数据中begin到end间的内容
     *
     * @param begin 开始位置（1~list.length）
     * @param end   结束位置（1~list.length）
     * @return 返回begin~end间的lsit数据，如果位置错误则返回null
     */
    public List<T> slice(int begin, int end) {
        if (this.pageList == null)
            return null;
        int size = this.pageList.size();
        if (begin < 1 || end > size)
            return null;
        return this.pageList.subList(begin, end);
    }

    /**
     * 通过字符串获取合法的页码
     *
     * @param number 页码字符串
     * @param type   需要转换的类型（page、size）
     * @return 如果type为page：字符串正常将该字符串转换为int返回，否则返回 1。如果type为size，返回正常的页面大小，默认返回10
     */
    public static int getValidTransfer(String number, String type) {
        Integer value;
        if ("page".equals(type)) {
            if (number == null || "".equals(number))
                value =  1;
            value = Integer.valueOf(number);
            if (value < 1)
                value =  1;
        } else {
            if (number == null || "".equals(number))
                value =  10;
            value = Integer.valueOf(number);
            if (value < 1)
                value =  10;
        }
        return value;
    }
}
