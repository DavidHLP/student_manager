package cn.javaweb.base.api.menu;

import cn.javaweb.base.model.MenuModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/menu/list")
public class TreeListServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        MenuModel menuModel = new MenuModel(appConfig);
        success("查询成功", menuModel.getAll());
    }
}
