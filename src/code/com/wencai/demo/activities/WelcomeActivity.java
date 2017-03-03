package com.wencai.demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAdView;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAdView;
import com.wencai.demo.R;
import com.wencai.demo.utils.ThreadUtils;
import com.wencai.demo.utils.VolleyUtil;
import com.wencai.demo.utils.logs.ELog;
import com.xiaomi.ad.i18n.sdk.AdError;
import com.xiaomi.ad.i18n.sdk.MiAdsSDK;
import com.xiaomi.ad.i18n.sdk.NativeAdItem;
import com.xiaomi.ad.i18n.sdk.NativeAdsListener;

/**
 * Created by Li Guoqing on 2016/5/5.
 */
public class WelcomeActivity extends Activity {
    private static final int DELAY_JUMP_INTERNAL = 5 * 1000;
    private static final String ADMOB_AD_UNIT_ID = "ca-app-pub-3940256099942544/2247696110";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Handler handler = new Handler(Looper.myLooper()) {
            @Override
            public void handleMessage(Message msg) {
                ELog.e("handleMessage" + " " + msg.what);
            }
        };
        handler.sendEmptyMessage(1);
    }

/*    static {
        System.loadLibrary("HelloWorldJNI");
    }
    private native String printJNI(String inputStr);*/

    @Override
    protected void onResume() {
        super.onResume();

        MiAdsSDK.init(this.getApplicationContext());

        MiAdsSDK.getInstance().loadNativeAdsForAdmob(111l, new NativeAdsListener() {
            @Override
            public void onAdsLoadSucceed(NativeAdItem nativeAdItem) {
                Log.e("WelcomeActivi23", nativeAdItem.getTitle() + "");
                addToWindow(nativeAdItem);
            }

            private void addToWindow(NativeAdItem nativeAdItem) {
                Object o = nativeAdItem.getAdObject();
                ViewHolder viewHolder = transform(nativeAdItem);
                if(viewHolder == null){
                    return;
                }
                final View view;
                if (o instanceof NativeContentAd) {
                    NativeContentAdView contentAdView = new NativeContentAdView(WelcomeActivity.this.getApplicationContext());

                    contentAdView.setHeadlineView(viewHolder.mTitleView);
                    contentAdView.setCallToActionView(viewHolder.mCallToAction);
                    contentAdView.setBodyView(viewHolder.mBody);
                    contentAdView.setLogoView(viewHolder.mIcon);
                    contentAdView.setImageView(viewHolder.mImage);

                    contentAdView.addView(viewHolder.mAdView);

                    contentAdView.setNativeAd((NativeContentAd)o);

                    view = contentAdView;
                } else if (o instanceof NativeAppInstallAd) {
                    NativeAppInstallAdView appInstallAd = new NativeAppInstallAdView(WelcomeActivity.this.getApplicationContext());

                    appInstallAd.setHeadlineView(viewHolder.mTitleView);
                    appInstallAd.setCallToActionView(viewHolder.mCallToAction);
                    appInstallAd.setBodyView(viewHolder.mBody);
                    appInstallAd.setIconView(viewHolder.mIcon);
                    appInstallAd.setImageView(viewHolder.mImage);

                    appInstallAd.addView(viewHolder.mAdView);

                    appInstallAd.setNativeAd((NativeAppInstallAd)o);

                    view = appInstallAd;
                } else {
                    return;
                }
                WelcomeActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        RelativeLayout layout = (RelativeLayout)findViewById(R.id.admob_area);
                        layout.addView(view);
                    }
                });
            }

            ViewHolder transform(NativeAdItem item) {
                if(item == null){
                    return null;
                }
                LayoutInflater inflater = LayoutInflater.from(WelcomeActivity.this.getApplicationContext());
                ViewHolder holder = new ViewHolder();
                holder.mAdView = inflater.inflate(R.layout.admob_items, null);
                holder.mBody = (TextView) holder.mAdView.findViewById(R.id.ad_body);
                holder.mTitleView = (TextView) holder.mAdView.findViewById(R.id.ad_title);
                holder.mCallToAction = (TextView) holder.mAdView.findViewById(R.id.ad_cta);
                holder.mIcon = (ImageView) holder.mAdView.findViewById(R.id.ad_icon);
                holder.mImage = (ImageView) holder.mAdView.findViewById(R.id.ad_image);

                setTextView(holder.mBody, item.getDes());
                setTextView(holder.mTitleView, item.getTitle());
                setTextView(holder.mCallToAction, item.getCta());
                loadImage(holder.mIcon, item.getIconUrl());
                loadImage(holder.mImage, item.getImgUrl());

                return holder;
            }

            void loadImage(ImageView imageView, String url){
                if(imageView == null || TextUtils.isEmpty(url)){
                    return;
                }

                VolleyUtil.loadImage(imageView, url);
            }

            void setTextView(TextView textView, String text){
                if(textView == null || TextUtils.isEmpty(text)){
                    return;
                }

                textView.setText(text);
            }

            class ViewHolder {
                View mAdView;
                TextView mTitleView;
                TextView mCallToAction;
                TextView mBody;
                ImageView mIcon;
                ImageView mImage;
            }

            @Override
            public void onAdsLoadFailed(AdError adError) {
                Log.e("WelcomeActivi23", adError.getErrorMessage());
            }
        });
//        delayJump();
    }

    private void delayJump() {
        ThreadUtils.postOnUiThread(new Runnable() {
            @Override
            public void run() {
                jumpToMainActivity();
            }
        });
//        ThreadUtils.postOnUiThreadDelayed(new Runnable() {
//            @Override
//            public void run() {
//                jumpToMainActivity();
//            }
//        }, DELAY_JUMP_INTERNAL);
    }

    private void jumpToMainActivity() {
        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

}
