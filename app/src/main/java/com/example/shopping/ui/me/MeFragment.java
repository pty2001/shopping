package com.example.shopping.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.shopping.R;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.interfaces.IBasePresenter;


public class MeFragment extends BaseFragment implements View.OnClickListener{


    private TextView me_address;
    private TextView me_login;

    @Override
    protected int getLayout() {
        return R.layout.fragment_me;
    }

    @Override
    protected void initData() {

    }

    @Override
    protected IBasePresenter initPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        me_address = getActivity().findViewById(R.id.me_address);
        me_login = getActivity().findViewById(R.id.me_login);
        me_address.setOnClickListener(this);
        me_login.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.me_address:
                startActivity(new Intent(getActivity(),AddressActivity.class));
                break;
            case R.id.me_login:

                break;
        }
    }
}