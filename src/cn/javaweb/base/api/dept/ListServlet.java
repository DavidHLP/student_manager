package cn.javaweb.base.api.dept;

import cn.javaweb.base.entity.PageInfo;
import cn.javaweb.base.model.DeptModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/department/list")
public class ListServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        DeptModel deptModel = new DeptModel(appConfig);
        success("查询成功", deptModel.getAll());
    }
}
