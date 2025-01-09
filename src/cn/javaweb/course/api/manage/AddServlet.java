package cn.javaweb.course.api.manage;

import cn.javaweb.base.entity.User;
import cn.javaweb.course.entity.Course;
import cn.javaweb.course.model.CourseModel;
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

@WebServlet("/api/course/add")
public class AddServlet extends OpenApi {    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        User user = (User) req.getAttribute("user");

        CourseModel courseModel = new CourseModel(appConfig);

        Course course = JSON.parseObject(Util.getJsonParam(req), Course.class);
        if(course == null){
            error("数据格式不正确");
        }
        course.setTeacherId(user.getId());
    try {
        success("操作成功", courseModel.add(course));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
}
