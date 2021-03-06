package com.hyb.rbac.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hyb.rbac.common.util.JwtUtil;
import com.hyb.rbac.repo.AppAccessLog;
import com.hyb.rbac.repo.Result;
import com.hyb.rbac.service.AppAccessLogServiceImpl;
import net.minidev.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.PrintWriter;
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
            Result result=new Result();
            String token = request.getHeader("Authorization");
            if(token==null){

                result.setErrorInfo("未获取到token");
                returnJson(servletResponse,result);
                return;
            }
            try {
                Map<String, Object> map = JwtUtil.valid(token);
                Integer status = (Integer) map.get("Result");
                switch (status) {
                    case 0:
                        result.setErrorInfo("无效的token");
                        returnJson(servletResponse,result);
                        break;
                    case 2:
                        result.setErrorInfo("过期的token");
                        returnJson(servletResponse,result);
                        break;
                    default:
                        JSONObject data = (JSONObject) map.get("data");
                        accessLog.setUid(Long.parseLong(data.getAsString("uid")));
                        filterChain.doFilter(servletRequest, servletResponse);
                }
            } catch (Exception e) {
                result.setErrorInfo("token验证过程出错");
                result.setException(e);
                returnJson(servletResponse,result);
            }
        }
        accessLogService.addAppAccessLog(accessLog);
    }

    /**
     * 将错误信息返回前端
     * @param response
     * @param result
     */
    private void returnJson(ServletResponse response, Result result){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try (PrintWriter writer = response.getWriter()){
            ObjectMapper mapper = new ObjectMapper();
            writer.print(mapper.writeValueAsString(result));
        } catch (IOException e) {
            log.error("response error",e);
        }
    }

}
