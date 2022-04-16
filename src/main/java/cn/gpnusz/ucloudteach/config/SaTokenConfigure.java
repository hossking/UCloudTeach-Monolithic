package cn.gpnusz.ucloudteach.config;

import cn.dev33.satoken.interceptor.SaAnnotationInterceptor;
import cn.dev33.satoken.interceptor.SaRouteInterceptor;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author h0ss
 * @description sa-token鉴权拦截器注册
 * @date 2021/11/22 - 19:43
 */
@Configuration
public class SaTokenConfigure implements WebMvcConfigurer {
    /**
     * 注册Sa-Token的注解拦截器，打开注解式鉴权功能
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 注册Sa-Token的路由拦截器
        registry.addInterceptor(new SaRouteInterceptor((req, res, handler) -> {
            // 登录认证 -- 拦截所有路由，并排除登陆注册接口以及公共接口
            SaRouter.match("/**")
                    .notMatch("/api/user/auth/**")
                    .notMatch("/api/admin/login")
                    .notMatch("/api/common/**")
                    .notMatch("/favicon.ico")
                    .notMatchMethod(RequestMethod.OPTIONS.name())
                    .check(r -> StpUtil.checkLogin());
            // 角色认证 -- 不同角色认证不同权限
            SaRouter.match("/api/user/**")
                    .notMatch("/api/user/auth/**")
                    .notMatchMethod(RequestMethod.OPTIONS.name())
                    .check(r -> StpUtil.checkRole("user"));

            SaRouter.match("/api/admin/**")
                    .notMatch("/api/admin/login")
                    .notMatch("/api/admin/checkLogin")
                    .notMatchMethod(RequestMethod.OPTIONS.name())
                    .check(r -> StpUtil.checkRole("admin"));

        })).addPathPatterns("/**");

        registry.addInterceptor(new SaAnnotationInterceptor()).addPathPatterns("/**");
    }
}
