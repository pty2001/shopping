package com.example.shopping;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.example.shopping.ui.classfly.ClassflyFragment;
import com.example.shopping.ui.home.HomeFragment;
import com.example.shopping.ui.me.MeFragment;
import com.example.shopping.ui.shopping.ShoppingFragment;
import com.example.shopping.ui.special.SpecialFragment;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private LinearLayout line;
    private TabLayout main_tb;
    private HomeFragment homeFragment;
    private SpecialFragment specialFragment;
    private ClassflyFragment classflyFragment;
    private ShoppingFragment shoppingFragment;
    private MeFragment meFragment;
    private FragmentManager supportFragmentManager;
    private FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        line = (LinearLayout) findViewById(R.id.line);
        main_tb = (TabLayout) findViewById(R.id.main_tb);
        supportFragmentManager = getSupportFragmentManager();


        homeFragment = new HomeFragment();
        specialFragment = new SpecialFragment();
        classflyFragment = new ClassflyFragment();
        shoppingFragment = new ShoppingFragment();
        meFragment = new MeFragment();

        main_tb.addTab(main_tb.newTab().setIcon(R.drawable.home_item).setText("首页"), 0);
        main_tb.addTab(main_tb.newTab().setIcon(R.drawable.special_item).setText("专题"), 1);
        main_tb.addTab(main_tb.newTab().setIcon(R.drawable.classify_item).setText("分类"), 2);
        main_tb.addTab(main_tb.newTab().setIcon(R.drawable.shopping_item).setText("购物车"), 3);
        main_tb.addTab(main_tb.newTab().setIcon(R.drawable.me_item).setText("我的"), 4);
        main_tb.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                FragmentTransaction fragmentTransaction = supportFragmentManager.beginTransaction();
                switch (tab.getPosition()) {
                    case 0:
                        if (!homeFragment.isAdded()) {
                            fragmentTransaction.add(R.id.line, homeFragment).hide(specialFragment).hide(classflyFragment).hide(shoppingFragment).hide(meFragment);
                        }
                        fragmentTransaction.show(homeFragment).hide(specialFragment).hide(classflyFragment).hide(shoppingFragment).hide(meFragment);
                        fragmentTransaction.commit();
                        break;
                    case 1:
                        if (!specialFragment.isAdded()) {
                            fragmentTransaction.add(R.id.line, specialFragment).hide(homeFragment).hide(classflyFragment).hide(shoppingFragment).hide(meFragment);
                        }
                        fragmentTransaction.show(specialFragment).hide(homeFragment).hide(classflyFragment).hide(shoppingFragment).hide(meFragment);
                        fragmentTransaction.commit();
                        break;
                    case 2:
                        if (!classflyFragment.isAdded()) {
                            fragmentTransaction.add(R.id.line, classflyFragment).hide(homeFragment).hide(specialFragment).hide(shoppingFragment).hide(meFragment);
                        }
                        fragmentTransaction.show(classflyFragment).hide(homeFragment).hide(specialFragment).hide(shoppingFragment).hide(meFragment);
                        fragmentTransaction.commit();
                        break;
                    case 3:
                        if (!shoppingFragment.isAdded()) {
                            fragmentTransaction.add(R.id.line, shoppingFragment).hide(homeFragment).hide(specialFragment).hide(classflyFragment).hide(meFragment);
                        }
                        fragmentTransaction.show(shoppingFragment).hide(homeFragment).hide(specialFragment).hide(classflyFragment).hide(meFragment);
                        fragmentTransaction.commit();
                        break;
                    case 4:
                        if (!meFragment.isAdded()) {
                            fragmentTransaction.add(R.id.line, meFragment).hide(homeFragment).hide(specialFragment).hide(classflyFragment).hide(shoppingFragment);
                        }
                        fragmentTransaction.show(meFragment).hide(homeFragment).hide(specialFragment).hide(classflyFragment).hide(shoppingFragment);
                        fragmentTransaction.commit();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void show() {
        main_tb.getTabAt(3).select();
        supportFragmentManager.beginTransaction();
        fragmentTransaction.show(shoppingFragment).hide(homeFragment).hide(specialFragment).hide(classflyFragment).hide(meFragment).commit();
    }
}