package cn.javaweb.schooldormitory.api.dormitory;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.library.Util;
import cn.javaweb.schooldormitory.entity.DormitoryInfo;
import cn.javaweb.schooldormitory.service.DormitoryService;
import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/dormitory/edit")
public class EditServlet extends OpenApi {
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        DormitoryService dormitoryService = new DormitoryService(appConfig);
        // 从请求中解析 JSON 数据
        DormitoryInfo dormitory = JSON.parseObject(Util.getJsonParam(req), DormitoryInfo.class);
        // 校验数据
        if (dormitory.getId() == null || dormitory.getName() == null) {
            error("缺少必要的参数");
            return;
        }
        try {
            dormitoryService.updateDormitory(dormitory);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        success("查询完成",null);
    }
}
