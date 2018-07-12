package com.wint_ti.commonlib.network.commonokgo.model;

import java.io.Serializable;

/**
 * Created by yitu on 2017/9/27.
 * 时间 16:02
 */
public class BaseResponse implements Serializable {
    private static final long serialVersionUID = 5375804597574885028L;
    public int code;
    public String message;
}
