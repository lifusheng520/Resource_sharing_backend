package com.sharing.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 资源收藏业务controller
 *
 * @author 李福生
 * @date 2022-4-3
 * @time 下午 08:15
 */

@RestController
@RequestMapping("/favourite")
public class ResourceFavouriteController {

    @PostMapping("/add")
    public String addFavourite(@RequestBody Map<String, String> params) {
        params.get("");

        return "";
    }

}
