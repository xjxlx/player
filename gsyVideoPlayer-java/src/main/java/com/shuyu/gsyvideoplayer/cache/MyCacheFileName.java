package com.shuyu.gsyvideoplayer.cache;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.danikula.videocache.ProxyCacheUtils;
import com.danikula.videocache.file.FileNameGenerator;
import com.shuyu.gsyvideoplayer.utils.GsySpUtil;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * 自定义视频的缓存文件的名字,这个工具必须依赖于网络，不是很友好，所以要进行改造
 */
public class MyCacheFileName implements FileNameGenerator {

    private static final int MAX_EXTENSION_LENGTH = 4;
    // 资源最新的更新TAG
    private String mUrlLastModified = null;
    // 文件的名字
    private String mFimeName;
    private Context mContext;

    public MyCacheFileName(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public String generate(String url) {
        // 获取最新资源的最后修改时间
        String newSourceKey = getNewSourceKey(url);
        // 这里需要判断，如果拿不到网络获取的信息，就先从本地获取上一次请求后存入到Sp中的信息
        if (!TextUtils.isEmpty(newSourceKey)) {
            mFimeName = ProxyCacheUtils.computeMD5(newSourceKey);
            Log.e("XJX", "从网络获取的数据信息！");
        } else {
            // 获取上一次存入对应key的Value
            String mSpValue = GsySpUtil.getString(mContext, url);

            // 如果本地的sp中也获取不到，则说明是第一次请求，还没有加载过，就去重新生成
            if (!TextUtils.isEmpty(mSpValue)) {
                mFimeName = ProxyCacheUtils.computeMD5(mSpValue);
                Log.e("XJX", "从本地的SP中获取的数据信息！");
            } else {
                // 使用本地生成的方法去获取
                mFimeName = getExtension(url);
                Log.e("XJX", "使用本地的方法总获取的数据信息！");
            }

        }
        return mFimeName;
    }

    /**
     * @param url
     * @return 使用get方法去请求网络，获取视频的最新更新地址
     */
    public String getNewSourceKey(final String url) {

        // 开启异步线程去获取资源最新的更新时间
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();

                // 线程对象
                HttpURLConnection conn = null;

                try {
                    URL urls = new URL(url);

                    try {
                        conn = (HttpURLConnection) urls.openConnection();
                        conn.setRequestMethod("GET");
                        conn.setConnectTimeout(5000);
                        conn.connect();

                        // 获取最新资源的更新时间
                        long lastModified = conn.getLastModified();
                        // 获取最新资源的内容的长度
                        int contentLength = conn.getContentLength();

                        String valueOfTime = String.valueOf(lastModified);
                        String valueOfLength = String.valueOf(contentLength);

                        // 赋值操作
                        mUrlLastModified = valueOfTime + valueOfLength;

                        // 存入到本地中
                        GsySpUtil.putString(mContext, url, mUrlLastModified);

                    } catch (IOException e) {
                        e.printStackTrace();
                        Log.e("XJX", "检测VideoCache资源的时候，打开网络资源失败！");
                        if (conn != null) {
                            conn.disconnect();
                        }
                    }

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                    Log.e("XJX", "检测VideoCache资源的时候，不在主线程中执行！");
                }
            }
        };

        if (thread != null) {
            // 开启线程
            thread.start();
        }

        try {
            // 阻塞线程，等待线程执行完毕之后再去放行
            thread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return mUrlLastModified;
    }

    /**
     * @param url
     * @return 本地生成的方法去获取
     */
    private String getExtension(String url) {
        int dotIndex = url.lastIndexOf('.');
        int slashIndex = url.lastIndexOf('/');
        return dotIndex != -1 && dotIndex > slashIndex && dotIndex + 2 + MAX_EXTENSION_LENGTH > url.length() ?
                url.substring(dotIndex + 1, url.length()) : "";
    }

}
