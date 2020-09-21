package com.example.shopping.interfaces;

public interface IBasePresenter<T extends IBaseView> {
    //V层接口的关联
    void attachView(T view);
    //V层接口的取消关联
    void detachView();
}
