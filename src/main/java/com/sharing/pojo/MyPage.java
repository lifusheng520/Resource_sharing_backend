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
}
