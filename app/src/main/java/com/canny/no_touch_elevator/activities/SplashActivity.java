package com.canny.no_touch_elevator.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;

import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;

import java.io.File;

public class SplashActivity extends BaseActivity implements CannyCallback, BaseActivity.ReloadInterface {

    ImageView mImgStart;
    private String openId;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData() {
        //supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setReloadInterface(this);
        openId = SharedPrefOP.getInstance().getOpenId();
        initView();
    }

    @Override
    protected void getData() {

    }

    private void initView() {
        mImgStart = (ImageView) findViewById(R.id.id_img_splash);
        iniImage();
    }

    private void iniImage() {
        File dir = getFilesDir();
        File imageFile = new File(dir, "start.jpg");
        if(imageFile.exists()) {
            mImgStart.setImageBitmap(BitmapFactory.decodeFile(imageFile.getAbsolutePath()));
        }else {
            mImgStart.setImageResource(R.mipmap.splash);
        }

        ScaleAnimation scaleAnim = new ScaleAnimation(
                1.0f,
                1.2f,
                1.0f,
                1.2f,
                Animation.RELATIVE_TO_SELF,
                0.5f,
                Animation.RELATIVE_TO_SELF,
                0.5f
        );

        scaleAnim.setFillAfter(true);
        scaleAnim.setDuration(3000);
        scaleAnim.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                //在这里做一些初始化的操作
                //跳转到指定的Activity
                startActivity();
                mImgStart.setImageResource(0);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mImgStart.startAnimation(scaleAnim);
    }

    private void startActivity() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo==null){
            changePageState(BaseActivity.PageState.ERROR);
        }else {
            if(null!=openId && !openId.isEmpty()){
                CannyApi.JudgeIfRegister(openId,this);
            }else{
                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                finish();
            }
        }
    }


    @Override
    public void onFailure(String method, String reason, Object other) {
        if (method=="JudgeIfRegister"){
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    public void onSuccess(String method, Object result, Object other) {
        if (method=="JudgeIfRegister"){
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }

    @Override
    public void reloadClickListener() {
        if(null!=openId && !openId.isEmpty()){
            CannyApi.JudgeIfRegister(openId,this);
        }else{
            Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            finish();
        }
    }
}
