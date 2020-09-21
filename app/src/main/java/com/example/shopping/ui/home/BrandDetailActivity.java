package com.example.shopping.ui.home;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.base.BaseActivity;
import com.example.shopping.bean.BrandDetailBean;
import com.example.shopping.interfaces.home.IBrandDetail;
import com.example.shopping.presenter.home.BrandDetailPresenter;

public class BrandDetailActivity extends BaseActivity<IBrandDetail.BrandDetailPresenter> implements IBrandDetail.BrandDetailView , View.OnClickListener {

    private ImageView branddetail_iv_finsh;
    private ImageView branddetail_iv_img;
    private TextView branddetail_iv_title;
    private TextView branddetail_iv_content;

    @Override
    protected IBrandDetail.BrandDetailPresenter initPresenter() {
        return new BrandDetailPresenter();
    }

    @Override
    protected void initData() {
        int brandId = getIntent().getIntExtra("brandId", 0);
        presenter.getBrandDetail(brandId);
    }

    @Override
    protected void initView() {
        branddetail_iv_finsh = findViewById(R.id.branddetail_iv_finsh);
        branddetail_iv_img = findViewById(R.id.branddetail_iv_img);
        branddetail_iv_title = findViewById(R.id.branddetail_iv_title);
        branddetail_iv_content = findViewById(R.id.branddetail_iv_content);
        branddetail_iv_finsh.setOnClickListener(this);


    }

    @Override
    protected int getLayout() {
        return R.layout.activity_brand_detail;
    }

    @Override
    public void getBrandDetailResult(BrandDetailBean brandDetailBean) {
        BrandDetailBean.DataBean.BrandBean brand = brandDetailBean.getData().getBrand();
        Glide.with(this).load(brand.getList_pic_url()).into(branddetail_iv_img);
        branddetail_iv_title.setText(brand.getName());
        branddetail_iv_content.setText(brand.getSimple_desc());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.branddetail_iv_finsh:
                finish();
                break;
        }
    }
}