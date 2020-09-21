package com.example.shopping.ui.shopping;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.shopping.MainActivity;
import com.example.shopping.R;
import com.example.shopping.adapter.ShoppingCartAdapter;
import com.example.shopping.adapter.ShoppingCartAdapters;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.bean.AddCartBean;
import com.example.shopping.bean.CartBean;
import com.example.shopping.bean.DeleteCardBean;
import com.example.shopping.interfaces.IBasePresenter;
import com.example.shopping.interfaces.shopping.ICart;
import com.example.shopping.presenter.shopping.CartPresenter;

import java.util.ArrayList;
import java.util.List;


public class ShoppingFragment extends BaseFragment<ICart.ICartPersenter> implements View.OnClickListener,ICart.ICartView{

    private RecyclerView shopping_rv;
    private CheckBox shopping_cb;
    private TextView shopping_allcheck;
    private TextView shopping_allprices;
    private TextView shopping_compile;
    private TextView shopping_belowsingle;
    private List<CartBean.DataBean.CartListBean> cartListBeans;
    private MainActivity mainActivity;
    private int allNumber;
    private int allPrice;
    private ShoppingCartAdapter shoppingCartAdapter;

    @Override
    protected int getLayout() {
        return R.layout.fragment_shopping;
    }

    @Override
    protected void initData() {
        presenter.getCartList();

    }

    @Override
    protected ICart.ICartPersenter initPresenter() {
        return new CartPresenter();
    }

    @Override
    protected void initView() {
        shopping_rv = getActivity().findViewById(R.id.shopping_rv);
        shopping_cb = getActivity().findViewById(R.id.shopping_cb);
        shopping_allcheck = getActivity().findViewById(R.id.shopping_allcheck);
        shopping_allprices = getActivity().findViewById(R.id.shopping_allprices);
        shopping_compile = getActivity().findViewById(R.id.shopping_compile);
        shopping_belowsingle = getActivity().findViewById(R.id.shopping_belowsingle);
        shopping_cb.setOnClickListener(this);
        shopping_compile.setOnClickListener(this);
        shopping_belowsingle.setOnClickListener(this);
        shopping_rv.setLayoutManager(new LinearLayoutManager(getActivity()));
        cartListBeans = new ArrayList<>();
        shoppingCartAdapter = new ShoppingCartAdapter(getActivity(), cartListBeans);
        shopping_rv.setAdapter(shoppingCartAdapter);

        shoppingCartAdapter.setCheckBokClick(new ShoppingCartAdapter.CheckBokClick() {
            @Override
            public void checkChange() {
                boolean bool = CheckSelectAll();
                shopping_allcheck.setText("全选("+allNumber+")");
                shopping_allprices.setText("￥"+allPrice);
                shopping_cb.setSelected(bool);
                shoppingCartAdapter.notifyDataSetChanged();
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.shopping_cb:
                break;
            case R.id.shopping_compile:
                compile();
                break;
            case R.id.shopping_belowsingle:
                String belowsingle = shopping_belowsingle.getText().toString();
                if (belowsingle.equals("下单")){
                    startActivity(new Intent(getActivity(),OrderActivity.class));
                }

                break;
        }
    }

    private void compile() {
        //编辑状态
        String compile = shopping_compile.getText().toString();
        if (compile.equals("完成")){
            shoppingCartAdapter.isCompile = false;
            shopping_allprices.setVisibility(View.VISIBLE);
            shopping_belowsingle.setText("下单");
            shopping_compile.setText("编辑");
        }else if (compile.equals("编辑")){
            shoppingCartAdapter.isCompile = true;
            shopping_allprices.setVisibility(View.GONE);
            shopping_belowsingle.setText("删除所选");
            shopping_compile.setText("完成");

        }
        resetSelect(false);
        shoppingCartAdapter.notifyDataSetChanged();
    }
    //重置选中状态
    private void resetSelect(boolean bool){
        allNumber = 0;
        allPrice = 0;
        for (CartBean.DataBean.CartListBean item:cartListBeans){
            item.select = bool;
            if (bool){
                allNumber += item.getNumber();
                allPrice += item.getNumber()*item.getRetail_price();
            }
        }
        if (!bool){
            allNumber = 0;
            allPrice = 0;
        }
    }

    private static final String TAG = "ShoppingFragment";
    @Override
    public void getCartListReturn(CartBean result) {
        Log.d(TAG, "getCartListReturn: "+result.getData().getCartTotal().toString());
        cartListBeans.addAll(result.getData().getCartList());
        shoppingCartAdapter.notifyDataSetChanged();
    }

    @Override
    public void deleteCartListReturn(DeleteCardBean result) {

    }
    private boolean CheckSelectAll(){
        allNumber = 0;
        allPrice = 0;
        boolean isSelectAll = true;
        for(CartBean.DataBean.CartListBean item:cartListBeans){
            if(item.select){
                allNumber += item.getNumber();
                allPrice += item.getNumber()*item.getRetail_price();
            }
            if(isSelectAll && !item.select){
                isSelectAll = false;
            }
        }
        return isSelectAll;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==100){
            mainActivity.show();
        }
    }
}