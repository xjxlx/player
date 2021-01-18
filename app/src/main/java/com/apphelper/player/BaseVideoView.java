package com.apphelper.player;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class BaseVideoView extends StandardGSYVideoPlayer {

    public BaseVideoView(Context context, Boolean fullFlag) {
        super(context, fullFlag);
    }

    public BaseVideoView(Context context) {
        super(context);
    }

    public BaseVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
