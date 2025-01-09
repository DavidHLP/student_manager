package cn.javaweb.schooldormitory.api.college;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.schooldormitory.model.CollegeModel;
import cn.javaweb.schooldormitory.model.DormitoryModel;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/college/del")
public class DelServlet extends OpenApi {
    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws  IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        CollegeModel collegeModel = new CollegeModel(appConfig);

        int id = Integer.parseInt(req.getParameter("id"));
        try {
            success("操作成功", collegeModel.del(id));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
