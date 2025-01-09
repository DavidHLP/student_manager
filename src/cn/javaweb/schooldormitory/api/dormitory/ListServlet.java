package cn.javaweb.schooldormitory.api.dormitory;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.schooldormitory.entity.DormitoryInfo;
import cn.javaweb.schooldormitory.entity.PageInfo;
import cn.javaweb.schooldormitory.service.DormitoryService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/dormitory/list")
public class ListServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 获取配置对象
        Config appConfig = (Config) req.getAttribute("AppConfig");
        DormitoryService dormitoryModel = new DormitoryService(appConfig);
        // 从请求中获取分页参数
        int page = Integer.parseInt(req.getParameter("page"));
        int limit = Integer.parseInt(req.getParameter("limit"));
        String dormitoryName = req.getParameter("dormitoryName");
        // 计算分页偏移量
        int offset = (page - 1) * limit;

        PageInfo<DormitoryInfo> pageInfos = null;
        try {
            pageInfos = dormitoryModel.getDormitoryInfo(page,limit,dormitoryName);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        System.out.println(pageInfos);

            // 返回结果
        success("查询成功",pageInfos);
    }
}