package com.hyb.rbac.filter;

import com.hyb.rbac.common.util.JwtUtil;
import com.hyb.rbac.repo.AppAccessLog;
import com.hyb.rbac.service.AppAccessLogServiceImpl;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.*;

@WebFilter(filterName = "tokenFilter",
        //initParams = {@WebInitParam(name="exclusions",value = "/log")},
        urlPatterns = "/*")
@Order(2)
public class TokenFilter implements Filter {

    @Autowired
    private AppAccessLogServiceImpl accessLogService;

    private static final Logger log = LoggerFactory.getLogger(TokenFilter.class);

    private static final Set<String> ALLOWED_PATHS = Collections.unmodifiableSet(new HashSet<>(
            Arrays.asList("/log")));

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        AppAccessLog accessLog = new AppAccessLog();
        accessLog.setIp(request.getRemoteAddr());
        accessLog.setTargetUrl(request.getRequestURI());
        accessLog.setQueryParams(request.getQueryString());

        StringBuilder headerInfo=new StringBuilder();
        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()){
            String element = headerNames.nextElement();
            headerInfo.append(String.format("\"%s\":\"%s\",",element,request.getHeader(element)));
        }
        accessLog.setHeader(headerInfo.substring(0,headerInfo.length()-1));

//        BufferedReader br = request.getReader();
//        String str, body = "";
//        while((str = br.readLine()) != null){
//            body += str;
//        }
//        accessLog.setData(body);


        if (ALLOWED_PATHS.contains(accessLog.getTargetUrl())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String token = request.getHeader("Authorization");
            try {
                Map<String, Object> map = JwtUtil.valid(token);
                Integer result = (Integer) map.get("Result");
                switch (result) {
                    case 0:
                        request.getRequestDispatcher("/error/tokenError.html").forward(request, response);
                        break;
                    case 2:
                        request.getRequestDispatcher("/error/tokenError.html").forward(request, response);
                        break;
                    default:
                        JSONObject data = (JSONObject) map.get("data");
                        accessLog.setUid(Long.parseLong(data.getAsString("uid")));
                        filterChain.doFilter(servletRequest, servletResponse);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        accessLogService.addAppAccessLog(accessLog);
    }

}
