package com.example.shopping.interfaces.home;

import com.example.shopping.bean.BrandDetailBean;
import com.example.shopping.bean.ParticularsBean;
import com.example.shopping.interfaces.IBasePresenter;
import com.example.shopping.interfaces.IBaseView;

public interface IBrandDetail {
    interface BrandDetailView extends IBaseView {
        void getBrandDetailResult(BrandDetailBean brandDetailBean);
    }
    interface BrandDetailPresenter extends IBasePresenter<BrandDetailView> {
        void getBrandDetail(int id);
    }
}
