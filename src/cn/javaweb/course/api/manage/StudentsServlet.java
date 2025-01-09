package cn.javaweb.course.api.manage;

import cn.javaweb.course.model.EnrollmentModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/course/students")
public class StudentsServlet extends OpenApi {
    @Override
    protected void doGet(
        HttpServletRequest req,
        HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        Integer courseId = Integer.valueOf(req.getParameter("id")) ;
        EnrollmentModel enrollmentModel = new EnrollmentModel(appConfig);
        if(courseId<1){
            error("未找到课程数据");
        }
        success("查询成功", enrollmentModel.getStudents(courseId));
    }
}
