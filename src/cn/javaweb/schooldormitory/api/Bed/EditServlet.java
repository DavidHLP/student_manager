package cn.javaweb.schooldormitory.api.Bed;

import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;
import cn.javaweb.library.Util;
import cn.javaweb.schooldormitory.entity.BedRequest;
import cn.javaweb.schooldormitory.service.BedService;
import com.alibaba.fastjson2.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/bed/edit")
public class EditServlet extends OpenApi {
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        BedService bedService = new BedService(appConfig);
        BedRequest bedRequest = JSON.parseObject(Util.getJsonParam(req), BedRequest.class);
        if (bedRequest.getPrevBedId() != null) {
            try {
                bedService.updateStatus(null,bedRequest.getPrevBedId(),"available");
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            bedService.updateStatus(bedRequest.getUserId(),bedRequest.getNowBedId(),"occupied");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        success("更新完成",null);
    }
}
