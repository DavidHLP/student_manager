package cn.javaweb.base.api.menu;

import cn.javaweb.base.entity.IDInfo;
import cn.javaweb.base.entity.Menu;
import cn.javaweb.base.model.MenuModel;
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

@WebServlet("/api/menu/del")
public class DelServlet extends OpenApi {
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        MenuModel menuModel = new MenuModel(appConfig);

        IDInfo idInfo = JSON.parseObject(Util.getJsonParam(req), IDInfo.class);

        if(idInfo == null){
            error("数据格式不正确");
        }
        try {
            success("操作成功", menuModel.del(idInfo));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}