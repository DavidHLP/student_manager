package cn.javaweb.base.entity;

import javax.servlet.http.HttpServletRequest;

public class PageInfo {
    private int page;
    private int limit;

    public int getPage() {
        return page;
    }

    public int getOffset() {
        return (page-1)*limit;
    }

    public int getLimit() {
        return limit;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public PageInfo(HttpServletRequest req)
    {
        String p1 = req.getParameter("page");
        String p2 = req.getParameter("limit");

        if(p1==null){
            this.page = 1;
        }else{
            this.page = Integer.parseInt(p1);
        }

        if(p2==null){
            this.limit = 10;
        }else{
            this.limit = Integer.parseInt(p2);
        }
    }
}
