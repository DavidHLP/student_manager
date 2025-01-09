package cn.javaweb.schooldormitory.api.college;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.library.Util;
import cn.javaweb.schooldormitory.entity.College;
import cn.javaweb.schooldormitory.model.CollegeModel;
import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/college/edit")
public class EditServlet extends OpenApi {
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        CollegeModel collegeModel = new CollegeModel(appConfig);
        // 从请求中解析 JSON 数据
        College college = JSON.parseObject(Util.getJsonParam(req), College.class);
        // 校验数据
        if (college.getId() == null || college.getName() == null) {
            error("缺少必要的参数");
            return;
        }
        try {
            collegeModel.updateCollege(college);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        success("更新完成",null);
    }
}
