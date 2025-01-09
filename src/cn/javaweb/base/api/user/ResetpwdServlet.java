package cn.javaweb.base.api.user;

import cn.javaweb.base.model.UserModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/user/resetpwd")
public class ResetpwdServlet extends OpenApi {
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        UserModel userModel = new UserModel(appConfig);
        String p1 = req.getParameter("id");
        if(p1!=null ){
            try {
                success("操作成功", userModel.resetpassword(Integer.valueOf(p1)));
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        error("参数不正确");
    }

}
