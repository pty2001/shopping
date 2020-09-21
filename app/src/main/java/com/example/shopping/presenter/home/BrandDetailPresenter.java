package com.example.shopping.presenter.home;

import com.example.shopping.base.BasePresenter;
import com.example.shopping.bean.BrandDetailBean;
import com.example.shopping.bean.ParticularsBean;
import com.example.shopping.common.CommonSubscriber;
import com.example.shopping.interfaces.home.IBrandDetail;
import com.example.shopping.model.HttpManager;
import com.example.shopping.utils.RxUtils;

public class BrandDetailPresenter extends BasePresenter<IBrandDetail.BrandDetailView> implements IBrandDetail.BrandDetailPresenter {
    @Override
    public void getBrandDetail(int id) {
        addSubscribe(HttpManager.getInstance().getShoppingApi().getBrandDetail(id)
                .compose(RxUtils.<BrandDetailBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<BrandDetailBean>(mView) {
                    @Override
                    public void onNext(BrandDetailBean brandDetailBean) {
                        mView.getBrandDetailResult(brandDetailBean);
                    }
                }));
    }
}
