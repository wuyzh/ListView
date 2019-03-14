package com.wuyazhou.learn.logview;

import android.util.Log;

import java.util.concurrent.PriorityBlockingQueue;

/**
 * @author wuyzh
 * 存储需要展示的信息
 * */
public class LogShowUtil {
    public static final String CLEAN_LOG = "clean_all_clean_all";
    private static PriorityBlockingQueue<LogShowView.LogModel> mPriorityBlockingQueue = null;
    public static final String INTERVAL= ": ";

    public static void addLog(String key,String value){
        mPriorityBlockingQueue.add(new LogShowView.LogModel(key+INTERVAL,value));
    }

    public static void addLog(String key,String value,boolean showAndroidLog){
        mPriorityBlockingQueue.add(new LogShowView.LogModel(key+INTERVAL,value));
        if (showAndroidLog){
            Log.d(key,value);
        }
    }

    public static PriorityBlockingQueue getLogQueue(){
        if (mPriorityBlockingQueue == null){
            mPriorityBlockingQueue = new PriorityBlockingQueue<LogShowView.LogModel>();
        }
        return mPriorityBlockingQueue;
    }

    public static void release(){
        mPriorityBlockingQueue = null;
    }
}
