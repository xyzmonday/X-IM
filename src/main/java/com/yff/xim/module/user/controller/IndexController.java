package com.yff.xim.module.user.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 首页web接入层
 */
@Controller
public class IndexController {

    /**
     * 用户访问/index / "" 进入index.html页面
     * @return
     */
    @GetMapping(value = {"","/","/index"})
    public String index() {
        return "index";
    }
}
