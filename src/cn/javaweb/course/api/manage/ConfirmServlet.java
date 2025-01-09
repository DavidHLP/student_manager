package cn.javaweb.course.api.manage;

import cn.javaweb.course.entity.StudentConfirm;
import cn.javaweb.course.model.EnrollmentModel;
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

@WebServlet("/api/course/confirm")
public class ConfirmServlet extends OpenApi {
    protected void doPost(
        HttpServletRequest req,
        HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        EnrollmentModel enrollmentModel = new EnrollmentModel(appConfig);
        StudentConfirm studentConfirm = JSON.parseObject(Util.getJsonParam(req), StudentConfirm.class);

        boolean r = false;
        try {
            r = enrollmentModel.confirm(studentConfirm.getStudentId(), studentConfirm.getCourseId(), studentConfirm.getAction());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        if(r){
            success("操作成功",null);
        }
        error("操作失败");
    }

}
