package com.example.shopping.ui.home;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.shopping.R;
import com.example.shopping.adapter.HomeListAdapter;
import com.example.shopping.base.BaseFragment;
import com.example.shopping.bean.HomeContentBean;
import com.example.shopping.interfaces.home.IHomeContent;
import com.example.shopping.presenter.home.HomePresenter;
import com.example.shopping.ui.shopping.ParticularsActivity;
import com.example.shopping.ui.home.title.BrandTitleActivity;
import com.example.shopping.ui.home.title.NewPublishTitleActivity;
import com.example.shopping.ui.home.title.RecommendTitleActivity;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment<IHomeContent.HomeContentPresenter> implements IHomeContent.HomeContentView {

    private RecyclerView home_rv;

    HomeListAdapter homeListAdapter;
    List<HomeContentBean.HomeListBean> list;

//    privateHomeViewModel homeViewModel;

    @Override
    protected int getLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initData() {
        presenter.getContent();
        Log.d(TAG, "initData: " + presenter.toString());
    }

    @Override
    protected IHomeContent.HomeContentPresenter initPresenter() {
        return new HomePresenter();
    }

    @Override
    protected void initView() {
        home_rv = getActivity().findViewById(R.id.home_rv);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        home_rv.setLayoutManager(gridLayoutManager);
        list = new ArrayList<>();
        homeListAdapter = new HomeListAdapter(list, getActivity());
        home_rv.setAdapter(homeListAdapter);
        homeListAdapter.bindToRecyclerView(home_rv);
        homeListAdapter.setSpanSizeLookup(new BaseQuickAdapter.SpanSizeLookup() {
            @Override
            public int getSpanSize(GridLayoutManager gridLayoutManager, int i) {
                int type = list.get(i).currentType;
                switch (type) {
                    case HomeContentBean.ITEM_TYPE_BANNER:
                    case HomeContentBean.ITEM_TYPE_TITLE:
                    case HomeContentBean.ITEM_TYPE_TITLETOP:
                    case HomeContentBean.ITEM_TYPE_HOT:
                    case HomeContentBean.ITEM_TYPE_TOPIC:
                    case HomeContentBean.ITEM_TYPE_TAB:
                        return 2;
                    case HomeContentBean.ITEM_TYPE_BRAND:
                    case HomeContentBean.ITEM_TYPE_NEW:
                    case HomeContentBean.ITEM_TYPE_CATEGORY:
                        return 1;

                }
                return 0;
            }
        });
        homeListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                int currentType = list.get(position).currentType;
                Intent intent = new Intent();
                switch (currentType) {
                    case HomeContentBean.ITEM_TYPE_TITLETOP://标题
                        String types = (String) list.get(position).data;
                        if (types.contains("品牌制造商直供")) {
                            intent.setClass(getActivity(), BrandTitleActivity.class);
                            startActivity(intent);
                        } else if (types.equals("人气推荐")) {
                            intent.setClass(getActivity(), RecommendTitleActivity.class);
                            startActivity(intent);
                        } else if (types.equals("专题精选")) {
                            intent.setClass(getActivity(), SelectionTitleActivity.class);
                            startActivity(intent);
                        }else if (types.equals("周一周四·新品首发")){
                            intent.setClass(getActivity(), NewPublishTitleActivity.class);
                            startActivity(intent);
                        }
                        break;
                    case HomeContentBean.ITEM_TYPE_BRAND://品牌
                        HomeContentBean.DataBean.BrandListBean brandListBean = (HomeContentBean.DataBean.BrandListBean) list.get(position).data;
                        intent.putExtra("brandId", brandListBean.getId());
                        intent.setClass(getActivity(), BrandDetailActivity.class);
                        startActivity(intent);
                        break;
                    case HomeContentBean.ITEM_TYPE_HOT://人气
                        HomeContentBean.DataBean.HotGoodsListBean data = (HomeContentBean.DataBean.HotGoodsListBean) list.get(position).data;
                        intent.putExtra("id", data.getId());
                        intent.setClass(getActivity(), ParticularsActivity.class);
                        startActivity(intent);
                        break;
                    case HomeContentBean.ITEM_TYPE_TOPIC://专题
                        break;
                    case HomeContentBean.ITEM_TYPE_CATEGORY://类别

                        break;
                }
            }
        });
    }

    private static final String TAG = "HomeFragment";

    @Override
    public void getContentResult(List<HomeContentBean.HomeListBean> dataBeans) {
        Log.d(TAG, "getContentResult: " + dataBeans.size());
        list.addAll(dataBeans);
        homeListAdapter.notifyDataSetChanged();
    }
}