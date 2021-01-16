package com.shuyu.gsyvideoplayer.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

/**
 * <p>文件描述<p>
 * <p>作者：hp<p>
 * <p>创建时间：2019/1/14<p>
 * <p>更改时间：2019/1/14<p>
 */
public class GsySpUtil {

    private static final String SP_FILE_NAME = "userInfo";
    private static SharedPreferences sp;

    // 获取SP 对象
    private static synchronized SharedPreferences getSp(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE);
        }
        return sp;
    }

    /**
     * 放入String类型的数据
     *
     * @param context
     * @param key     存入的key
     * @param value   存入的value
     */
    public static void putString(Context context, String key, String value) {
        if ((context != null) && (key != null)) {
            getSp(context);
            // 开启编辑器
            SharedPreferences.Editor edit = sp.edit();
            // 存入数据
            if (value == null) {
                value = "";
            }
            edit.putString(key, value);
            // 提交
            edit.commit();
        }
    }

    /**
     * @param context
     * @param key     获取数据的key
     * @return 获取Stirng类型的数据, 如果获取不到, 则返回null
     */
    public static String getString(Context context, String key) {
        String value = null;
        if ((context != null) && (!TextUtils.isEmpty(key))) {
            getSp(context);
            value = sp.getString(key, null);
        }
        return value;
    }

    /**
     * 放入String类型的数据
     *
     * @param context
     * @param key     存入的key
     * @param value   存入的value
     */
    public static void putInt(Context context, String key, int value) {
        if ((context != null) && (key != null)) {
            getSp(context);
            // 开启编辑器
            SharedPreferences.Editor edit = sp.edit();
            // 存入数据
            edit.putInt(key, value);
            // 提交
            edit.commit();
        }
    }

    /**
     * @param context
     * @param key     获取数据的key
     * @return 获取Stirng类型的数据, 如果获取不到, 则返回null
     */
    public static int getInt(Context context, String key) {
        int value = 0;
        if ((context != null) && (!TextUtils.isEmpty(key))) {
            getSp(context);
            value = sp.getInt(key, 0);
        }
        return value;
    }

}
