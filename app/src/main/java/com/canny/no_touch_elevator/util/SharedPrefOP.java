package com.canny.no_touch_elevator.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.canny.no_touch_elevator.MyApp;

public class SharedPrefOP {
    public static final String FILE_NAME = "setting";
    public static final String KEY_PHONE= "phoneNum";
    public static final String KEY_OPENID = "OpenId";
    public static final String KEY_USERNAME= "userName";
    public static final String KEY_BUILDNAME= "buildname";
    public static final String KEY_BUILDNUMBER= "buildnumber";
    public static final String KEY_BIANHAO= "bianhao";
    public static final String KEY_REQUESTID= "requestId";

    private static SharedPreferences mSharedPreferences;
    private static SharedPrefOP instance;

    private SharedPrefOP(Context context) {
        mSharedPreferences = context.getSharedPreferences(FILE_NAME,
                Context.MODE_PRIVATE);
    }

    /**
     * 初始化单例
     *
     * @param context
     */
    public static synchronized SharedPrefOP init(Context context) {
        if (instance == null) {
            instance = new SharedPrefOP(context);
        }
        return instance;
    }

    public static SharedPrefOP getInstance() {
        if (instance == null) {
            instance=new SharedPrefOP(MyApp.getApplication().getApplicationContext());
        }
        return instance;
    }

    public String getRequestId() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        String requestId= (String) getData(KEY_REQUESTID,"");
        return requestId;
    }
    public void saveRequestId(String requestId) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        saveData(KEY_REQUESTID,requestId);
    }

    public String getBianHao() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        String bianhao=(String)getData(KEY_BIANHAO,"");
        return bianhao;
    }
    public void saveBianHao(String bianhao) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        saveData(KEY_BIANHAO,bianhao);
    }

    public String getBuildName() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        String buildName=(String)getData(KEY_BUILDNAME,"");
        return buildName;
    }
    public void saveBuildName(String buildName) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        saveData(KEY_BUILDNAME,buildName);
    }

    public String getBuildNumber() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        String buildNumber=(String)getData(KEY_BUILDNUMBER,"");
        return buildNumber;
    }
    public void saveBuildNumber(String buildNumber) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        saveData(KEY_BUILDNUMBER,buildNumber);
    }

    //获得保存的OpenID
    public String getOpenId() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        String openId=(String)getData(KEY_OPENID,"");
        return openId;
    }
    public void saveOpenId(String openId) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        saveData(KEY_OPENID,openId);
    }

    //获得保存的phoneNum
    public String getPhoneNum() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        String phoneNum=(String)getData(KEY_PHONE,"");
        return phoneNum;
    }
    public void savePhoneNum(String phoneNum) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        saveData(KEY_PHONE,phoneNum);
    }

    //获得保存的userName
    public String getUsername() {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        String userName=(String)getData(KEY_USERNAME,"");
        return userName;
    }
    public void saveUsername(String userName) {
        //获取SharedPreferences对象，使用自定义类的方法来获取对象
        saveData(KEY_USERNAME,userName);
    }
    //------------------------------------------------------
    /**
     * 保存数据
     *
     * @param key
     * @param data
     */
    private void saveData(String key, Object data) {
        String type = data.getClass().getSimpleName();

        SharedPreferences.Editor editor = mSharedPreferences.edit();

        if ("Integer".equals(type)) {
            editor.putInt(key, (Integer) data);
        } else if ("Boolean".equals(type)) {
            editor.putBoolean(key, (Boolean) data);
        } else if ("String".equals(type)) {
            editor.putString(key, (String) data);
        } else if ("Float".equals(type)) {
            editor.putFloat(key, (Float) data);
        } else if ("Long".equals(type)) {
            editor.putLong(key, (Long) data);
        }

        editor.commit();
    }

    /**
     * 得到数据
     *
     * @param key
     * @param defValue
     * @return
     */
    private Object getData(String key, Object defValue) {

        String type = defValue.getClass().getSimpleName();
        if ("Integer".equals(type)) {
            return mSharedPreferences.getInt(key, (Integer) defValue);
        } else if ("Boolean".equals(type)) {
            return mSharedPreferences.getBoolean(key, (Boolean) defValue);
        } else if ("String".equals(type)) {
            return mSharedPreferences.getString(key, (String) defValue);
        } else if ("Float".equals(type)) {
            return mSharedPreferences.getFloat(key, (Float) defValue);
        } else if ("Long".equals(type)) {
            return mSharedPreferences.getLong(key, (Long) defValue);
        }

        return null;
    }

}