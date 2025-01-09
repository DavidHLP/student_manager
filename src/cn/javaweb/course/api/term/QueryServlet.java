package cn.javaweb.course.api.term;

import cn.javaweb.base.entity.PageInfo;
import cn.javaweb.course.model.TermModel;
import cn.javaweb.library.Config;
import cn.javaweb.library.OpenApi;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/api/term/list")
public class QueryServlet extends OpenApi {
    @Override
    protected void doGet(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config)req.getAttribute("AppConfig");
        TermModel termModel = new TermModel(appConfig);

        PageInfo pi = new PageInfo(req);

        HashMap<String, Object> params = new HashMap<>();
        String termName = (String)req.getParameter("termName");
        if(termName!=null && !termName.equals("")){
            params.put("term_name", termName);
        }

        success("查询成功", termModel.getPageData(pi, params));

    }
}
