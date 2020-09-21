package com.example.shopping.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.shopping.R;
import com.example.shopping.bean.HomeContentBean;
import com.example.shopping.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import java.util.ArrayList;
import java.util.List;

public class HomeListAdapter extends BaseMultiItemQuickAdapter<HomeContentBean.HomeListBean, BaseViewHolder> {

    private Context context;
    private String priceWord;
    private TopicAdapter topicAdapter;

    public HomeListAdapter(List<HomeContentBean.HomeListBean> data, Context context) {
        super(data);
        this.context = context;
        priceWord = context.getString(R.string.word_price_brand);
        //做UI绑定
        addItemType(HomeContentBean.ITEM_TYPE_BANNER, R.layout.layout_home_banner);
        addItemType(HomeContentBean.ITEM_TYPE_TAB, R.layout.layout_home_tab);
        addItemType(HomeContentBean.ITEM_TYPE_TITLETOP, R.layout.layout_title_top);
        addItemType(HomeContentBean.ITEM_TYPE_BRAND, R.layout.layout_home_brand);
        addItemType(HomeContentBean.ITEM_TYPE_TITLETOP, R.layout.layout_title_top);
        addItemType(HomeContentBean.ITEM_TYPE_NEW, R.layout.layout_home_newgood);
        addItemType(HomeContentBean.ITEM_TYPE_TITLETOP, R.layout.layout_title_top);
        addItemType(HomeContentBean.ITEM_TYPE_HOT, R.layout.layout_home_hot);
        addItemType(HomeContentBean.ITEM_TYPE_TITLETOP, R.layout.layout_title_top);
        addItemType(HomeContentBean.ITEM_TYPE_TOPIC, R.layout.layout_home_topiclist);
        addItemType(HomeContentBean.ITEM_TYPE_TITLETOP, R.layout.layout_title_top);
        addItemType(HomeContentBean.ITEM_TYPE_CATEGORY, R.layout.layout_home_categorylist);
    }

    /**
     * 主要刷新数据，绑定数据  onbindviewholder
     * @param helper viewholder 界面
     * @param item   数据
     */
    @Override
    protected void convert(BaseViewHolder helper, HomeContentBean.HomeListBean item) {
        switch (item.getItemType()) {
            case HomeContentBean.ITEM_TYPE_TITLE:
                updateTitle(helper, (String) item.data);
                break;
            case HomeContentBean.ITEM_TYPE_TITLETOP:
                updateTitle(helper, (String) item.data);
                break;
            case HomeContentBean.ITEM_TYPE_BANNER:
                updateBanner(helper, (List<HomeContentBean.DataBean.BannerBean>) item.data);
                break;
            case HomeContentBean.ITEM_TYPE_TAB:
                updateTab(helper, (List<HomeContentBean.DataBean.ChannelBean>) item.data);
                break;
            case HomeContentBean.ITEM_TYPE_BRAND:
                updateBrand(helper, (HomeContentBean.DataBean.BrandListBean) item.data);
                break;
            case HomeContentBean.ITEM_TYPE_NEW:
                updateNewGood(helper, (HomeContentBean.DataBean.NewGoodsListBean) item.data);
                break;
            case HomeContentBean.ITEM_TYPE_HOT:
                udpateHot(helper, (HomeContentBean.DataBean.HotGoodsListBean) item.data);
                break;
            case HomeContentBean.ITEM_TYPE_TOPIC:
                updateTopic(helper, (List<HomeContentBean.DataBean.TopicListBean>) item.data);
                break;
            case HomeContentBean.ITEM_TYPE_CATEGORY:
                updatecategory(helper, (HomeContentBean.DataBean.CategoryListBean.GoodsListBean) item.data);
                break;

        }
    }

    private void updatecategory(BaseViewHolder helper, HomeContentBean.DataBean.CategoryListBean.GoodsListBean data) {
        Glide.with(context).load(data.getList_pic_url()).into((ImageView) helper.getView(R.id.category_iv_img));
        helper.setText(R.id.category_tv_name, data.getName());
        helper.setText(R.id.category_tv_money, "￥" + (int) data.getRetail_price());
    }

    /**
     * 刷新title
     * @param viewHolder
     * @param title
     */
    private void updateTitle(BaseViewHolder viewHolder, String title) {
        viewHolder.setText(R.id.txt_title, title);
    }

    /**
     * 刷新banner
     * @param viewHolder
     * @param bannsers
     */
    private void updateBanner(BaseViewHolder viewHolder, List<HomeContentBean.DataBean.BannerBean> bannsers) {
        Banner banner = viewHolder.getView(R.id.banner);
        List<String> imgs = new ArrayList<>();
        for (HomeContentBean.DataBean.BannerBean bannerBean : bannsers){
            imgs.add(bannerBean.getImage_url());
        }
        banner.setAdapter(new BannerImageAdapter<String>(imgs) {


            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                Glide.with(holder.itemView)
                        .load(data)
                        .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                        .into(holder.imageView);
            }

        }).setIndicator(new CircleIndicator(context));
    }

    /**
     * 刷新channel
     * @param viewHolder
     * @param channels
     */
    private void updateTab(BaseViewHolder viewHolder, List<HomeContentBean.DataBean.ChannelBean> channels) {
        LinearLayout layoutChannels = viewHolder.getView(R.id.layout_tab);
        //只让当前的布局内容添加一次 only one
        if (layoutChannels.getChildCount() == 0) {
            for (HomeContentBean.DataBean.ChannelBean item : channels) {
                String name = item.getName();
                String icon_url = item.getIcon_url();
                TextView tab = new TextView(context);
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1);
                int size = SystemUtils.dp2px(context, 5);
                tab.setTextSize(size);
                tab.setGravity(Gravity.CENTER);
                Drawable icon = context.getDrawable(R.mipmap.ic_launcher);
                icon.setBounds(0, 0, 80, 80);
                tab.setCompoundDrawables(null, icon, null, null);
                openBidevent(icon_url,tab,name);
                tab.setLayoutParams(params);
                layoutChannels.addView(tab);
            }
        }
    }
    private void openBidevent(String icon_url, final TextView textView, String name) {
        Glide.with(context).asBitmap().load(icon_url).into(new SimpleTarget<Bitmap>(){
            @Override
            public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                BitmapDrawable drawable= new BitmapDrawable(context.getResources(), resource);
                /// 这一步必须要做,否则不会显示.
                drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
                textView.setCompoundDrawables(null,drawable,null,null);
            }
        });
        textView.setText(name);
    }
    /**
     * 刷新品牌
     * @param viewHolder
     * @param brands
     */
    private void updateBrand(BaseViewHolder viewHolder, HomeContentBean.DataBean.BrandListBean brands) {
        if (!TextUtils.isEmpty(brands.getNew_pic_url())) {
            Glide.with(context).load(brands.getNew_pic_url()).into((ImageView) viewHolder.getView(R.id.img_brand));
        }
        viewHolder.setText(R.id.txt_brand_name, brands.getName());
        String price = priceWord.replace("$", String.valueOf(brands.getFloor_price()));
        viewHolder.setText(R.id.txt_brand_price, price);
    }

    /**
     * 刷新新品数据
     * @param viewHolder
     * @param newGoods
     */
    private void updateNewGood(BaseViewHolder viewHolder, HomeContentBean.DataBean.NewGoodsListBean newGoods) {
        Glide.with(context).load(newGoods.getList_pic_url()).into((ImageView) viewHolder.getView(R.id.img_newgood));
        viewHolder.setText(R.id.txt_newgood_name, newGoods.getName());
        viewHolder.setText(R.id.txt_newgood_price, "￥" + (int) newGoods.getRetail_price());

    }
    /**
     * 刷新人气数据
     */
    private void udpateHot(BaseViewHolder viewHolder, HomeContentBean.DataBean.HotGoodsListBean hotGoods) {
        Glide.with(context).load(hotGoods.getList_pic_url()).into((ImageView) viewHolder.getView(R.id.img_hot));
        viewHolder.setText(R.id.txt_hot_name, hotGoods.getName());
        viewHolder.setText(R.id.txt_hot_title, hotGoods.getGoods_brief());
        viewHolder.setText(R.id.txt_hot_price, "￥" + (int) hotGoods.getRetail_price());
    }

    /**
     * 刷新专题
     * @param viewHolder
     * @param topicGoods
     */
    private void updateTopic(BaseViewHolder viewHolder, List<HomeContentBean.DataBean.TopicListBean> topicGoods) {
        RecyclerView recyclerView = viewHolder.getView(R.id.recyclerviewTopic);
        if (topicAdapter == null) {
            topicAdapter = new TopicAdapter(context, topicGoods);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(topicAdapter);
        } else if (recyclerView.getAdapter() == null) {
            recyclerView.setAdapter(topicAdapter);
        }
    }


}
