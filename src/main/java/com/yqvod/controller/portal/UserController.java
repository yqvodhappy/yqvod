package com.yqvod.controller.portal;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * @Author: dtvikey
 * @Date: 15/10/19 上午 10:52
 * @Version 1.0
 */
@Controller
@RequestMapping("/user/")
public class UserController {
    /**
     * 用户登录
     * @param username
     * @param password
     * @param session
     * @return
     */
    @RequestMapping(value = "login.do",method = RequestMethod.POST)
    @ResponseBody
    public Object login(String username, String password, HttpSession session){
        return null;
    }


}
