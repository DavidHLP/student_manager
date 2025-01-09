package cn.javaweb.base.api.dept;

import cn.javaweb.base.model.DeptModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.schooldormitory.model.CollegeModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/dept/all")
public class AllDeptServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取配置对象
        Config appConfig = (Config) req.getAttribute("AppConfig");
        DeptModel deptModel = new DeptModel(appConfig);

        success("查询部门信息", deptModel.getCollegeAll());
    }
}
