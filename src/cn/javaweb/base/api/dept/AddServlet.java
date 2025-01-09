package cn.javaweb.base.api.dept;

import cn.javaweb.base.entity.Department;
import cn.javaweb.base.model.DeptModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.library.Util;
import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/department/add")
public class AddServlet extends OpenApi {    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        DeptModel deptModel = new DeptModel(appConfig);

        Department department = JSON.parseObject(Util.getJsonParam(req), Department.class);
        if(department == null){
            error("数据格式不正确");
        }
    try {
        success("操作成功", deptModel.add(department));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
}
