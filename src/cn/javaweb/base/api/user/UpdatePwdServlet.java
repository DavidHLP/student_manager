package cn.javaweb.base.api.user;

import cn.javaweb.base.entity.ResetPassword;
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

@WebServlet("/api/user/updatepwd")
public class UpdatePwdServlet extends OpenApi {
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        User user = (User) req.getAttribute("user");
        UserModel userModel = new UserModel(appConfig);
        ResetPassword resetPassword = JSON.parseObject(Util.getJsonParam(req), ResetPassword.class);
        if(resetPassword==null)
            error("参数不正确");

        try {
            success("操作完成", userModel.updatePassword(resetPassword, user.getId()));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
