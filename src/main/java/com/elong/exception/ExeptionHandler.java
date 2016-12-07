package com.elong.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice  
public class ExeptionHandler {  
  
    @ExceptionHandler(value = Exception.class)  
    public ModelAndView defaultErrorHandler(HttpServletRequest req, Exception e) {  
        ModelAndView mv = new ModelAndView();  
        mv.addObject("e", e);  
        mv.addObject("uri", req.getRequestURI());  
        return mv;  
    }  
}  