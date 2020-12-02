package com.junlan.shiro;

import com.junlan.common.result.ApiCode;
import com.junlan.common.result.ApiResult;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * JWT 提供token
 * 2. Shiro 对象过滤验证
 *
 * @author XJJ
 * @date 2019-09-27
 *
 **/
public class JwtFilter extends BasicHttpAuthenticationFilter {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 是否允许访问
     * 如果带有 token，则对 token 进行检查
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws UnauthorizedException {
        final String uri = WebUtils.toHttp(request).getRequestURI();
        logger.info("access url:{}", uri);
        boolean allowed = false;
        //判断请求的请求头是否带上 "Token"
        if (isLoginAttempt(request, response)) {
            //如果存在，则进入 executeLogin 方法执行登入，检查 token 是否正确
            try {
                allowed = executeLogin(request, response);
            } catch (Exception e) {
                //token 错误
                responseError(response, e.getMessage());
            }
        }
        return allowed || super.isPermissive(mappedValue);
    }

    /**
     * 判断用户是否想要登入。
     * 检测 header 里面是否包含 Token 字段
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        String token = WebUtils.toHttp(request).getHeader("token");
        return StringUtils.isNotBlank(token);
    }

    /**
     * 执行登陆操作, 注入【包装】JwtToken到AuthenticationToken
     * 或者重写executeLogin调用到方法createToken，
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response) throws Exception {
        String token = WebUtils.toHttp(request).getHeader("token");
        JwtToken jwtToken = new JwtToken(token);
        try {
            // 提交jwtToken给realm进行登入
            Subject subject = getSubject(request, response);
            subject.login(jwtToken);
            return onLoginSuccess(jwtToken, subject, request, response);
        } catch (AuthenticationException e) {
            return onLoginFailure(jwtToken, e, request, response);
        }
    }

    /**
     * 登录成功处理, executeLogin调用
     *
     * @param token
     * @param subject
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        String url = WebUtils.toHttp(request).getRequestURI();
        logger.info("Realm 登录成功,url:{},token:{}", url, token);
        // 刷新token
        JwtToken jwtToken = (JwtToken) token;
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
//        shiroLoginService.refreshToken(jwtToken, httpServletResponse);
        return true;
    }

    /**
     * 访问失败处理
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = WebUtils.toHttp(request);
        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
        // 返回401
        httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        // 设置响应码为401或者直接输出消息
        String url = httpServletRequest.getRequestURI();
        logger.error("访问失败 url：{}", url);
        ApiResult<Boolean> apiResult = ApiResult.fail(ApiCode.UNAUTHORIZED);
//        HttpServletResponseUtil.printJson(httpServletResponse, apiResult);
        return false;
    }

    /**
     * 登录失败处理， executeLogin调用
     *
     * @param token
     * @param e
     * @param request
     * @param response
     * @return
     */
    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        logger.error("登录失败，token:" + token + ",error:" + e.getMessage(), e);
        return false;
    }

    /**
     * 对跨域提供支持
     */
    @Override
    protected boolean preHandle(ServletRequest request, ServletResponse response) throws Exception {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        httpServletResponse.setHeader("Access-control-Allow-Origin", httpServletRequest.getHeader("Origin"));
        httpServletResponse.setHeader("Access-Control-Allow-Methods", "GET,POST,OPTIONS,PUT,DELETE");
        httpServletResponse.setHeader("Access-Control-Allow-Headers", httpServletRequest.getHeader("Access-Control-Request-Headers"));
        // 跨域时会首先发送一个option请求，这里我们给option请求直接返回正常状态
        if (httpServletRequest.getMethod().equals(RequestMethod.OPTIONS.name())) {
            httpServletResponse.setStatus(HttpStatus.OK.value());
            return false;
        }
        return super.preHandle(request, response);
    }

    /**
     * 将非法请求跳转到 /unauthorized/**
     */
    private void responseError(ServletResponse response, String message) {
        try {
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            //设置编码，否则中文字符在重定向时会变为空字符串
            message = URLEncoder.encode(message, "UTF-8");
            httpServletResponse.sendRedirect("/unauthorized/" + message);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }
}
