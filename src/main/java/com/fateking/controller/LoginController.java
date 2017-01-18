package com.fateking.controller;

import com.fateking.base.FatekingBaseController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dingxin on 17/1/18.
 */
@RequestMapping("/login")
public class LoginController extends FatekingBaseController{


    @RequestMapping("/signIn")
    public Json signIn(HttpServletRequest request){
        return ok();
    }

    @RequestMapping("/out")
    public Json loginOut(HttpServletRequest request){
        return ok();
    }
}
