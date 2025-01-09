package cn.javaweb.library;

import cn.javaweb.base.entity.User;
import cn.javaweb.base.model.UserModel;

import java.util.UUID;

public class Token {
    public static String generateToken(){
        return UUID.randomUUID().toString();
    }

    public static User checkToken(String token, Config appConfig) {

        UserModel userModel = new UserModel(appConfig);
        User u = userModel.getUserByToken(token);
        return u;
    }
}
