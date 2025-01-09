/**
 * 登录接口
 */
package cn.javaweb.base.api;

import cn.javaweb.base.entity.LoginInfo;
import cn.javaweb.base.entity.User;
import cn.javaweb.library.*;
import cn.javaweb.base.model.UserModel;
import com.alibaba.fastjson2.JSON;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/api/login")
public class LoginServlet extends OpenApi {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Config conf = (Config)req.getAttribute("AppConfig");

        LoginInfo li = JSON.parseObject(Util.getJsonParam(req), LoginInfo.class);
        if(li==null){
            error("用户名或密码不能为空");
        }
        UserModel userModel = new UserModel(conf);

        User user = userModel.getUserByName(li.getUsername());

        if(user==null){
            //用户名不对
            error("用户名或者密码错误");
        }else{
            if(user.getStatus()==0){
                // 账户禁用
                error("用户账号被禁用，请联系管理员");
            }else if(user.getLoginFailure()>=5){
                if(user.getPreventTime()>System.currentTimeMillis() / 1000){
                    // 账户被冻结
                    error("密码输入次数过多，账户被锁定，请稍后重试");
                }
            }
            if(!user.getPassword().equals(li.getPassword())){
                //密码错误
                user.setLoginFailure(user.getLoginFailure()+1);
                //超过5锁定30分钟
                if(user.getLoginFailure()>=5){
                    user.setPreventTime( System.currentTimeMillis() / 1000 + 30 * 60 );
                }
                try {
                    userModel.updateUserToken(user);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                error("用户名或者密码错误");
            }
        }

        String token = Token.generateToken();
        user.setToken(token);
        user.setLoginFailure(0);
        try {
            userModel.updateUserToken(user);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        // 敏感词过滤
        user.setPassword("***");
        user.setPwdQuestion("***");
        user.setPwdAnswer("***");

        success("登陆成功", user);

    }

}
