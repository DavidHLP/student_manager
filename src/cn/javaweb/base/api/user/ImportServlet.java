package cn.javaweb.base.api.user;

import cn.javaweb.library.OpenApi;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.List;

@WebServlet("/api/user/import")
public class ImportServlet extends OpenApi {
    @Override
    protected void doPost(
            HttpServletRequest req,
            HttpServletResponse resp
    ) throws ServletException, IOException {
        if (!ServletFileUpload.isMultipartContent(req)) {
            throw new IllegalArgumentException("表单必须包含enctype=multipart/form-data");
        }
        try {
        // 创建ServletFileUpload实例
            
            ServletFileUpload upload = new ServletFileUpload();
            List<FileItem> items = upload.parseRequest(req);
            for( FileItem item: items ) {
                if (!item.isFormField()) {
                    String fileName = item.getName();
                    String filePath = getServletContext().getRealPath("/") + "uploads/" + fileName;
                    File file = new File(filePath);
                    item.write(file);
                }
            }
        } catch (Exception e){
            error(e.getMessage());
        }
        success("上传成功", null);
    }
}
