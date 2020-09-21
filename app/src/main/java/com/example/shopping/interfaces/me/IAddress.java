package com.example.shopping.interfaces.me;

import com.example.shopping.bean.AddressBean;
import com.example.shopping.interfaces.IBasePresenter;
import com.example.shopping.interfaces.IBaseView;

public interface IAddress {
    interface IAddressView extends IBaseView {
        void getAddressReturn(AddressBean result);
    }

    interface ICartPersenter extends IBasePresenter<IAddressView> {
        void getAddress(int id);
    }
}
