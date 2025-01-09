package cn.javaweb.base.api;

import cn.javaweb.base.entity.User;
import cn.javaweb.base.model.UserModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/logout")
public class LogoutServlet extends OpenApi {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Config conf = (Config)req.getAttribute("AppConfig");
        User user = (User) req.getAttribute("user");
        if(user == null){
            success("退出成功", null);
        }

        UserModel userModel = new UserModel(conf);
        try {
            userModel.logout(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        success("退出成功", null);

    }
}
