package com.example.shopping.ui.me;

import android.graphics.drawable.BitmapDrawable;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.shopping.R;
import com.example.shopping.base.BaseActivity;
import com.example.shopping.bean.AddressBean;
import com.example.shopping.interfaces.me.IAddress;
import com.example.shopping.presenter.me.AddressPresenter;
import com.example.shopping.utils.SystemUtils;
import com.weigan.loopview.LoopView;
import com.weigan.loopview.OnItemSelectedListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddressActivity extends BaseActivity<IAddress.ICartPersenter> implements View.OnClickListener,IAddress.IAddressView {

    private ImageView address_iv_finsh;
    private EditText address_ed_name;
    private EditText address_ed_phone;
    private EditText address_ed_location;
    private EditText address_ed_detaillocation;
    private CheckBox address_cb;
    private PopupWindow mPopWindow;
    private LoopView province;
    private LoopView city;
    private LoopView area;
    private TextView txtProvince;
    private TextView txtCity;
    private TextView txtArea;
    private LinearLayout line;
    private Map<Integer, List<AddressBean.DataBean>> addressMap;
    private int curProvinceId;
    private int curCityId;
    private int curAreaId;

    @Override
    protected IAddress.ICartPersenter initPresenter() {
        return new AddressPresenter();
    }

    @Override
    protected void initData() {
        presenter.getAddress(1);
    }

    @Override
    protected void initView() {
        address_iv_finsh = findViewById(R.id.address_iv_finsh);
        address_ed_name = findViewById(R.id.address_ed_name);
        address_ed_phone = findViewById(R.id.address_ed_phone);
        address_ed_location = findViewById(R.id.address_ed_location);
        address_ed_detaillocation = findViewById(R.id.address_ed_detaillocation);
        province = findViewById(R.id.adress_province);
        city = findViewById(R.id.adress_city);
        area = findViewById(R.id.adress_area);
        txtProvince = findViewById(R.id.txt_province);
        txtCity = findViewById(R.id.txt_city);
        txtArea = findViewById(R.id.txt_area);
        line = findViewById(R.id.line);
        address_iv_finsh.setOnClickListener(this);
        address_ed_location.setOnClickListener(this);
        addressMap = new HashMap<>();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_address;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.address_iv_finsh:
                finish();
                break;
            case R.id.address_ed_location:
                initPop();
                break;
        }
    }

    private void initPop() {
        if(mPopWindow != null && mPopWindow.isShowing()){

        }else{
            View contentView = LayoutInflater.from(this).inflate(R.layout.layout_popwindow_adress, null);
            int height = SystemUtils.dp2px(this,250);
            mPopWindow = new PopupWindow(contentView, LinearLayout.LayoutParams.MATCH_PARENT,height);
            mPopWindow.setBackgroundDrawable(new BitmapDrawable());
            mPopWindow.setFocusable(false);
            mPopWindow.setOutsideTouchable(false);
            mPopWindow.setContentView(contentView);
            contentView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
            province = contentView.findViewById(R.id.adress_province);
            city = contentView.findViewById(R.id.adress_city);
            area = contentView.findViewById(R.id.adress_area);
            txtProvince = contentView.findViewById(R.id.txt_province);
            txtCity = contentView.findViewById(R.id.txt_city);
            txtArea = contentView.findViewById(R.id.txt_area);
            TextView txt_submit = contentView.findViewById(R.id.txt_submit);
            txt_submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mPopWindow.dismiss();
                    mPopWindow = null;
                }
            });
            mPopWindow.showAtLocation(line, Gravity.BOTTOM,0,0);

            //省份
            province.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AddressBean.DataBean> proviceList = addressMap.get(1); //key为1固定为省的数据
                    AddressBean.DataBean dataBean = proviceList.get(index);
                    curProvinceId = dataBean.getId();
                    presenter.getAddress(curProvinceId);
                    List<String> items = new ArrayList<>();
                    items.add("请选择");
                    city.setItems(items);
                    txtProvince.setText(dataBean.getName());
                    txtCity.setText("请选择城市");
                    txtArea.setText("请选中区域");
                }
            });

            city.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AddressBean.DataBean> cityList = addressMap.get(curProvinceId); //key省份id
                    AddressBean.DataBean dataBean = cityList.get(index);
                    curCityId = dataBean.getId();
                    presenter.getAddress(curCityId);
                    area.setItems(new ArrayList<>());
                    txtCity.setText(dataBean.getName());
                    txtArea.setText("请选中区域");
                }
            });

            area.setListener(new OnItemSelectedListener() {
                @Override
                public void onItemSelected(int index) {
                    List<AddressBean.DataBean> areaList = addressMap.get(curCityId); //key省份id
                    AddressBean.DataBean dataBean = areaList.get(index);
                    curAreaId = dataBean.getId();
                    txtArea.setText(dataBean.getName());
                }
            });

            //初始化省份的数据
            List<AddressBean.DataBean> pList = addressMap.get(1);
            if(pList == null) return;
            List<String> adresses = getAdressStrings(pList);
            if(pList == null || adresses.size() == 0){
                presenter.getAddress(1);
            }else{
                province.setItems(adresses);
                curProvinceId = pList.get(0).getId();
                txtProvince.setText(adresses.get(0));
            }

        }
    }

    private static final String TAG = "AddressActivity";
    /**
     * 提取省市区的名字
     * @param list
     * @return
     */
    private List<String> getAdressStrings(List<AddressBean.DataBean> list){
        List<String> adresses = new ArrayList<>();
        for(AddressBean.DataBean item:list){
            adresses.add(item.getName());
        }
        return adresses;
    }
    @Override
    public void getAddressReturn(AddressBean result) {
        Log.d(TAG, "getAddressReturn: "+result.toString());
        List<AddressBean.DataBean> list = null;
        int type = 0;
        for(AddressBean.DataBean item:result.getData()){
            int key = item.getParent_id();
            list = addressMap.get(key);
            if(list == null){
                list = new ArrayList<>();
                addressMap.put(key,list);
            }
            boolean bool = hasList(item.getId(),list);
            if(!bool) list.add(item);
            if(type == 0){
                type = item.getType();
            }
        }
        if(list == null) return;
        List<String> adresses = getAdressStrings(list);
        if(type == 1){
            //刷新省的数据
            if(province != null){
                curProvinceId = list.get(0).getId();
                txtProvince.setText(list.get(0).getName());
                province.setItems(adresses);
            }
        }else if(type == 2){
            //刷新市的数据
            if(city != null){
                curCityId = list.get(0).getId();
                txtCity.setText(list.get(0).getName());
                city.setItems(adresses);
            }
        }else{
            //区
            if(area != null){
                curAreaId = list.get(0).getId();
                txtArea.setText(list.get(0).getName());
                area.setItems(adresses);
            }
        }
    }
    /**
     * 判断当前的地址列表中是否有这个地址
     * @param id
     * @param list
     * @return
     */
    private boolean hasList(int id, List<AddressBean.DataBean> list){
        boolean bool = false;
        for(AddressBean.DataBean item:list){
            if(item.getId() == id){
                bool = true;
                break;
            }
        }
        return bool;
    }
}