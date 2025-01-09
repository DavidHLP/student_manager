package cn.javaweb.schooldormitory.api.Bed;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.schooldormitory.service.BedService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/bed/all")
public class NoUseAllServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取配置对象
        Config appConfig = (Config) req.getAttribute("AppConfig");
        BedService bedService = new BedService(appConfig);
        try {
            success("查询用户信息", bedService.getNoUseAll());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
