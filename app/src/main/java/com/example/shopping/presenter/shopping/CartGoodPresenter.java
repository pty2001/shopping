package com.example.shopping.presenter.shopping;

import com.example.shopping.base.BasePresenter;
import com.example.shopping.bean.AddCartBean;
import com.example.shopping.bean.BrandDetailBean;
import com.example.shopping.bean.ParticularsBean;
import com.example.shopping.common.CommonSubscriber;
import com.example.shopping.interfaces.shopping.ICart;
import com.example.shopping.model.HttpManager;
import com.example.shopping.utils.RxUtils;

public class CartGoodPresenter extends BasePresenter<ICart.AddCartView> implements ICart.AddCartPresenter{
    @Override
    public void getAddCart(int id) {
        addSubscribe(HttpManager.getInstance().getShoppingApi().getParticulars(id)
                .compose(RxUtils.<ParticularsBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<ParticularsBean>(mView) {
                    @Override
                    public void onNext(ParticularsBean result) {
                        mView.getAddCartResult(result);
                    }
                }));
    }

    @Override
    public void addCart(int goodsId, int number, int productId) {
        addSubscribe(HttpManager.getInstance().getShoppingApi().getAddCart(goodsId,number,productId)
                .compose(RxUtils.<AddCartBean>rxScheduler())
                .subscribeWith(new CommonSubscriber<AddCartBean>(mView) {
                    @Override
                    public void onNext(AddCartBean result) {
                        mView.addCartInfoReturn(result);
                    }
                }));
    }
}
