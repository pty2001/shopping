package com.example.shopping.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.shopping.interfaces.IBasePresenter;
import com.example.shopping.interfaces.IBaseView;


public abstract class BaseFragment<P extends IBasePresenter> extends Fragment implements IBaseView {
    protected  P presenter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(getLayout(), null);
        return inflate;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        presenter  = initPresenter();
        if (presenter!=null){
            presenter.attachView(this);
            initData();
        }
    }
    protected abstract int getLayout();

    protected abstract void initData();

    protected abstract P initPresenter();

    protected abstract void initView();

    @Override
    public void showTips(String tips) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (presenter!=null){
            presenter.detachView();
        }
    }
}
