package com.shuyu.gsyvideoplayer.app;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.SeekBar;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class BaseBuddyVideoPlayer extends StandardGSYVideoPlayer {

    public BaseBuddyVideoPlayer(Context context, Boolean fullFlag) {
        super(context, fullFlag);
        Log.e(GsyApplication.TAG, "StandardGSYVideoPlayer --->Context context, Boolean fullFlag ：" + fullFlag);

    }

    public BaseBuddyVideoPlayer(Context context) {
        super(context);
    }

    public BaseBuddyVideoPlayer(Context context, AttributeSet attrs) {
        super(context, attrs);
        Log.e(GsyApplication.TAG, "Context context, AttributeSet attrs");
        // 如果进入到了这里，说明窗口没有被打开
        GsyApplication.getInstance(context).setWindow(false);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        super.onProgressChanged(seekBar, progress, fromUser);
        // 累积视频的播放时长
        GsyApplication.getInstance(seekBar.getContext()).autoAdd();
    }

    @Override
    public void onPrepared() {
        super.onPrepared();

        // 视频加载完毕，开始播放了
        Log.e(GsyApplication.TAG, "----->onPrepared --- 视频开始播放了！");
        // 在视频开始播放的时候数量也开始增加以下，不然有时候不会触发这个操作
        GsyApplication.getInstance(mContext).autoAdd();
    }

    @Override
    public void onVideoPause() {
        super.onVideoPause();
        // 暂停的时候，移除消息的统计
        GsyApplication.getInstance(mContext).removeSendMessage();
    }
}