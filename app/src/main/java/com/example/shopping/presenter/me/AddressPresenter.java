package com.example.shopping.presenter.me;

import com.example.shopping.base.BasePresenter;
import com.example.shopping.bean.AddressBean;
import com.example.shopping.common.CommonSubscriber;
import com.example.shopping.interfaces.me.IAddress;
import com.example.shopping.model.HttpManager;
import com.example.shopping.utils.RxUtils;

public class AddressPresenter extends BasePresenter<IAddress.IAddressView> implements IAddress.ICartPersenter {
    @Override
    public void getAddress(int id) {
        addSubscribe(HttpManager.getInstance().getShoppingApi().getAddress(id)
                .compose(RxUtils.<AddressBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<AddressBean>(mView) {
                    @Override
                    public void onNext(AddressBean result) {
                        mView.getAddressReturn(result);
                    }
                }));
    }
}
