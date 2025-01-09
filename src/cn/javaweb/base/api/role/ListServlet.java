package cn.javaweb.base.api.role;

import cn.javaweb.base.entity.PageInfo;
import cn.javaweb.base.model.RoleModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet("/api/role/list")
public class ListServlet extends OpenApi {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        RoleModel roleModel = new RoleModel(appConfig);

        // 分页参数
        PageInfo pi = new PageInfo(req);

        // 查询参数
        HashMap<String,Object> params = new HashMap<>();
        String p1 = req.getParameter("role_name");
        if(p1!=null && !p1.equals("")){
            params.put("role_name", p1);
        }

        success("查询成功", roleModel.getPageData(pi, params));
    }
}
