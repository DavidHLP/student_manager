package cn.javaweb.course.api.student;

import cn.javaweb.base.entity.PageInfo;
import cn.javaweb.base.entity.User;
import cn.javaweb.course.model.CourseModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/course/avalible")
public class AvalibleServlet  extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        User user = (User) req.getAttribute("user");
        CourseModel courseModel = new CourseModel(appConfig);

        // 分页参数
        PageInfo pi = new PageInfo(req);

        // 查询参数
        HashMap<String,Object> params = new HashMap<>();
        String p1 = req.getParameter("course_name");
        if(p1!=null && !p1.equals("")){
            params.put("course_name", p1);
        }

        String p2 = req.getParameter("term");
        if(p2!=null && !p2.equals("")){
            params.put("term", p2);
        }

        success("查询成功", courseModel.getPageData2(pi, params, user.getId()));
    }
}
