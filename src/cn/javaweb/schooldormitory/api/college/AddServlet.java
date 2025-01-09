package cn.javaweb.schooldormitory.api.college;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.library.Util;
import cn.javaweb.schooldormitory.entity.College;
import cn.javaweb.schooldormitory.entity.DormitoryInfo;
import cn.javaweb.schooldormitory.model.CollegeModel;
import cn.javaweb.schooldormitory.service.DormitoryService;
import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/college/add")
public class AddServlet extends OpenApi<Void> {    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        CollegeModel collegeModel = new CollegeModel(appConfig);

        College college = JSON.parseObject(Util.getJsonParam(req), College.class);
        if(college == null){
            error("数据格式不正确");
        }
    try {
        success("操作成功", collegeModel.add(college));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
}
