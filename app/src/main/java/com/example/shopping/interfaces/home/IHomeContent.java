package com.example.shopping.interfaces.home;

import com.example.shopping.bean.HomeContentBean;
import com.example.shopping.interfaces.IBasePresenter;
import com.example.shopping.interfaces.IBaseView;

import java.util.List;

public interface IHomeContent {
    interface HomeContentView extends IBaseView{
        void getContentResult(List<HomeContentBean.HomeListBean> dataBeans);
    }
    interface HomeContentPresenter extends IBasePresenter<HomeContentView> {
        void getContent();
    }
}
