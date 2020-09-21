package com.example.shopping.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.base.BaseAdapter;
import com.example.shopping.bean.AddCartBean;
import com.example.shopping.bean.CartBean;
import com.example.shopping.common.AddDeleteView;

import java.util.List;

public class ShoppingCartAdapter extends BaseAdapter {
    public boolean isCompile ;
    private static final String TAG = "ShoppingCartAdapter";
    public ShoppingCartAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int getLayout() {
        return R.layout.shoppingcart_item;
    }

    @Override
    protected void bindData(BaseViewHolder vh, Object data) {
        CheckBox shopping_rv_cb = (CheckBox) vh.getViewById(R.id.shopping_rv_cb);
        ImageView shopping_rv_img = (ImageView) vh.getViewById(R.id.shopping_rv_img);
        TextView shopping_rv_name = (TextView) vh.getViewById(R.id.shopping_rv_name);
        TextView shopping_rv_price = (TextView) vh.getViewById(R.id.shopping_rv_price);
        TextView shopping_rv_number = (TextView) vh.getViewById(R.id.shopping_rv_number);
        TextView shopping_rv_selected = (TextView) vh.getViewById(R.id.shopping_rv_selected);
        AddDeleteView shopping_rv_adddeleteview = (AddDeleteView) vh.getViewById(R.id.shopping_rv_adddeleteview);
        CartBean.DataBean.CartListBean cartListBean = (CartBean.DataBean.CartListBean) data;
        if (isCompile){
            shopping_rv_name.setVisibility(View.GONE);
            shopping_rv_number.setVisibility(View.GONE);
            shopping_rv_selected.setVisibility(View.VISIBLE);
            shopping_rv_adddeleteview.setVisibility(View.VISIBLE);
        }else {
            shopping_rv_name.setVisibility(View.VISIBLE);
            shopping_rv_number.setVisibility(View.VISIBLE);
            shopping_rv_selected.setVisibility(View.GONE);
            shopping_rv_adddeleteview.setVisibility(View.GONE);
        }
        Glide.with(context).load(cartListBean.getList_pic_url()).into(shopping_rv_img);
        shopping_rv_name.setText(cartListBean.getGoods_name());
        shopping_rv_price.setText("￥"+cartListBean.getMarket_price());
        shopping_rv_number.setText("x"+cartListBean.getNumber());
        shopping_rv_adddeleteview.initView();
        shopping_rv_adddeleteview.setValue(cartListBean.getNumber());
        shopping_rv_adddeleteview.setClickLinter(new AddDeleteView.OnClickLinter() {
            @Override
            public void onClicks(int num) {
                cartListBean.setNumber(num);
            }
        });
        shopping_rv_cb.setSelected(cartListBean.select);
        shopping_rv_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                cartListBean.select = !cartListBean.select;
                if (checkBokClick!=null){
                    checkBokClick.checkChange();
                }
            }
        });
    }
    CheckBokClick checkBokClick;

    public void setCheckBokClick(CheckBokClick checkBokClick) {
        this.checkBokClick = checkBokClick;
    }

    public interface CheckBokClick{
        void checkChange();
    }
}
