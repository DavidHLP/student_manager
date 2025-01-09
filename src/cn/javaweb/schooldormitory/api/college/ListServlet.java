package cn.javaweb.schooldormitory.api.college;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.schooldormitory.entity.College;
import cn.javaweb.schooldormitory.entity.PageInfo;
import cn.javaweb.schooldormitory.model.CollegeModel;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/college/list")
public class ListServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取配置对象
        Config appConfig = (Config) req.getAttribute("AppConfig");
        CollegeModel collegeModel = new CollegeModel(appConfig);
            // 从请求中获取分页参数
            int page = Integer.parseInt(req.getParameter("page"));
            int limit = Integer.parseInt(req.getParameter("limit"));
            String collegeName = req.getParameter("collegeName");

            // 计算分页偏移量
            int offset = (page - 1) * limit;

            PageInfo<College> pageInfos = collegeModel.getPaginatedList(page,limit,collegeName);

            // 返回结果
            success("查询成功",pageInfos);
    }
}