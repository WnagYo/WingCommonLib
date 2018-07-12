package com.wint_ti.commonlib.network.commonokgo.resulthelper;


/**
 * 网络请求失败
 */
public class SuccessErrorCodeException extends Exception {
    public int code;
    public String msg;

    public SuccessErrorCodeException(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
