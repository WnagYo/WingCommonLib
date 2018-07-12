package com.wint_ti.commonlib.network.commonokgo.resulthelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;
import android.widget.Toast;

import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.convert.StringConvert;
import com.lzy.okgo.request.base.Request;

import okhttp3.Response;

/**
 * Created by yitu on 2017/9/27.
 * 时间 16:18
 */

public abstract class StringCallback<T> extends AbsCallback<T> {

    private Activity context;
    private StringConvert convert;

    private ProgressDialog dialog;

    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public StringCallback(Activity activity) {
        super();
        this.context = activity;
        initDialog(activity);
        convert = new StringConvert();

    }

    @Override
    public void onStart(Request request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onFinish() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onError(com.lzy.okgo.model.Response<T> response) {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
        if (response.code() == -1) {
            Toast.makeText(context, "服务器连接失败,请检查网络或稍候重试", Toast.LENGTH_SHORT).show();
        }
        super.onError(response);
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        T s = (T) convert.convertResponse(response);
        response.close();
        return s;
    }
}