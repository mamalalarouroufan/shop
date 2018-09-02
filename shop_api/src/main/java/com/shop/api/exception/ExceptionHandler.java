package com.shop.api.exception;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.shop.api.base.entity.WebEntity;

public class ExceptionHandler implements HandlerExceptionResolver {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandler.class);

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
                                         Exception ex) {
        LOGGER.error(ex.getMessage(), ex);
        PrintWriter writer = null;
        try {
            response.setContentType("application/json;charset=UTF-8");
            writer = response.getWriter();
            if (ex instanceof BusinessException) {
                if (((BusinessException) ex).getKey() != null) {
                    Map<String, Object> map = new HashMap<>();
                    map.put("errcode", ((BusinessException) ex).getCode());
                    map.put("errorMsg", ex.getMessage());
                    map.put(((BusinessException) ex).getKey(), ((BusinessException) ex).getValue());
                    writer.println(JSON.toJSON(map));
                } else {
                	WebEntity<?> rs = new WebEntity<>((BusinessException)ex);
                    writer.println(JSON.toJSON(rs));
                }
            } else if (ex instanceof BaseException) {
                WebEntity<?> wt = new WebEntity<>((BaseException) ex);
                writer.println(JSON.toJSON(wt));

            } else {
                ByteArrayOutputStream buf = new java.io.ByteArrayOutputStream();
                ex.printStackTrace(new java.io.PrintWriter(buf, true));
                buf.close();
                WebEntity<?> wt = new WebEntity<>(EnumError.UNKNOWN_ERROR);
                writer.println(JSON.toJSON(wt));              
            }
            writer.flush();

        } catch (Exception e) {
            LOGGER.error("ExceptionHandler:{}", e);
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
        return null;
    }
}
