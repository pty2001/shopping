package com.example.shopping.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.bean.AddCartBean;
import com.example.shopping.bean.CartBean;
import com.example.shopping.common.AddDeleteView;

import java.util.List;

public class ShoppingCartAdapters extends RecyclerView.Adapter<ShoppingCartAdapters.ViewHolder> {
    List<CartBean.DataBean.CartListBean> cartListBeans;
    Context context;

    public boolean isCompile ;
    public ShoppingCartAdapters(Context context,List<CartBean.DataBean.CartListBean> cartListBeans) {
        this.context = context;
        this.cartListBeans = cartListBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View inflate = LayoutInflater.from(context).inflate(R.layout.shoppingcart_item, parent, false);
        return new ViewHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CartBean.DataBean.CartListBean cartListBean = cartListBeans.get(position);
        Glide.with(context).load(cartListBean.getList_pic_url()).into(holder.shopping_rv_img);
        holder.shopping_rv_name.setText(cartListBean.getGoods_name());
        holder.shopping_rv_price.setText("￥"+cartListBean.getMarket_price());

        if (isCompile){
            holder.shopping_rv_name.setVisibility(View.GONE);
            holder.shopping_rv_number.setVisibility(View.GONE);
            holder.shopping_rv_selected.setVisibility(View.VISIBLE);
            holder.shopping_rv_adddeleteview.setVisibility(View.VISIBLE);
        }else {
            holder.shopping_rv_name.setVisibility(View.VISIBLE);
            holder.shopping_rv_number.setVisibility(View.VISIBLE);
            holder.shopping_rv_selected.setVisibility(View.GONE);
            holder.shopping_rv_adddeleteview.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return cartListBeans.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        CheckBox shopping_rv_cb;
        ImageView shopping_rv_img;
        TextView shopping_rv_name;
        TextView shopping_rv_price;
        TextView shopping_rv_number;
        TextView shopping_rv_selected;
        AddDeleteView shopping_rv_adddeleteview;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            shopping_rv_cb = itemView.findViewById(R.id.shopping_rv_cb);
            shopping_rv_img = itemView.findViewById(R.id.shopping_rv_img);
            shopping_rv_name = itemView.findViewById(R.id.shopping_rv_name);
            shopping_rv_price = itemView.findViewById(R.id.shopping_rv_price);
            shopping_rv_number = itemView.findViewById(R.id.shopping_rv_number);
            shopping_rv_selected = itemView.findViewById(R.id.shopping_rv_selected);
            shopping_rv_adddeleteview = itemView.findViewById(R.id.shopping_rv_adddeleteview);
        }
    }

    OnClickItem onClickItem;

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void OnClick();
    }
}
