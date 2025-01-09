package cn.javaweb.base.api.user;

import cn.javaweb.base.entity.User;
import cn.javaweb.base.model.UserModel;
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

@WebServlet("/api/user/add")
public class AddServlet extends OpenApi {    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        UserModel userModel = new UserModel(appConfig);

        User user = JSON.parseObject(Util.getJsonParam(req), User.class);
        if(user == null){
            error("数据格式不正确");
        }
    try {
        success("操作成功", userModel.add(user));
    } catch (SQLException e) {
        throw new RuntimeException(e);
    }
}
}
