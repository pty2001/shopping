package com.example.shopping.presenter.shopping;

import com.example.shopping.base.BasePresenter;
import com.example.shopping.bean.CartBean;
import com.example.shopping.bean.DeleteCardBean;
import com.example.shopping.common.CommonSubscriber;
import com.example.shopping.interfaces.shopping.ICart;
import com.example.shopping.model.HttpManager;
import com.example.shopping.utils.RxUtils;

public class CartPresenter extends BasePresenter<ICart.ICartView>implements ICart.ICartPersenter {

    @Override
    public void getCartList() {
        addSubscribe(HttpManager.getInstance().getShoppingApi().getCart()
                .compose(RxUtils.<CartBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<CartBean>(mView) {
                    @Override
                    public void onNext(CartBean result) {
                        mView.getCartListReturn(result);
                    }
                }));
    }

    @Override
    public void deleteCartList(String productIds) {
        addSubscribe(HttpManager.getInstance().getShoppingApi().getDeleteCart(productIds)
                .compose(RxUtils.<DeleteCardBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<DeleteCardBean>(mView) {
                    @Override
                    public void onNext(DeleteCardBean result) {
                        mView.deleteCartListReturn(result);
                    }
                }));
    }
}
