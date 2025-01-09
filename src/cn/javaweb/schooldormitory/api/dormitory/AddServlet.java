package cn.javaweb.schooldormitory.api.dormitory;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.library.Util;
import cn.javaweb.schooldormitory.entity.DormitoryInfo;
import cn.javaweb.schooldormitory.model.DormitoryModel;
import cn.javaweb.schooldormitory.service.DormitoryService;
import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/dormitory/add")
public class AddServlet extends OpenApi<Void> {    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        DormitoryService dormitoryService = new DormitoryService(appConfig);

        DormitoryInfo dormitory = JSON.parseObject(Util.getJsonParam(req), DormitoryInfo.class);
        if(dormitory == null){
            error("数据格式不正确");
        }
    try {
        success("操作成功", dormitoryService.add(dormitory));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
}
