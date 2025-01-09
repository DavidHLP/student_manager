package cn.javaweb.schooldormitory.api.Bed;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.schooldormitory.model.BedModel;
import cn.javaweb.schooldormitory.model.CollegeModel;
import cn.javaweb.schooldormitory.service.BedService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/bed/del")
public class DelServlet extends OpenApi<Void> {
    @Override
    protected void doDelete(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws  IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        BedService bedService = new BedService(appConfig);

        Integer id = Integer.parseInt(req.getParameter("id"));
        try {
            success("操作成功", bedService.del(id , "available"));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
