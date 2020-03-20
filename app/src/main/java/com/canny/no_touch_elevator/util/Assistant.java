package com.canny.no_touch_elevator.util;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.canny.no_touch_elevator.BuildConfig;
import com.canny.no_touch_elevator.MyApp;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static android.os.Build.VERSION.SDK;

public class Assistant {

    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        String s2="^[1](([3|5|8][\\d])|([4][4,5,6,7,8,9])|([6][2,5,6,7])|([7][^9])|([9][1,8,9]))[\\d]{8}$";// 验证手机号
        if((null!=str)&&str.length()==11){
            p = Pattern.compile(s2);
            m = p.matcher(str);
            b = m.matches();
        }
        return b;
    }

    public static  String getDeviceId(){
        try {
            TelephonyManager tm = (TelephonyManager) MyApp.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            String deviceId = tm.getDeviceId();

            return deviceId;
        }catch (Exception e){
            Log.e("Assistant.getDeviceId",""+e);
        }
        return "";
    }

    public static  String getUserPhone(){
        try {
            TelephonyManager tm = (TelephonyManager) MyApp.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
            //String deviceid = tm.getDeviceId();//获取智能设备唯一编号
            String te1 = tm.getLine1Number();//获取本机号码
            //String imei = tm.getSimSerialNumber();//获得SIM卡的序号
            //String imsi = tm.getSubscriberId();//得到用户Id

            return te1;
        }catch (Exception e){
            Log.e("Assistant.getUserPhone",""+e);
        }
        return "";
    }

    public static String getDeviceInfo(){
        String device_model = Build.MODEL; // 设备型号 。
        String version_sdk = SDK; // 设备SDK版本  。
        String version_release = Build.VERSION.RELEASE; // 设备的系统版本 。
        String brand = android.os.Build.BRAND;

        String deviceInfo = "DeviceName:"+brand + " " + device_model + "," +"OS:"+"Android" +version_release +
                "," + "API:" + version_sdk + "," + "ID:" + Assistant.getDeviceId();

        return deviceInfo;
    }

    public static String getSoftwareInfo(){
        String softwareInfo = "AppName:" + getAppName(MyApp.getApplication()) + "," + "AppVersion:" + BuildConfig.VERSION_NAME;
        return softwareInfo;
    }

    public static String getAppName(Context context) {
        PackageManager pm = context.getPackageManager();
        //获取包信息
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), 0);
            //获取应用 信息
            ApplicationInfo applicationInfo = packageInfo.applicationInfo;
            //获取albelRes
            int labelRes = applicationInfo.labelRes;
            //返回App的名称
            return context.getResources().getString(labelRes);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
