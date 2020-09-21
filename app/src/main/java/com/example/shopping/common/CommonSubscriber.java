package com.example.shopping.common;

import android.text.TextUtils;
import android.util.Log;

import com.example.shopping.interfaces.IBaseView;

import io.reactivex.subscribers.ResourceSubscriber;


public abstract class CommonSubscriber<T> extends ResourceSubscriber<T> {
    private IBaseView mView;
    private String errorMsg;
    private boolean isShowSubErrorState = false;

    protected CommonSubscriber(IBaseView view){
        mView = view;
    }
    protected CommonSubscriber(IBaseView view,String msg){
        mView = view;
        errorMsg = msg;
    }
    protected CommonSubscriber(IBaseView view,boolean isError){
        mView = view;
        isShowSubErrorState = isError;
    }
    protected CommonSubscriber(IBaseView view,String msg,boolean isError){
        mView = view;
        errorMsg = msg;
        isShowSubErrorState = isError;
    }


    private static final String TAG = "CommonSubscriber";
    @Override
    public void onError(Throwable t) {
        Log.d(TAG, "onError: "+t.getMessage());
        if (mView==null)return;
        if (errorMsg!=null&& TextUtils.isEmpty(errorMsg)){
            mView.showTips(errorMsg);
        }
    }

    @Override
    public void onComplete() {

    }
}
