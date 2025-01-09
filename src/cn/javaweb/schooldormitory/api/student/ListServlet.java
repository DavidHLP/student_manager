package cn.javaweb.schooldormitory.api.student;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.schooldormitory.entity.College;
import cn.javaweb.schooldormitory.entity.PageInfo;
import cn.javaweb.schooldormitory.entity.Student;
import cn.javaweb.schooldormitory.model.CollegeModel;
import cn.javaweb.schooldormitory.model.StudentModel;
import cn.javaweb.schooldormitory.service.StudentService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/student/list")
public class ListServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取配置对象
        Config appConfig = (Config) req.getAttribute("AppConfig");
        StudentService studentService = new StudentService(appConfig);
            // 从请求中获取分页参数
            int page = Integer.parseInt(req.getParameter("page"));
            int limit = Integer.parseInt(req.getParameter("limit"));
            String studentName = req.getParameter("studentName");

            // 计算分页偏移量
            int offset = (page - 1) * limit;

            PageInfo<Student> pageInfos = studentService.getStudentList(page,limit,studentName);

            // 返回结果
            success("查询成功",pageInfos);
    }
}