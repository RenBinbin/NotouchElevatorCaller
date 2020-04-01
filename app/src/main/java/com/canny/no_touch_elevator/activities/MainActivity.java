package com.canny.no_touch_elevator.activities;

import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.SelectActivity;
import com.canny.no_touch_elevator.UserActivity;
import com.canny.no_touch_elevator.VisitorActivity;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.huti.CallElevatorActivity;
import com.canny.no_touch_elevator.huti.ScanInTheCallActivity;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;
import com.canny.no_touch_elevator.webapi.response.UserInfoBean;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements CannyCallback {

    @BindView(R.id.btn_select)
    Button btnSelect;
    @BindView(R.id.rl_apply)
    RelativeLayout rlApply;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;

    private boolean isExit;
    private UserInfoBean infoBean;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        startScan();
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, SelectActivity.class);
                startActivity(intent1);
            }
        });

        rlApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, VisitorActivity.class));
            }
        });

    }

    private void startScan() {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CustomScanActivity.class);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE);
        integrator.setPrompt("扫描呼梯");
        integrator.setCameraId(0);  // Use a specific camera of the device
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(true);
        integrator.initiateScan();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.saomiao: {
                startScan();
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void getData() {
        CannyApi.JudgeIfRegister(SharedPrefOP.getInstance().getOpenId(),this);
    }

    // Get the results:
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //IntentIntegrator.REQUEST_CODE
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
            } else {
                String content = result.getContents();
                String bianhao = content.substring(content.indexOf("th=") + 3, content.indexOf("&doorpos"));
                String doorpos = content.substring(content.indexOf("&do") + 9, content.indexOf("&floornum"));
                if (bianhao != null) {
                    SharedPrefOP.getInstance().saveBianHao(bianhao);
                }
                if (doorpos.equals("f")) {
                    //前门
                    Intent intent = new Intent(MainActivity.this, CallElevatorActivity.class);
                    startActivity(intent);
                } else if (doorpos.equals("n")) {
                    //轿内
                    Intent intent = new Intent(MainActivity.this, ScanInTheCallActivity.class);
                    startActivity(intent);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exitBy2Click(); //调用双击退出函数
        }
        return false;
    }

    private void exitBy2Click() {
        Timer tExit;
        if (isExit == false) {
            isExit = true; // 准备退出
            Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            tExit = new Timer();
            tExit.schedule(new TimerTask() {
                @Override
                public void run() {
                    isExit = false; // 取消退出
                }
            }, 2000); // 如果2秒钟内没有按下返回键，则启动定时器取消掉刚才执行的任务
        } else {
            finish();
            System.exit(0);
        }
    }

    @Override
    public void onFailure(String method, String reason, Object other) {

    }

    @Override
    public void onSuccess(String method, Object result, Object other) {
            if (method=="JudgeIfRegister"){
                infoBean = (UserInfoBean) other;
                if (infoBean.isIsOwner()!=true){
                    rlUser.setVisibility(View.GONE);
                }else {
                    showUserMsg();
                }
            }
    }

    private void showUserMsg() {
        rlUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, UserActivity.class));
            }
        });
    }
}
