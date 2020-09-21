package com.example.shopping.presenter.home;


import android.util.Log;

import com.example.shopping.base.BasePresenter;
import com.example.shopping.bean.HomeContentBean;
import com.example.shopping.common.CommonSubscriber;
import com.example.shopping.interfaces.home.IHomeContent;
import com.example.shopping.model.HttpManager;
import com.example.shopping.utils.RxUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.functions.Function;

public class HomePresenter extends BasePresenter<IHomeContent.HomeContentView> implements IHomeContent.HomeContentPresenter {
    private static final String TAG = "HomePresenter";
    @Override
    public void getContent() {
        addSubscribe(HttpManager.getInstance().getShoppingApi().getHomeContent()
                .compose(RxUtils.<HomeContentBean>rxScheduler())
                //数据加工 把HomeBean转换成List<HomeListBean>
                .map(new Function<HomeContentBean, List<HomeContentBean.HomeListBean>>() {
                    @Override
                    public List<HomeContentBean.HomeListBean> apply(HomeContentBean homeBean) throws Exception {
                        List<HomeContentBean.HomeListBean> list = new ArrayList<>();
                        //第一个对象的封装 Banner
                        HomeContentBean.HomeListBean banner = new HomeContentBean.HomeListBean();
                        banner.currentType = HomeContentBean.ITEM_TYPE_BANNER;
                        banner.data = homeBean.getData().getBanner();
                        list.add(banner);
                        //导航的封装
                        HomeContentBean.HomeListBean tab = new HomeContentBean.HomeListBean();
                        tab.currentType = HomeContentBean.ITEM_TYPE_TAB;
                        tab.data = homeBean.getData().getChannel();
                        list.add(tab);
                        //封装带top边距的标题
                        HomeContentBean.HomeListBean title1 = new HomeContentBean.HomeListBean();
                        title1.currentType = HomeContentBean.ITEM_TYPE_TITLETOP;
                        title1.data = "品牌制造商直供";
                        list.add(title1);
                        //封装品牌制造商直供的列表数据
                        for (int i = 0; i < homeBean.getData().getBrandList().size(); i++) {
                            HomeContentBean.HomeListBean brand = new HomeContentBean.HomeListBean();
                            brand.currentType = HomeContentBean.ITEM_TYPE_BRAND;
                            brand.data = homeBean.getData().getBrandList().get(i);
                            list.add(brand);
                        }
                        //新品首发标题
                        HomeContentBean.HomeListBean title2 = new HomeContentBean.HomeListBean();
                        title2.currentType = HomeContentBean.ITEM_TYPE_TITLETOP;
                        title2.data = "周一周四·新品首发";
                        list.add(title2);
                        //新品首发数据封装
                        for (int i = 0; i < homeBean.getData().getNewGoodsList().size(); i++) {
                            HomeContentBean.HomeListBean brand = new HomeContentBean.HomeListBean();
                            brand.currentType = HomeContentBean.ITEM_TYPE_NEW;
                            brand.data = homeBean.getData().getNewGoodsList().get(i);
                            list.add(brand);
                        }
                        //人气推荐
                        HomeContentBean.HomeListBean title3 = new HomeContentBean.HomeListBean();
                        title3.currentType = HomeContentBean.ITEM_TYPE_TITLETOP;
                        title3.data = "人气推荐";
                        list.add(title3);
                        //人气推荐数据
                        for (int i = 0; i < homeBean.getData().getHotGoodsList().size(); i++) {
                            HomeContentBean.HomeListBean brand = new HomeContentBean.HomeListBean();
                            brand.currentType = HomeContentBean.ITEM_TYPE_HOT;
                            brand.data = homeBean.getData().getHotGoodsList().get(i);
                            list.add(brand);
                        }
                        //专题精选
                        HomeContentBean.HomeListBean title4 = new HomeContentBean.HomeListBean();
                        title4.currentType = HomeContentBean.ITEM_TYPE_TITLETOP;
                        title4.data = "专题精选";
                        list.add(title4);
                        //人气推荐数据
                        HomeContentBean.HomeListBean brand = new HomeContentBean.HomeListBean();
                        brand.currentType = HomeContentBean.ITEM_TYPE_TOPIC;
                        brand.data = homeBean.getData().getTopicList();
                        list.add(brand);

                        //分类
                        for (HomeContentBean.DataBean.CategoryListBean item:homeBean.getData().getCategoryList()){
                            HomeContentBean.HomeListBean classifyTitle = new HomeContentBean.HomeListBean();
                            classifyTitle.currentType = HomeContentBean.ITEM_TYPE_TITLETOP;
                            classifyTitle.data = item.getName();
                            list.add(classifyTitle);
                            for (HomeContentBean.DataBean.CategoryListBean.GoodsListBean classfly:item.getGoodsList()){
                                HomeContentBean.HomeListBean classifys = new HomeContentBean.HomeListBean();
                                classifys.currentType = HomeContentBean.ITEM_TYPE_CATEGORY;
                                classifys.data = classfly;
                                list.add(classifys);
                            }
                        }
                        return list;
                    }
                })
                .subscribeWith(new CommonSubscriber<List<HomeContentBean.HomeListBean>>(mView) {
                    @Override
                    public void onNext(List<HomeContentBean.HomeListBean> list) {
                        mView.getContentResult(list);
                    }
                }));
    }

}
