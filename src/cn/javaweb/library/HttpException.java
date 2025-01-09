package cn.javaweb.library;

public class HttpException extends RuntimeException{

    protected AjaxResult result;

    public AjaxResult getResult()
    {
        return this.result;
    }
    public HttpException(AjaxResult r) {
        result = r;
    }
}
