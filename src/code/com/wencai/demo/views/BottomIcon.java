package com.wencai.demo.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.LinearLayout;

import com.wencai.demo.R;
import com.wencai.demo.utils.DimenSdkUtils;

/**
 * Created by Li Guoqing on 2016/12/10.
 */
public class BottomIcon extends LinearLayout{
    ImageView mImageView;
    TextView mTextView;

    public BottomIcon(Context context) {
        super(context);
        initView();
    }

    public BottomIcon(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }

    public BottomIcon(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(attrs);
    }

    private void initView() {
        mImageView = new ImageView(getContext());
        mTextView = new TextView(getContext());
        LinearLayout.LayoutParams params_img = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        LinearLayout.LayoutParams params_txt = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 0);
        params_img.weight = 1;
        params_txt.weight = 1;
        mImageView.setLayoutParams(params_img);
        mTextView.setLayoutParams(params_txt);
        addView(mImageView);
        addView(mTextView);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_VERTICAL);
        mTextView.setGravity(Gravity.CENTER);
    }

    private void initView(AttributeSet attrs) {
        initView();
        if(attrs == null){
            return;
        }
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BottomIcon);
        int imageId = typedArray.getResourceId(R.styleable.BottomIcon_img, -1);
        String text = typedArray.getString(R.styleable.BottomIcon_text);
        String textColor = typedArray.getString(R.styleable.BottomIcon_textcolor);
        int textSize = typedArray.getInt(R.styleable.BottomIcon_textsize, 12);

        if(!TextUtils.isEmpty(textColor)){
            mTextView.setTextColor(Color.parseColor(textColor));
        }

        if(textSize > 0){
            mTextView.setTextSize(textSize);
        }

        if(imageId < 0 || TextUtils.isEmpty(text)){
            return;
        }
        mImageView.setImageResource(imageId);
        mTextView.setText(text);
    }

    public void setImageResource(int imageId){
        mImageView.setImageResource(imageId);
    }

    public void setTextColor(String color){
        mTextView.setTextColor(Color.parseColor(color));
    }


}
