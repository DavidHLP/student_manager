/**
 * 全局过滤器，用于鉴权
 */
package cn.javaweb.library;

import cn.javaweb.base.entity.PermissionNode;
import cn.javaweb.base.entity.User;
import cn.javaweb.base.model.MenuModel;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebFilter("/*")
public class Filter implements javax.servlet.Filter {
    //全局配置
    Config appConfig = null;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        try {
            appConfig = new Config();

            String s = null;
            s = filterConfig.getServletContext().getInitParameter("DbUrl");
            appConfig.setDbUrl(s);

            s = filterConfig.getServletContext().getInitParameter("DbUser");
            appConfig.setDbUser(s);

            s = filterConfig.getServletContext().getInitParameter("DbPassword");
            appConfig.setDbPassword(s);

            s = filterConfig.getServletContext().getInitParameter("noNeedLoginList");
            appConfig.setNoNeedLoginList(s);

            s = filterConfig.getServletContext().getInitParameter("noNeedRightList");
            appConfig.setNoNeedRightList(s);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void doFilter(
        ServletRequest servletRequest,
        ServletResponse servletResponse,
        FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletResponse resp = (HttpServletResponse) servletResponse;
        HttpServletRequest  req  = (HttpServletRequest) servletRequest;

        //解决中文乱码-统一用UTF8
        req.setCharacterEncoding("utf8");
        resp.setCharacterEncoding("utf8");
        resp.setContentType("application/json;charset=utf-8");

        //允许跨域请求
        resp.setHeader("Access-Control-Allow-Headers", "*");
        //允许所有的域
        resp.setHeader("Access-Control-Allow-Origin","*");
        resp.setHeader("Access-Control-Allow-Methods", "POST, GET, OPTIONS, DELETE");

        //解决未登录访问拦截问题
        String requestURI = req.getRequestURI();
        servletRequest.setAttribute("AppConfig", appConfig);

        try{
            if( appConfig.isNoNeedLogin(requestURI) ){
                //白名接口，无需验证
                filterChain.doFilter(servletRequest, servletResponse);
            }else{
                //用户身份验证
                String token = req.getHeader("token");
                if(token==null){
                    throw new HttpException(AjaxResult.error("请登陆后操作", 401));
                }
                User user = Token.checkToken(token, appConfig);

                if(user==null){
                    throw new HttpException(AjaxResult.error("请登陆后操作", 401));
                }else{
                    if(!appConfig.isNoNeedRight(requestURI)){
                        //验证用户是否具有功能权限
                        boolean hasPermission = false;
                        MenuModel menuModel = new MenuModel(appConfig);
                        List<PermissionNode> pers = menuModel.getPermissions();
                        for(PermissionNode n: pers){
                            if( requestURI.equals("/prj"+n.getPath()) ){
                                hasPermission = true;
                                break;
                            }
                        }

                        if(hasPermission == false){
                            throw new HttpException(AjaxResult.error(requestURI+"无访问权限", 403));
                        }
                    }

                    req.setAttribute("user", user);
                    req.setAttribute("appConfig", appConfig);

                    filterChain.doFilter(servletRequest, servletResponse);
                }
            }
        }catch(HttpException e){
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().print(e.getResult());
        }catch(Exception e){
            resp.setContentType("application/json;charset=UTF-8");
            resp.getWriter().print(AjaxResult.error(e.getMessage()));
        }


    }

    @Override
    public void destroy() {

    }
}
