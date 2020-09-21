package com.example.shopping.base;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.shopping.interfaces.IBasePresenter;
import com.example.shopping.interfaces.IBaseView;


public abstract class BaseActivity<P extends IBasePresenter> extends AppCompatActivity implements IBaseView {
    protected P presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        initView();
        presenter = initPresenter();
        if (presenter != null) {
            presenter.attachView(this);
            initData();
        }

    }

    protected abstract P initPresenter();

    protected abstract void initData();

    protected abstract void initView();

    protected abstract int getLayout();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (presenter != null) {
            presenter.detachView();
        }
    }

    @Override
    public void showTips(String tips) {

    }
}
