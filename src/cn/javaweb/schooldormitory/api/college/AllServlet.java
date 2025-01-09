package cn.javaweb.schooldormitory.api.college;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.schooldormitory.model.CollegeModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/college/all")
public class AllServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取配置对象
        Config appConfig = (Config) req.getAttribute("AppConfig");
        CollegeModel collegeModel = new CollegeModel(appConfig);
        success("查询用户信息", collegeModel.getCollegeAll());
    }
}