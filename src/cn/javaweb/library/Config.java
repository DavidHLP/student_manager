package cn.javaweb.library;


import com.alibaba.fastjson2.JSON;

import java.util.Arrays;

public class Config {
    private String dbUser;
    private String dbPassword;
    private String dbUrl;
    private String[] noNeedLoginList = null;
    private String[] noNeedRightList = null;

    public boolean setDbUser(String v){
        this.dbUser = v;
        return true;
    }
    public String getDbUser(){
        return this.dbUser;
    }
    public boolean setDbPassword(String v){
        this.dbPassword = v;
        return true;
    }
    public boolean setDbUrl(String v){
        this.dbUrl = v;
        return true;
    }

    public String getDbPassword() {
        return dbPassword;
    }

    public String getDbUrl() {
        return dbUrl;
    }

    public boolean isNoNeedLogin(String url) {
        for(int i=0; i<noNeedLoginList.length; i++){
            if(noNeedLoginList[i].equals(url))
                return true;
        }
        return false;
    }


    public boolean isNoNeedRight(String url) {
        for(int i=0; i<noNeedRightList.length; i++){
            if(noNeedRightList[i].equals(url))
                return true;
        }
        return false;
    }


    public void setNoNeedLoginList(String noNeedLoginList) {
        this.noNeedLoginList = noNeedLoginList.split(",");
    }

    public void setNoNeedRightList(String noNeedRightList) {
        this.noNeedRightList = noNeedRightList.split(",");
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}
