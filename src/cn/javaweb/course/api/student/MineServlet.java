package cn.javaweb.course.api.student;

import cn.javaweb.base.entity.User;
import cn.javaweb.course.model.CourseModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/course/my")
public class MineServlet  extends OpenApi {
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        // 获取导航菜单
        Config appConfig = (Config) req.getAttribute("AppConfig");
        User user = (User) req.getAttribute("user");
        CourseModel courseModel = new CourseModel(appConfig);

        success("请求成功", courseModel.getMyCourse(user.getId()));
    }
}
