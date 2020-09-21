package com.example.shopping.interfaces.shopping;

import com.example.shopping.bean.AddCartBean;
import com.example.shopping.bean.BrandDetailBean;
import com.example.shopping.bean.CartBean;
import com.example.shopping.bean.DeleteCardBean;
import com.example.shopping.bean.ParticularsBean;
import com.example.shopping.interfaces.IBasePresenter;
import com.example.shopping.interfaces.IBaseView;

public interface ICart {
    interface AddCartView extends IBaseView {
        void getAddCartResult(ParticularsBean addCartBean);
        void addCartInfoReturn(AddCartBean result);
    }
    interface AddCartPresenter extends IBasePresenter<AddCartView> {
        void getAddCart(int id);
        void addCart(int goodsId,int number,int productId);
    }




    interface ICartView extends IBaseView{
        void getCartListReturn(CartBean result);

        void deleteCartListReturn(DeleteCardBean result);
    }

    interface ICartPersenter extends IBasePresenter<ICartView>{

        //获取购物车的数据
        void getCartList();

        //删除购物车数据
        void deleteCartList(String productIds);

    }
}
