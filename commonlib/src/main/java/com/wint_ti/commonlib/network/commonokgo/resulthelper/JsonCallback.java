package com.wint_ti.commonlib.network.commonokgo.resulthelper;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.base.Request;
import com.wint_ti.commonlib.network.commonokgo.model.BaseResponse;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by yitu on 2017/9/27.
 * 时间 16:18
 */

public abstract class JsonCallback<T> extends AbsCallback<T> {

    private Activity context;

    private ProgressDialog dialog;

    private void initDialog(Activity activity) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage("请求网络中...");
    }

    public JsonCallback(Activity activity) {
        super();
        this.context = activity;
        initDialog(activity);
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
            Toast.makeText(context, "服务器连接失败，请检查网络或稍候重试", Toast.LENGTH_SHORT).show();
        }
        super.onError(response);
    }

    @Override
    public T convertResponse(Response response) throws Throwable {
        ResponseBody body = response.body();
        if (body == null) return null;
        T data = null;
        Gson gson = new Gson();
        JsonReader jsonReader = new JsonReader(body.charStream());
        Type genType = getClass().getGenericSuperclass();
        Type type = ((ParameterizedType) genType).getActualTypeArguments()[0];
        data = gson.fromJson(jsonReader, type);
        //判断
        int code = ((BaseResponse) data).code;
        final String msg = ((BaseResponse) data).message;
        if (code == 1000) {//请求成功
            return data;
        } else {//请求失败，显示后台返回信息
//            if (code == 1005 || code == 1006) {
//                context.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        String[] split = msg.split("-");
//                        showOtherProcessDialog(split[0], split[1]);
//                    }
//                });
//            }
            throw new SuccessErrorCodeException(code, ((BaseResponse) data).message);
        }
    }
}