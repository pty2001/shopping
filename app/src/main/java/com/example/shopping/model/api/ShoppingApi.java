package com.example.shopping.model.api;


import com.example.shopping.bean.AddCartBean;
import com.example.shopping.bean.AddressBean;
import com.example.shopping.bean.BrandDetailBean;
import com.example.shopping.bean.CartBean;
import com.example.shopping.bean.DeleteCardBean;
import com.example.shopping.bean.HomeContentBean;
import com.example.shopping.bean.LoginBean;
import com.example.shopping.bean.ParticularsBean;
import com.example.shopping.bean.RegistBean;

import io.reactivex.Flowable;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ShoppingApi {
    //首页
    @GET("index")
    Flowable<HomeContentBean> getHomeContent();

    //人气推荐列表详情
    @GET("goods/detail")
    Flowable<ParticularsBean> getParticulars(@Query("id")int id);

    //品牌制造列表详情
    @GET("brand/detail")
    Flowable<BrandDetailBean> getBrandDetail(@Query("id")int id);

    //购物车添加商品
    @POST("cart/add")
    @FormUrlEncoded
    Flowable<AddCartBean> getAddCart(@Field("goodsId") int goodsId,@Field("number") int number, @Field("productId") int productId);

    //购物车删除商品
    @POST("cart/delete")
    @FormUrlEncoded
    Flowable<DeleteCardBean> getDeleteCart(@Field("productIds") String productIds);

    //购物车数据
    @GET("cart/index")
    Flowable<CartBean> getCart();

    //登录
    @POST("auth/login")
    Flowable<LoginBean> getLogin(@Field("username")String username,@Field("password")String password);

    //注册
    @POST("auth/register")
    Flowable<RegistBean> getRegist(@Field("nickname")String nickname,@Field("password")String password);

    //地区选择
    @GET("region/list")
    Flowable<AddressBean> getAddress(@Query("id")int id);
}
