package com.wencai.demo.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.wencai.demo.R;
import com.wencai.demo.fragments.CommonFragment;
import com.wencai.demo.fragments.MainFragment;
import com.wencai.demo.fragments.SettingsFragment;
import com.wencai.demo.views.BottomIcon;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final int INDEX_MAIN_FRAGMENT = 0;
    private static final int INDEX_SHOP_FRAGMENT = 1;
    private static final int INDEX_FAVOR_FRAGMENT = 2;
    private static final int INDEX_SETTING_FRAGMENT = 3;

    private static final String COLOR_DEFAULT = "#707070";
    private static final String COLOR_SELECTED = "#00bb9c";

    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private SettingsFragment mSettingsFragment;
    private CommonFragment mCommonFragment;

    private BottomIcon mButton1;
    private BottomIcon mButton2;
    private BottomIcon mButton3;
    private BottomIcon mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initFragment();
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        updateChangedView(INDEX_MAIN_FRAGMENT);
    }

    private void updateChangedView(int index) {
        changeButtonImage(index);
        changeFragmentView(index);
    }

    private void changeFragmentView(int index) {
        FragmentTransaction transaction = mFragmentManager.beginTransaction();
        if (transaction == null) {
            return;
        }

        Fragment fragment = null;
        switch (index) {
            case INDEX_MAIN_FRAGMENT: {
                if (mMainFragment == null) {
                    mMainFragment = new MainFragment();
                    transaction.add(R.id.main_fragment, mMainFragment, index + "");
                }
                fragment = mMainFragment;
                break;
            }
            case INDEX_SETTING_FRAGMENT: {
                if (mSettingsFragment == null) {
                    mSettingsFragment = new SettingsFragment();
                    transaction.add(R.id.main_fragment, mSettingsFragment, index + "");
                }
                fragment = mSettingsFragment;
                break;
            }
            case INDEX_SHOP_FRAGMENT:
            case INDEX_FAVOR_FRAGMENT:{
                if (mCommonFragment == null) {
                    mCommonFragment = new CommonFragment();
                    transaction.add(R.id.main_fragment, mCommonFragment, index + "");
                }
                fragment = mCommonFragment;
                break;
            }
        }

        if (fragment == null) {
            return;
        }

        hideAllFragment(transaction);
        transaction.show(fragment);
        transaction.commit();
    }

    private void hideAllFragment(FragmentTransaction transaction) {
        if (transaction == null) {
            return;
        }

        if (mMainFragment != null) {
            transaction.hide(mMainFragment);
        }
        if (mSettingsFragment != null) {
            transaction.hide(mSettingsFragment);
        }
        if (mCommonFragment != null) {
            transaction.hide(mCommonFragment);
        }
    }

    private void resetBottomIcon() {
        mButton1.setTextColor(COLOR_DEFAULT);
        mButton2.setTextColor(COLOR_DEFAULT);
        mButton3.setTextColor(COLOR_DEFAULT);
        mButton4.setTextColor(COLOR_DEFAULT);
        mButton1.setImageResource(R.mipmap.home);
        mButton2.setImageResource(R.mipmap.shop);
        mButton3.setImageResource(R.mipmap.favor);
        mButton4.setImageResource(R.mipmap.people);
    }

    private void initView() {
        mButton1 = (BottomIcon) findViewById(R.id.main_icon_1);
        mButton2 = (BottomIcon) findViewById(R.id.main_icon_2);
        mButton3 = (BottomIcon) findViewById(R.id.main_icon_3);
        mButton4 = (BottomIcon) findViewById(R.id.main_icon_4);
        mButton1.setOnClickListener(this);
        mButton2.setOnClickListener(this);
        mButton3.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }

    private void changeButtonImage(int index) {
        resetBottomIcon();
        switch (index) {
            case INDEX_MAIN_FRAGMENT: {
                mButton1.setImageResource(R.mipmap.home_fill);
                mButton1.setTextColor(COLOR_SELECTED);
                break;
            }
            case INDEX_SHOP_FRAGMENT: {
                mButton2.setImageResource(R.mipmap.shop_fill);
                mButton2.setTextColor(COLOR_SELECTED);
                break;
            }
            case INDEX_FAVOR_FRAGMENT: {
                mButton3.setImageResource(R.mipmap.favor_fill);
                mButton3.setTextColor(COLOR_SELECTED);
                break;
            }
            case INDEX_SETTING_FRAGMENT: {
                mButton4.setImageResource(R.mipmap.people_fill);
                mButton4.setTextColor(COLOR_SELECTED);
                break;
            }
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.main_icon_1: {
                updateChangedView(INDEX_MAIN_FRAGMENT);
                break;
            }
            case R.id.main_icon_2: {
                updateChangedView(INDEX_SHOP_FRAGMENT);
                break;
            }
            case R.id.main_icon_3: {
                updateChangedView(INDEX_FAVOR_FRAGMENT);
                break;
            }
            case R.id.main_icon_4: {
                updateChangedView(INDEX_SETTING_FRAGMENT);
                break;
            }
        }
    }

}
