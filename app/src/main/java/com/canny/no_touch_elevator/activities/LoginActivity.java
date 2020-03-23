package com.canny.no_touch_elevator.activities;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.canny.no_touch_elevator.BroadcastReceiver.ISmsReceived;
import com.canny.no_touch_elevator.BroadcastReceiver.SMSContentObserver;
import com.canny.no_touch_elevator.BroadcastReceiver.SMSReceiver;
import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.util.Assistant;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.util.WeiboDialogUtils;
import com.canny.no_touch_elevator.views.VerifyCodeView;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;

import androidx.core.app.ActivityCompat;
import butterknife.BindView;
import butterknife.ButterKnife;
import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.RuntimePermissions;

import static com.canny.no_touch_elevator.BroadcastReceiver.SMSReceiver.ACTION_SMS_RECEIVER;

@RuntimePermissions
public class LoginActivity extends BaseActivity implements CannyCallback,
        ActivityCompat.OnRequestPermissionsResultCallback  {

    @BindView(R.id.img_account)
    ImageView imgAccount;
    @BindView(R.id.et_phone_num)
    EditText etPhoneNum;
    @BindView(R.id.img_pw)
    ImageView imgPw;
    @BindView(R.id.verify_code_view)
    VerifyCodeView verify_code_view;
    @BindView(R.id.tv_reqpasscode)
    TextView tvReqPasscode;
    @BindView(R.id.btn_login)
    Button btnLogin;

    private TimeCount time;
    private Dialog mWeiboDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        requestPermission();
        time=new TimeCount(60000,1000);
        SMSMsg();

        //从设备获取手机号
        String userPhone=Assistant.getUserPhone();
        if(null!=userPhone&&!userPhone.isEmpty()){
            etPhoneNum.setText(userPhone);
        }else{
            //从save 的获取
            String phoneNum= SharedPrefOP.getInstance().getPhoneNum();
            if(null!=phoneNum&&!phoneNum.isEmpty()){
                etPhoneNum.setText(phoneNum);
            }
        }

        btnLogin.setEnabled(false);
        tvReqPasscode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!Assistant.isMobile(getInputPhoneNum())) {
                    Toast toast=Toast.makeText(LoginActivity.this, "请输入有效手机号", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                    return;
                }
                CannyApi.ReqPasscode(etPhoneNum.getText().toString().trim(), LoginActivity.this);
                time.start();
                verify_code_view.requestFocus();
            }
        });

        verify_code_view.setInputCompleteListener(new VerifyCodeView.InputCompleteListener() {
            @Override
            public void inputComplete() {
                if(verify_code_view.getEditContent().toString().length()==4){
                    btnLogin.setEnabled(true);
                }
            }

            @Override
            public void invalidContent() {

            }
        });

        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        final NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Assistant.isMobile(getInputPhoneNum())){
                    Toast.makeText(LoginActivity.this,"请输入有效手机号",Toast.LENGTH_SHORT).show();
                    return;
                }

                if (mNetworkInfo==null){
                    Toast.makeText(LoginActivity.this,"无网络信号",Toast.LENGTH_SHORT).show();
                }else {
                    if (etPhoneNum.getText().toString().length()==11){
                        CannyApi.Login(etPhoneNum.getText().toString().trim(),verify_code_view.getEditContent().toString().trim(),LoginActivity.this);
                        mWeiboDialog = WeiboDialogUtils.createLoadingDialog(LoginActivity.this, "登录中...");
                        mHandler.sendEmptyMessageDelayed(1, 10000);
                    }else {
                        Toast.makeText(LoginActivity.this,"请输入11位长度的有效手机号",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.M)
    private void requestPermission() {
        LoginActivityPermissionsDispatcher.autoReadSMSWithCheck(this);
    }

    private void SMSMsg() {
        SMSContentObserver smsContentObserver = new SMSContentObserver(this, handler);
        getContentResolver().registerContentObserver(Uri.parse("content://sms/"), true, smsContentObserver);
    }

    @SuppressLint("HandlerLeak")
    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {
                verify_code_view.setEditContent(msg.obj.toString());
            }
        }
    };

    //获取账号
    public String getInputPhoneNum() {
        return etPhoneNum.getText().toString().trim();//去掉空格
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    break;
            }
        }
    };

    @Override
    protected void getData() {
        //CannyApi.JudgeRegister(this);
    }

    @Override
    public void onFailure(String method, String reason, Object other) {
        if(method=="ReqPasscode"){
            //TODO:check network etc?
        }
        if (method=="Login"){
            mWeiboDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(String method, Object result, Object other) {
        if(method=="ReqPasscode"){

        }else if (method=="Login"){
            String deviceId =Assistant.getDeviceId();
            String phoneNum=getInputPhoneNum();
            SharedPrefOP.getInstance().savePhoneNum(phoneNum);
            SharedPrefOP.getInstance().saveOpenId(phoneNum+deviceId);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            mWeiboDialog.dismiss();
            finish();
        }
    }


    @NeedsPermission(Manifest.permission.READ_SMS)
    void autoReadSMS() {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        LoginActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
    }


    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);
        }

        @Override
        public void onTick(long l) {
           // tvRegister.setBackgroundColor(Color.parseColor("#B6B6D8"));
            tvReqPasscode.setClickable(false);
            tvReqPasscode.setText("("+l/1000+")秒后可重新发送");
            tvReqPasscode.setTextColor(Color.WHITE);
        }

        @Override
        public void onFinish() {
            tvReqPasscode.setText("重新获取验证码");
            tvReqPasscode.setClickable(true);
            tvReqPasscode.setTextColor(Color.WHITE);
           // tvRegister.setBackgroundColor(Color.parseColor("#4EB84A"));
        }
    }

}
