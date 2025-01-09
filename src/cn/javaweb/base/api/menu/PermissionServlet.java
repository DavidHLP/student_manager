package cn.javaweb.base.api.menu;

import cn.javaweb.base.entity.User;
import cn.javaweb.base.model.MenuModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/menu/rules")
public class PermissionServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取导航菜单
        Config appConfig = (Config) req.getAttribute("AppConfig");
        User user = (User) req.getAttribute("user");
        MenuModel menuModel = new MenuModel(appConfig);

        success("请求成功", menuModel.getPermissions());
    }
}
