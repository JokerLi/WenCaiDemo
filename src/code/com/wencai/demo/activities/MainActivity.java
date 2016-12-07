package com.wencai.demo.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.wencai.demo.R;
import com.wencai.demo.fragments.MainFragment;
import com.wencai.demo.fragments.SettingsFragment;

public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private static final int INDEX_MAIN_FRAGMENT = 0;
    private static final int INDEX_SETTING_FRAGMENT = 3;

    private FragmentManager mFragmentManager;
    private MainFragment mMainFragment;
    private SettingsFragment mSettingsFragment;

    private View mButton1;
    private View mButton2;
    private View mButton3;
    private View mButton4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initFragment();
        initView();
    }

    private void initFragment() {
        mFragmentManager = getSupportFragmentManager();
        trans2Fragment(INDEX_MAIN_FRAGMENT);
    }

    private void trans2Fragment(int index) {
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
    }

    private void initView() {
        mButton1 = findViewById(R.id.main_icon_1);
        mButton2 = findViewById(R.id.main_icon_2);
        mButton3 = findViewById(R.id.main_icon_3);
        mButton4 = findViewById(R.id.main_icon_4);
        mButton1.setOnClickListener(this);
        mButton4.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.main_icon_1) {
            trans2Fragment(INDEX_MAIN_FRAGMENT);
        } else if (id == R.id.main_icon_4) {
            trans2Fragment(INDEX_SETTING_FRAGMENT);
        }
    }

}
