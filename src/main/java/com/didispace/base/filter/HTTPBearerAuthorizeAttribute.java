package com.didispace.base.filter;

import com.didispace.base.jwt.Audience;
import config.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ChenYan on 2017-07-03.
 */
public class HTTPBearerAuthorizeAttribute implements Filter {
    @Autowired
    @Qualifier("audience")
    private Audience audience;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        SpringBeanAutowiringSupport.processInjectionBasedOnServletContext(this,
                filterConfig.getServletContext());
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ResponseResult resultMsg;

        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        //这里填写你允许进行跨域的主机ip
        httpResponse.setHeader("Access-Control-Allow-Origin", "*");
        //允许的访问方法
        httpResponse.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE, PATCH");
        //Access-Control-Max-Age 用于 CORS 相关配置的缓存
        httpResponse.setHeader("Access-Control-Max-Age", "3600");
        httpResponse.setHeader("Access-Control-Allow-Headers", "Content-Type, Accept, X-Requested-With, remember-me, authorization, x-auth-token");
        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
        String auth = httpRequest.getHeader("Authorization");
        chain.doFilter(request, response);
        //TODO 此处是为了测试方便添加的~  后期必须删除！！！！
        if (auth != null && "lp".equals(auth)){
            chain.doFilter(request, response);
            return;
        }
//        if ((auth != null) && (auth.length() > 7))
//        {
//            String HeadStr = auth.substring(0, 6).toLowerCase();
//            if (HeadStr.compareTo("bearer") == 0)
//            {
//                auth = auth.substring(7, auth.length());
//                if (JwtHelper.parseJWT(auth, audience.getBase64Secret()) != null)
//                {
//                    chain.doFilter(request, response);
//                    return;
//                }
//            }
//        }
//
//        httpResponse.setCharacterEncoding("UTF-8");
//        httpResponse.setContentType("application/json; charset=utf-8");
//        httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//
//        ObjectMapper mapper = new ObjectMapper();
//        if("OPTIONS".equalsIgnoreCase(httpRequest.getMethod())) {
//            httpResponse.setStatus(HttpServletResponse.SC_OK);
//        }
//        resultMsg = new BaseResult(ErrCode.INVALID_TOKEN);
//        httpResponse.getWriter().write(mapper.writeValueAsString(resultMsg));
        return;
    }

    @Override
    public void destroy() {

    }
}
