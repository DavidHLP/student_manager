package cn.javaweb.course.api.term;

import cn.javaweb.course.entity.Term;
import cn.javaweb.course.model.TermModel;
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

@WebServlet("/api/term/add")
public class AddServlet extends OpenApi {
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        Config appConfig = (Config) req.getAttribute("AppConfig");
        TermModel termModel = new TermModel(appConfig);

        Term term = JSON.parseObject(Util.getJsonParam(req), Term.class);

        if(term == null)
            error("您输入的数据格式不正确");

        try {
            success("添加成功", termModel.add(term));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}
