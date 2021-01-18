package com.shuyu.gsyvideoplayer.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.shuyu.gsyvideoplayer.utils.GsySpUtil;

public class GsyApplication {

    public static final String TAG = "GsyApplication";
    /**
     * 统计视频播放时间总时长的key
     */
    private static final String KEY_PLAYER_TOTAL_TIME = "key_player_total_time";

    /**
     * 视频播放的时间到了，停止播放，弹出解锁界面
     */
    private static final int CODE_PLAYER_TIME_END_UNLOCK = (int) ((System.currentTimeMillis()) + 1);

    /**
     * 轮训器轮训的code
     */
    private static final int CODE_AUTO_ADD_TIME = CODE_PLAYER_TIME_END_UNLOCK + 1;

    /**
     * GsyPlayer播放器统计时长的对象
     */
    private static int mCurrentTime;

    /**
     * 规定视频播放的总时长 ,-1 无限制
     */
    private static int mTotalTime = -1;

    /**
     * 当前是否是在弹窗模式下
     */
    public boolean isShowWindow = false;
    private int mTempTime;// 临时的时间，用于避免触发此处过多导致的数据不精确

    private MessageListener messageListener;

    @SuppressLint("StaticFieldLeak")
    private static GsyApplication mApp;
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;
    private long currentTimeMillis;
    private long endTimeMillis;

    /**
     * @return 获取单利的对象
     */
    public synchronized static GsyApplication getInstance(Context context) {
        mContext = context;
        if (mTotalTime < 0) {
            // 获取设定视频的总时长
            mTotalTime = GsySpUtil.getInt(context, KEY_PLAYER_TOTAL_TIME);
        }

        if (mApp == null) {
            mApp = new GsyApplication();
        }
        return mApp;
    }

    /**
     * 保存设置的总数据大小
     */
    public void setTotalNumber(int value) {
        GsySpUtil.putInt(mContext, KEY_PLAYER_TOTAL_TIME, value);
    }

    /**
     * 数据自增长
     */
    public void autoAdd() {
        /*
         * 满足条件了，才回去执行
         * 1：等待窗口没有被打开
         * 2：用户设置过了才生效
         */
        boolean window = isWindow();
        boolean b = mTotalTime > 0;
        if (!window && b) {// 只有在用户设置过了，才会去生效，否则如果检测到时-1的话，就不做任何的操作

            // 当前的时间
            currentTimeMillis = System.currentTimeMillis();

            // 第一次发送数据和第二次发送数据，相差不能小于1秒，否则就不发送
            if ((endTimeMillis > 0) && ((currentTimeMillis - endTimeMillis) < 1000)) {
                Log.e(TAG, "数据异常，不予统计！" + (currentTimeMillis - endTimeMillis));
                endTimeMillis = currentTimeMillis;
                return;
            }

            // 这里使用轮训器去处理时间的增加，因为进度条的精度不准确，这里先删除之前发送的请求，然后再次去发送新的数据
            removeSendMessage();

            Message message = mHandler.obtainMessage();
            message.what = CODE_AUTO_ADD_TIME;
            mHandler.sendMessage(message);
        } else {
           // Log.e(TAG, "是否在窗口打开的模式：" + window + "   是否用户已经设置过了：" + b);
        }
    }

    /**
     * @return 获取当前的自增长的数据
     */
    public int getCurrentNumber() {
        return mCurrentTime;
    }

    /**
     * @return 获取存入的总时长
     */
    public int getTotalNumber() {
        return GsySpUtil.getInt(mContext, KEY_PLAYER_TOTAL_TIME);
    }

    /**
     * 重置数据
     */
    public void resetNumber() {
        mCurrentTime = 0;
    }

    /**
     * @return 当前是否在弹窗模式下
     */
    public boolean isWindow() {
        return isShowWindow;
    }

    /**
     * 设置是否是弹窗的模式
     *
     * @param isWindow true:在弹窗模式下，false 没有在弹窗模式下
     */
    public void setWindow(boolean isWindow) {
        isShowWindow = isWindow;
    }

    public interface MessageListener {
        /**
         * @param type 1:时间到了，需要弹出弹窗的标记，2：用户解锁了，或者倒计时的时间到了，需要关闭弹窗的标记
         */
        void onHandlerMessage(int type);
    }

    /**
     * 消息的监听
     */
    public void setMessageListener(MessageListener listener) {
        messageListener = listener;
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            /*
             * 满足条件了，才回去执行
             * 1：等待窗口没有被打开
             * 2：用户设置过了才生效
             */
            boolean window = isWindow();
            boolean b = mTotalTime > 0;
            if (!window && b) {// 只有在用户设置过了，才会去生效，否则如果检测到时-1的话，就不做任何的操作
                ++mCurrentTime;
                Log.e(TAG, "开始累积增加数据,目前的数据为：" + mCurrentTime + "  总的时长为：" + mTotalTime + "  剩余时间为：" + (mTotalTime - mCurrentTime));

                if (mCurrentTime >= mTotalTime) {
                    // 如果当前的时间大于等于总的设定的时间，那么就发送提示，让页面弹出弹窗
                    if (messageListener != null) {
                        messageListener.onHandlerMessage(1);

                        // 移除所有的数据
                        removeSendMessage();
                        return;
                    }
                }

                Message message = mHandler.obtainMessage();
                message.what = CODE_AUTO_ADD_TIME;
                mHandler.sendMessageDelayed(message, 1000);
                mTempTime = mCurrentTime;

                endTimeMillis = currentTimeMillis;
            }
        }
    };

    public void removeSendMessage() {
        mHandler.removeMessages(CODE_AUTO_ADD_TIME);
        mHandler.removeCallbacksAndMessages(null);
    }

}
