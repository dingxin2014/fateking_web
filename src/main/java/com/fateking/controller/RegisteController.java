package com.fateking.controller;

import com.fateking.base.FatekingBaseController;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dingxin on 17/1/18.
 */
@RequestMapping("registe")
public class RegisteController extends FatekingBaseController{

    @RequestMapping(value = "/signUp")
    public Json signUp(HttpServletRequest request){
        return ok();
    }

}
