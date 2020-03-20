package com.canny.no_touch_elevator;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import com.canny.no_touch_elevator.activities.MainActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by renbinbin1 on 2018/4/2.
 */

public class CrashHandler implements Thread.UncaughtExceptionHandler{
    private MyApp softApp;
    public static final String TAG="CrashHandler";
    //系统默认的UncaughtException处理类
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    //程序的Context对象
    private Context mContext;
    //用来存储设备信息和异常信息
    private Map<String,String> infos=new HashMap<String,String>();
    //用于格式化日期,作为日志文件名的一部分
    private DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
    /**保证只有一个CrashHandler实例**/
    public CrashHandler(MyApp app){
        softApp=app;
    }

    /**
     * 初始化
     */
    public void init(Context context){
        mContext=context;
        //获取系统默认的UnCaughtException处理器
        mDefaultHandler=Thread.getDefaultUncaughtExceptionHandler();
        //设置该CrashHandler为程序默认处理器
        Thread.setDefaultUncaughtExceptionHandler(this);;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        Log.e(TAG, "error : ", ex);
        //收集设备参数信息
        collectDeviceInfo(mContext);
        //保存日志文件
        saveCrashInfo2File(ex);
        long threadId = thread.getId();
        if (threadId != 1) {
            // 此处示例跳转到汇报异常界面。
            Intent intent = new Intent(softApp, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            softApp.startActivity(intent);
        } else {
            // 此处示例发生异常后，重新启动应用
            Intent intent = new Intent(softApp, MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            softApp.startActivity(intent);
            // kill App Progress
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }

    private String  saveCrashInfo2File(Throwable ex) {
        StringBuffer sb=new StringBuffer();
        for(Map.Entry<String, String> entry : infos.entrySet()){
            String key=entry.getKey();
            String value=entry.getValue();
            sb.append(key + "=" + value + "\n");
        }
        Writer writer=new StringWriter();
        PrintWriter printWriter=new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while(cause!=null){
            cause.printStackTrace(printWriter);
            cause=cause.getCause();
        }
        printWriter.close();
        String result=writer.toString();
        sb.append(result);
        long timestamp=System.currentTimeMillis();
        String time=formatter.format(new Date());
        String fileName = "crash-" + time + "-" + timestamp + ".log";
        if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)){
            String path="/sdcard/crash/";
            File dir=new File(path);
            if(!dir.exists()){
                dir.mkdirs();
            }
            try {
                FileOutputStream fos=new FileOutputStream(path + fileName);
                fos.write(sb.toString().getBytes());
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, "an error occured while writing file...", e);
            } catch (IOException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, "an error occured while writing file...", e);
            }
        }
        return null;
    }

    private void collectDeviceInfo(Context ctx) {
        PackageManager pm=ctx.getPackageManager();
        PackageInfo pi;
        try {
            pi = pm.getPackageInfo(ctx.getPackageCodePath(),PackageManager.GET_ACTIVITIES);
            if(pi!=null){
                String versionName=pi.versionName==null?"null":pi.versionName;
                String versionCode=pi.versionCode+"";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields=Build.class.getDeclaredFields();
        for(Field field:fields){

            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (IllegalAccessException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                // TODO Auto-generated catch block
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

}
