package com.example.shopping.common;

import android.content.Context;
import android.content.res.TypedArray;

import android.util.AttributeSet;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.shopping.R;

/**
 * Created by WuXirui
 * Create Time: 2017/11/1
 * Description:
 */
 
public class AddDeleteView extends LinearLayout implements View.OnClickListener{
    private Context context;
    private OnAddDelClickListener listener;
    private TextView tv_num;
    private int num = 1;

    private int min = 1;
    private int max = 999;
    private TextView tv_delete;
    private TextView tv_add;


    public void setOnAddDelClickListener(OnAddDelClickListener listener) {
        if (listener != null) {
            this.listener = listener;
        }
    }
    public void setValue(int num){
        this.num = num;
        tv_num.setText(String.valueOf(this.num));
    }
    interface OnAddDelClickListener{
        void onAddClick(View v);
        void onDelClick(View v);
    }
 
    public AddDeleteView(Context context) {
        this(context,null);
        this.context = context;
    }
 
    public AddDeleteView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
        this.context = context;
    }
 
    public AddDeleteView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }
    public void initView(){
        View.inflate(context, R.layout.adddelete_view,this);
        tv_delete = findViewById(R.id.tv_delete);
        tv_add = findViewById(R.id.tv_add);
        tv_num = findViewById(R.id.tv_num);
        if (tv_delete!=null&&tv_add!=null&tv_num!=null){
            tv_delete.setOnClickListener(this);
            tv_add.setOnClickListener(this);
        }
    }
    public void initView(int min,int max){
        this.min = min;
        this.max = max;
        initView();
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tv_delete:
                num--;
                if (num<1){
                    num=1;
                }else {
                    tv_num.setText(String.valueOf(num));
                    if (onClickLinter!=null){
                        onClickLinter.onClicks(num);
                    }
                }
                break;
            case R.id.tv_add:
                num++;
                if (num>max){
                    num = max;
                }else {
                    if (onClickLinter!=null){
                        onClickLinter.onClicks(num);
                    }
                }
                tv_num.setText(String.valueOf(num));
                break;
        }
    }


    OnClickLinter onClickLinter;
    public void setClickLinter(OnClickLinter onClickLinter) {
        this.onClickLinter = onClickLinter;
    }

    public interface OnClickLinter{
        void onClicks(int num);
    }
}