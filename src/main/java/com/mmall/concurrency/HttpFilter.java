package com.mmall.concurrency;


import com.mmall.concurrency.example.threadLocal.RequestHodler;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class HttpFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
//        request.getSession().getAttribute("user");
        RequestHodler.add(Thread.currentThread().getId());
        log.info("do filter, {}, {}", Thread.currentThread().getId(), request.getServletPath());
        filterChain.doFilter(servletRequest, servletResponse); //让请求继续
    }

    @Override
    public void destroy() {

    }
}
