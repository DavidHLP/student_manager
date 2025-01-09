package cn.javaweb.course.api.student;

import cn.javaweb.base.entity.User;
import cn.javaweb.course.entity.SelectCourse;
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

@WebServlet("/api/course/select")
public class SelectServlet extends OpenApi {
    @Override
    protected void doPost(
        HttpServletRequest req,
        HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        User user = (User) req.getAttribute("user");
        CourseModel courseModel = new CourseModel(appConfig);

        SelectCourse selectCourse = (SelectCourse) JSON.parseObject(Util.getJsonParam(req), SelectCourse.class);

        String msg = null;
        try {
            msg = courseModel.chooseCourse(user.getId(), selectCourse.getId(), selectCourse.getAction() );
        } catch (Exception e) {
            error(e.getMessage());
        }
        if(msg.equals("")){
            success("操作成功", null);
        }else{
            error(msg);
        }

    }

}
