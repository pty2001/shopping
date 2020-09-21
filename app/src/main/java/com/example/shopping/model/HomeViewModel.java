//package com.example.shopping.model;
//
//
//import android.text.TextUtils;
//
//import androidx.lifecycle.LiveData;
//import androidx.lifecycle.MutableLiveData;
//import androidx.lifecycle.ViewModel;
//
//import com.example.shopping.bean.HomeContentBean;
//import com.example.shopping.common.CommonSubscriber;
//import com.example.shopping.utils.RxUtils;
//
//public class HomeViewModel extends ViewModel {
//    //MutableLvieData 表示带有生命周期
//    private MutableLiveData<String> mText;
//    private MutableLiveData<HomeContentBean> homeBean;
//
//    public HomeViewModel() {
//        mText = new MutableLiveData<>();
//        mText.setValue("This is home fragment");
//        homeBean = new MutableLiveData<>();
//    }
//
//    public LiveData<String> getText() {
//        return mText;
//    }
//
//
//    public LiveData<HomeContentBean> getHomeBean(){
//        return homeBean;
//    }
//
//    /**
//     * 网络数据加载
//     */
//    public void getHomeData(){
//
//        HttpManager.getInstance().getShoppingApi().getHomeContent()
//                .compose(RxUtils.rxScheduler())
//                .subscribeWith(new CommonSubscriber<HomeContentBean>(null){
//
//                    @Override
//                    public void onNext(HomeContentBean result) {
//                        homeBean.setValue(result);
//                    }
//                });
//
//        /*Imall imall = HttpManager.getInstance().getMallApi();
//        Call<ResponseBody> call = imall.getHomeContent();
//        call.enqueue(new Callback<ResponseBody>() {
//            @Override
//            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {H
//                try {
//                    String result = response.body().string().toString();
//                    if(!TextUtils.isEmpty(result)){
//                        //修改被观察的数据对象的值
//                        homeBean.setValue(new Gson().fromJson(result, (Type) HomeBean.class));
//                    }
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void onFailure(Call<ResponseBody> call, Throwable t) {
//
//            }
//        });*/
//    }
//}