package cn.javaweb.library;

import com.alibaba.fastjson2.JSON;

import java.util.HashMap;

public class AjaxResult extends HashMap<String, Object> {

    public AjaxResult(int code, String msg){
        super.put("code", code);
        super.put("msg",  msg);
        super.put("data", null);
    }

    public AjaxResult(int code, String msg, Object data){
        super.put("code", code);
        super.put("msg",  msg);
        super.put("data", data);
    }

    public static AjaxResult success(String msg){

        return new AjaxResult(200, msg);
    }

    public static AjaxResult success(String msg, Object data){

        return new AjaxResult(200, msg, data);
    }

    public static AjaxResult error(String msg){
        return new AjaxResult(500, msg);
    }

    public static AjaxResult error(String msg, int code){
        return new AjaxResult(code, msg);
    }

    public static AjaxResult needLogin(){
        return new AjaxResult(401, "请登陆后进行操作");
    }

    public static AjaxResult noPermission(){
        return new AjaxResult(403, "无权限");
    }


    @Override
    public AjaxResult put(String key, Object value)
    {
        super.put(key, value);
        return this;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public static void main(String[] args) {
        System.out.println(AjaxResult.error("OK"));
    }
}
