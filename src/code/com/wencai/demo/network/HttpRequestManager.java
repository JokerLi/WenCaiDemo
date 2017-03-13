package com.wencai.demo.network;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.PriorityBlockingQueue;

/**
 * Created by root on 3/3/17.
 */

public class HttpRequestManager {
    private static HttpRequestManager sInstance;
    private final Object mLock = new Object();

    private PriorityBlockingQueue<Request> mHttpRequestQueue;
    private Thread mRequestThread;

    public enum ResultType {
        NORMAL, STRING, STREAM
    }

    public static class Request implements Comparable<Request> {
        private char mPriority;
        private IRequestListener mListener = null;
        private String mUrl = null;
        private ResultType mType = ResultType.NORMAL;

        public Request(String url, IRequestListener listener) {
            mUrl = url;
            mListener = listener;
        }

        public Request setResultType(ResultType type) {
            mType = type;
            return this;
        }

        @Override
        public int compareTo(Request another) {
            return 0;
        }
    }

    public interface IRequestListener {
        void onSuccess(String url, String response);

        void onSuccess(String url, InputStream response);

        void onFailed(String url, int errorCode);
    }

    private HttpRequestManager() {
        mHttpRequestQueue = new PriorityBlockingQueue<Request>();
        mRequestThread = new RequestThread();
        mRequestThread.start();
    }

    public static HttpRequestManager getInstance() {
        if (sInstance == null) {
            synchronized (HttpRequestManager.class) {
                if (sInstance == null) {
                    sInstance = new HttpRequestManager();
                }
            }
        }

        return sInstance;
    }

    public void addRequest(Request request) {
        synchronized (mLock) {
            mHttpRequestQueue.put(request);
            mLock.notify();
        }
    }

    class RequestThread extends Thread {

        @Override
        public void run() {
            while (true) {
                try {
                    synchronized (mLock) {
                        mLock.wait();
                    }
                    Request request = mHttpRequestQueue.poll();
                    requestUrl(request);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void requestUrl(Request request) {
        if (request == null || TextUtils.isEmpty(request.mUrl)) {
            return;
        }
        IRequestListener listener = request.mListener;
        HttpURLConnection conn = null;
        try {
            URL url = new URL(request.mUrl);
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setConnectTimeout(5000);
            conn.setUseCaches(false);
            if (conn.getResponseCode() == 200) {

                InputStream in = new BufferedInputStream(conn.getInputStream());

                if (listener != null) {
                    if (request.mType == ResultType.STREAM) {
                        listener.onSuccess(request.mUrl, in);
                    } else {
                        String response = convertStreamToString(in);
                        listener.onSuccess(request.mUrl, response);
                    }
                }
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static String convertStreamToString(InputStream is) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();

        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return sb.toString();
    }


}
