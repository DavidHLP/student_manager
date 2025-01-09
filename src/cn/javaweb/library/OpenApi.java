package cn.javaweb.library;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

public class OpenApi<T> extends HttpServlet {

    public void success(String msg, T data) throws IOException {
        throw new HttpException(AjaxResult.success(msg, data));
    }

    public void error(String msg) throws IOException {
        throw new HttpException(AjaxResult.error(msg));
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        error("不支持的请求类型");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        error("不支持的请求类型");
    }
}
