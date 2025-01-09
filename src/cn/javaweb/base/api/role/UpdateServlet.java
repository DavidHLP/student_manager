package cn.javaweb.base.api.role;

import cn.javaweb.base.entity.Role;
import cn.javaweb.base.model.RoleModel;
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

@WebServlet("/api/role/edit")
public class UpdateServlet extends OpenApi {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        RoleModel roleModel = new RoleModel(appConfig);
        Role role = JSON.parseObject(Util.getJsonParam(req), Role.class);
        if(role==null){
            error("数据格式不争取");
        }
        try {
            success("更新成功", roleModel.edit(role));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
