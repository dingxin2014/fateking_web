package com.fateking.controller;

import com.fateking.base.FatekingBaseController;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by dingxin on 16/12/12.
 */
@Controller
@RequestMapping(value = "/")
public class FatekingController extends FatekingBaseController{


    private static final Log logger = LogFactory.getLog(FatekingController.class);

    /**
     * 测试
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = { "test"}, method = { RequestMethod.GET })
    public Json index(HttpServletRequest request) {
        logger.debug("TestController:/test");
        return ok("It's OK now!");
    }


}
