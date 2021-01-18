package com.apphelper.player;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.shuyu.gsyvideoplayer.GSYVideoManager;
import com.shuyu.gsyvideoplayer.builder.GSYVideoOptionBuilder;
import com.shuyu.gsyvideoplayer.listener.GSYSampleCallBack;
import com.shuyu.gsyvideoplayer.listener.LockClickListener;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

public class MainActivity extends AppCompatActivity {

    private StandardGSYVideoPlayer detailPlayer;
    private OrientationUtils orientationUtils;
    private boolean isPlay;
    private boolean isPause;
    private TextView tv_test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        detailPlayer = findViewById(R.id.detail_player);

        tv_test = findViewById(R.id.tv_test);

        String url = "http://7xjmzj.com1.z0.glb.clouddn.com/20171026175005_JObCxCE2.mp4";

        //外部辅助的旋转，帮助全屏
        orientationUtils = new OrientationUtils(this, detailPlayer);
        //初始化不打开外部的旋转
        orientationUtils.setEnable(false);

        GSYVideoOptionBuilder gsyVideoOption = new GSYVideoOptionBuilder();
        gsyVideoOption
//                .setThumbImageView(imageView)
                .setIsTouchWiget(true)
                .setRotateViewAuto(false)
                .setLockLand(false)
                .setAutoFullWithSize(true)
                .setShowFullAnimation(false)
                .setNeedLockFull(true)
                .setUrl(url)
                .setCacheWithPlay(false)
                .setVideoTitle("测试视频")
                .setVideoAllCallBack(new GSYSampleCallBack() {
                    @Override
                    public void onPrepared(String url, Object... objects) {
                        super.onPrepared(url, objects);
                        //开始播放了才能旋转和全屏
                        orientationUtils.setEnable(true);
                        isPlay = true;
                        tv_test.setText("1111");
                    }

                    @Override
                    public void onQuitFullscreen(String url, Object... objects) {
                        super.onQuitFullscreen(url, objects);
                        if (orientationUtils != null) {
                            orientationUtils.backToProtVideo();
                        }
                    }
                }).setLockClickListener(new LockClickListener() {
            @Override
            public void onClick(View view, boolean lock) {
                if (orientationUtils != null) {
                    //配合下方的onConfigurationChanged
                    orientationUtils.setEnable(!lock);
                }
            }
        }).build(detailPlayer);

        detailPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //直接横屏
                orientationUtils.resolveByClick();

                //第一个true是否需要隐藏actionbar，第二个true是否需要隐藏statusbar
                detailPlayer.startWindowFullscreen(MainActivity.this, true, true);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (orientationUtils != null) {
            orientationUtils.backToProtVideo();
        }
        if (GSYVideoManager.backFromWindowFull(this)) {
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onPause() {
        detailPlayer.getCurrentPlayer().onVideoPause();
        super.onPause();
        isPause = true;
    }

    @Override
    protected void onResume() {
        detailPlayer.getCurrentPlayer().onVideoResume(false);
        super.onResume();
        isPause = false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (isPlay) {
            detailPlayer.getCurrentPlayer().release();
        }
        if (orientationUtils != null)
            orientationUtils.releaseListener();
    }

    /**
     * orientationUtils 和  detailPlayer.onConfigurationChanged 方法是用于触发屏幕旋转的
     */
    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //如果旋转了就全屏
        if (isPlay && !isPause) {
            detailPlayer.onConfigurationChanged(this, newConfig, orientationUtils, true, true);
        }
    }

}