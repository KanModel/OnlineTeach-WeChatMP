package me.kanmodel.july19.onlineteach.adapter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * uri格式化过滤器
 * 用于屏蔽双斜杠//导致报错
 * 记录访问情况
 *
 * @author: KanModel
 * @create: 2019-08-08 22:52
 */
@Component("uriFormatFilter")
public class UriFormatFilter extends OncePerRequestFilter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String uri = request.getRequestURI();
        Enumeration em = request.getParameterNames();
        StringBuilder para = new StringBuilder("");
        while (em.hasMoreElements()) {
            String name = (String) em.nextElement(); //参数名称
            para.append("&").append(name).append("=").append(request.getParameter(name)); //根据参数名称获取到参数值
        }
        logger.info("[" + request.getRemoteAddr() + "] [" + request.getMethod()
                + "] [" + uri + "]" + para);
        String newUri = uri.replace("//", "/");
        request = new HttpServletRequestWrapper(request) {
            @Override
            public String getRequestURI() {
                return newUri;
            }
        };

        filterChain.doFilter(request, response);
    }
}
