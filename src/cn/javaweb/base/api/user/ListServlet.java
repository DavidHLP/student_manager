package cn.javaweb.base.api.user;

import cn.javaweb.base.entity.PageInfo;
import cn.javaweb.base.model.UserModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;

@WebServlet("/api/user/list")
public class ListServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        UserModel userModel = new UserModel(appConfig);

        // 分页参数
        PageInfo pi = new PageInfo(req);

        // 查询参数
        HashMap<String,Object> params = new HashMap<>();
        String p1 = req.getParameter("realname");
        if(p1!=null && !p1.equals("")){
            params.put("realname", p1);
        }
        String p2 = req.getParameter("roleId");
        if(p2!=null && !p2.equals("")){
            params.put("role_id", Integer.valueOf(p2));
        }
        String p3 = req.getParameter("depId");
        if(p3!=null && !p3.equals("")){
            params.put("dep_id", Integer.valueOf(p3));
        }

        try {
            success("查询成功", userModel.getPageData(pi, params));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
