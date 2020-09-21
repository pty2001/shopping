package com.example.shopping.ui.shopping;

import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.example.shopping.R;
import com.example.shopping.adapter.ProblemAdapter;
import com.example.shopping.base.BaseActivity;
import com.example.shopping.bean.AddCartBean;
import com.example.shopping.bean.ParticularsBean;
import com.example.shopping.common.AddDeleteView;
import com.example.shopping.interfaces.shopping.ICart;
import com.example.shopping.presenter.shopping.CartGoodPresenter;
import com.example.shopping.utils.SystemUtils;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

public class ParticularsActivity extends BaseActivity<ICart.AddCartPresenter> implements ICart.AddCartView,View.OnClickListener {

    private Banner particulars_banner;
    private WebView particulars_webview;
    private RecyclerView particulars_rv;
    private LinearLayout particulars_liner_img;
    private LinearLayout particulars_liner_title;
    private RelativeLayout relaytive;
    private PopupWindow popupWindow;
    private TextView particulars_shopping_car;
    private ImageView particulars_iv_cart;
    private LinearLayout line1;
    private TextView particulars_tv_number;
    private int currentNum;
    ParticularsBean particularsBeans;
    private String html = "<html>\n" +
            "            <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\"/>\n" +
            "            <head>\n" +
            "                <style>\n" +
            "                    p{\n" +
            "                        margin:0px;\n" +
            "                    }\n" +
            "                    img{\n" +
            "                        width:100%;\n" +
            "                        height:auto;\n" +
            "                    }\n" +
            "                </style>\n" +
            "            </head>\n" +
            "            <body>\n" +
            "                $\n" +
            "            </body>\n" +
            "        </html>";
    private ProblemAdapter problemAdapter;
    private List<ParticularsBean.DataBeanX.IssueBean> issueBeans;

    @Override
    protected ICart.AddCartPresenter initPresenter() {
        return new CartGoodPresenter();
    }

    @Override
    protected void initData() {
        int id = getIntent().getIntExtra("id", 0);
        presenter.getAddCart(id);

    }

    @Override
    protected void initView() {
        particulars_banner = findViewById(R.id.particulars_banner);
        particulars_webview = findViewById(R.id.particulars_webview);
        particulars_rv = findViewById(R.id.particulars_rv);
        particulars_liner_img = findViewById(R.id.particulars_liner_img);
        particulars_liner_title = findViewById(R.id.particulars_liner_title);
        particulars_shopping_car = findViewById(R.id.particulars_shopping_car);
        particulars_iv_cart = findViewById(R.id.particulars_iv_cart);
        line1 = findViewById(R.id.line1);
        particulars_tv_number = findViewById(R.id.particulars_tv_number);
        particulars_iv_cart.setOnClickListener(this);
        particulars_shopping_car.setOnClickListener(this);
        relaytive = findViewById(R.id.relaytive);
        relaytive.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                initPop();
            }
        });

        particulars_rv.setLayoutManager(new LinearLayoutManager(this));
        issueBeans = new ArrayList<>();
        problemAdapter = new ProblemAdapter(issueBeans, this);
        particulars_rv.setAdapter(problemAdapter);

    }

    private void initPop() {
        if (popupWindow!=null){

        }else {
            View inflate = LayoutInflater.from(ParticularsActivity.this).inflate(R.layout.num_pop_view, null);
            AddDeleteView pop_adddeleteview_view = inflate.findViewById(R.id.pop_adddeleteview_view);
            ImageView pop_iv_close = inflate.findViewById(R.id.pop_iv_close);
            int height = SystemUtils.dp2px(ParticularsActivity.this, 200);
            popupWindow = new PopupWindow(inflate,LinearLayout.LayoutParams.MATCH_PARENT,height);
            backgroundAlpha(0.5f);
            popupWindow.setFocusable(false);
            popupWindow.setOutsideTouchable(false);
            popupWindow.setContentView(inflate);
            pop_iv_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    popupWindow.dismiss();
                    popupWindow = null;
                    backgroundAlpha(1f);
                }
            });
            int[] pt = new int[2];
            line1.getLocationInWindow(pt);
            popupWindow.showAtLocation(line1, Gravity.NO_GRAVITY,0,pt[1]-height);
            pop_adddeleteview_view.initView();
            pop_adddeleteview_view.setClickLinter(new AddDeleteView.OnClickLinter() {
                @Override
                public void onClicks(int num) {
                    currentNum = num;
                }
            });

        }
    }

    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
    }
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_particulars;
    }
    @Override
    public void getAddCartResult(ParticularsBean particularsBean) {
        particularsBeans = particularsBean;
        updateBanner(particularsBean.getData().getGallery());
        updateDetailInfo(particularsBean.getData().getInfo());
        issueBeans.addAll(particularsBean.getData().getIssue());
        problemAdapter.notifyDataSetChanged();
        //评论图片
        List<ParticularsBean.DataBeanX.CommentBean.DataBean.PicListBean> pic_list = particularsBean.getData().getComment().getData().getPic_list();
        for (ParticularsBean.DataBeanX.CommentBean.DataBean.PicListBean picListBean:pic_list){
            View inflate = LayoutInflater.from(this).inflate(R.layout.comment_item, null);
            ImageView comment_iv_img = inflate.findViewById(R.id.comment_iv_img);
            String pic_url = picListBean.getPic_url();
            Glide.with(this).load(pic_url).into(comment_iv_img);
            particulars_liner_img.addView(inflate);
        }
        //商品参数
        List<ParticularsBean.DataBeanX.AttributeBean> attribute = particularsBean.getData().getAttribute();
        for (ParticularsBean.DataBeanX.AttributeBean attributeBean : attribute){
            View inflate = LayoutInflater.from(this).inflate(R.layout.commodity_item, null);
            TextView commodity_tv_title = inflate.findViewById(R.id.commodity_tv_title);
            TextView commodity_tv_content = inflate.findViewById(R.id.commodity_tv_content);
            commodity_tv_title.setText(attributeBean.getName());
            commodity_tv_content.setText(attributeBean.getValue());
            particulars_liner_title.addView(inflate);
        }
    }

    private static final String TAG = "ParticularsActivity";
    @Override
    public void addCartInfoReturn(AddCartBean result) {
        Log.d(TAG, "addCartInfoReturn: "+result.getData().getCartTotal().getGoodsCount());
        int goodsCount = result.getData().getCartTotal().getGoodsCount();
        particulars_tv_number.setText(String.valueOf(goodsCount));
    }

    private void updateDetailInfo(ParticularsBean.DataBeanX.InfoBean info) {
        if(!TextUtils.isEmpty(info.getGoods_desc())){
            String h5 = info.getGoods_desc();
            html = html.replace("$",h5);
            particulars_webview.loadDataWithBaseURL("about:blank",html,"text/html","utf-8",null);
        }
    }

    private void updateBanner(List<ParticularsBean.DataBeanX.GalleryBean> gallery) {
        if (particulars_banner.getTag() == null || (int) particulars_banner.getTag() == 0) {
            List<String> imgs = new ArrayList<>();
            for (ParticularsBean.DataBeanX.GalleryBean item : gallery) {
                imgs.add(item.getImg_url());
            }
            particulars_banner.setTag(1);
            particulars_banner.setAdapter(new BannerImageAdapter<String>(imgs) {
                @Override
                public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                    Glide.with(holder.itemView)
                            .load(data)
                            .apply(RequestOptions.bitmapTransform(new RoundedCorners(30)))
                            .into(holder.imageView);
                }
            }).setIndicator(new CircleIndicator(this));
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.particulars_iv_cart:
                setResult(100);
                finish();
                break;
            case R.id.particulars_shopping_car:
                addCart();
                break;
        }
    }

    private void addCart() {
        if (popupWindow!=null&&popupWindow.isShowing()){
            if (particularsBeans!=null&&particularsBeans.getData().getProductList().size()>0){
                int goodsId = particularsBeans.getData().getProductList().get(0).getGoods_id();
                int id = particularsBeans.getData().getProductList().get(0).getId();
                    presenter.addCart(goodsId,currentNum,id);
                popupWindow.dismiss();
                popupWindow= null;
            }
        }else {
            initPop();
        }
    }


}