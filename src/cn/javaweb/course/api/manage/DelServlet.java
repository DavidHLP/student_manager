package cn.javaweb.course.api.manage;

import cn.javaweb.base.entity.IDInfo;
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

@WebServlet("/api/course/del")
public class DelServlet extends OpenApi {
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        CourseModel courseModel = new CourseModel(appConfig);

        IDInfo idInfo = JSON.parseObject(Util.getJsonParam(req), IDInfo.class);

        if(idInfo == null){
            error("数据格式不正确");
        }
        try {
            success("操作成功", courseModel.del(idInfo));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
