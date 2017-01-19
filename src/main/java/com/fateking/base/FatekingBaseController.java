package com.fateking.base;

import com.alibaba.fastjson.JSONObject;
import com.fateking.config.Configuration;
import com.fateking.exception.FatekingException;
import com.fateking.model.User;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * Created by dingxin on 16/12/12.
 */
public abstract class FatekingBaseController implements Global{

    private static Log logger = LogFactory.getLog(FatekingBaseController.class);

    protected Configuration config = Configuration.getInstance();

    protected Json ok() {
        return ok("");
    }

    protected Json ok(String msg) {
        return ok(msg, "");
    }

    protected Json ok(String msg, String detail) {
        return new Json("ok", msg, detail);
    }

    protected Json fail() {
        return fail("");
    }

    protected Json fail(String msg) {
        return fail(msg, "");
    }

    protected Json fail(String msg, String detail) {
        return new Json("fail", msg, detail);
    }

    public User getUser() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getRequest();
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(Constants.SESSION_USER);
        return user;
    }

    /**
     * <p>
     * FastJson在复杂类型的Bean转换Json上会出现一些问题，可能会出现引用的类型，导致Json转换出错，需要制定引用。
     * FastJson采用独创的算法，将parse的速度提升到极致，超过所有json库。
     * </p>
     * @author dingxin
     *
     */
    public static class Json extends JSONObject implements java.io.Serializable {

        /**
         *
         */
        private static final long serialVersionUID = 1L;

        public Json(String status) {
            super.put("status", status);
            super.put("message", "");
            super.put("detail", "");
        }

        public Json(String status, String message) {
            super.put("status", status);
            super.put("message", message);
            super.put("detail", "");
        }

        public Json(String status, String message, Object detail) {
            super.put("status", status);
            super.put("message", message);
            super.put("detail", detail);
        }

        public Json put(String key, Object obj) {
            super.put(key, obj);
            return this;
        }

        public Json remove(String key) {
            super.remove(key);
            return this;
        }

        public Json putall(Map<? extends String, ? extends Object> m){
            super.putAll(m);
            return this;
        }
    }


    /**
     * 拦截参数异常，返回Json
     *
     * @param ex 错误
     * @return 返回失败json
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseBody
    public Json illegalArgumentException(IllegalArgumentException ex) {
        logger.error("IllegalArgumentException => "+ex.getMessage(), ex);
        return fail(ex.getMessage());
    }

    @ExceptionHandler(FatekingException.class)
    @ResponseBody
    public Json fatekingException(FatekingException ex){
        logger.error("fatekingException => "+ex.getMessage(), ex);
        return fail(ex.getMessage());
    }

    /**
     * 拦截参数异常，返回Json
     *
     * @param ex 错误
     * @return 返回失败json
     */
    @ExceptionHandler(RuntimeException.class)
    @ResponseBody
    public Json runtimeException(RuntimeException ex) {
        logger.error("RuntimeException => "+ex.getMessage(), ex);
        return fail(ex.getMessage());
    }

    /**
     * 拦截数据库操作异常，返回Json
     * @param ex 错误
     * @return 返回失败json
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseBody
    public Json dataAccessException(DataAccessException ex) {
        logger.error("DataAccessException => "+ex.getMessage(), ex);
        return fail(ex.getMessage());
    }

    /**
     * 拦截空指针异常，返回Json
     * @param ex 错误
     * @return 返回失败json
     */
    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Json nullPointerException(NullPointerException ex) {
        logger.error("NullPointerException => " + ex.getMessage(), ex);
        return fail(ex.getMessage());
    }

    /**
     * 设置当前系统环境到MODEL
     * @return
     */
    @ModelAttribute(value="config")
    public Configuration instance(){
        return Configuration.getInstance();
    }


}
