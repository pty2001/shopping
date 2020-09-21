package com.example.shopping.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.shopping.R;
import com.example.shopping.base.BaseAdapter;
import com.example.shopping.bean.HomeContentBean;

import java.util.List;

public class TopicAdapter extends BaseAdapter {
    public TopicAdapter(Context context, List data) {
        super(context, data);
    }

    @Override
    protected int getLayout() {
        return R.layout.layout_home_topic;
    }


    @Override
    protected void bindData(BaseViewHolder vh, Object data) {
        HomeContentBean.DataBean.TopicListBean topicListBean = (HomeContentBean.DataBean.TopicListBean) data;
        Glide.with(context).load(topicListBean.getItem_pic_url()).into((ImageView) vh.getViewById(R.id.img_topic));
        ((TextView) vh.getViewById(R.id.txt_topic_name)).setText(topicListBean.getTitle());

    }
}
