package com.wencai.demo.utils;

import android.text.TextUtils;
import android.util.Log;

/**
 * Created by root on 3/8/17.
 */

public class DecodeUtils {

    public static String decode(String postData) {
        byte[] decodeData = AndroidBase64.decode(postData.getBytes(), AndroidBase64.NO_WRAP | AndroidBase64.URL_SAFE);
        return new String(decodeData);
    }

    public static String encode(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        byte[] input = str.getBytes();
        String decodeString = new String(AndroidBase64.encode(input, AndroidBase64.NO_WRAP | AndroidBase64.URL_SAFE));
        return decodeString;
    }
}
