package com.sharing.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
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
}
