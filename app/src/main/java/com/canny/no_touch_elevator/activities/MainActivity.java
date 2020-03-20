package com.canny.no_touch_elevator.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.canny.no_touch_elevator.BuildingActivity;
import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.SelectActivity;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.huti.CallElevatorActivity;
import com.canny.no_touch_elevator.huti.ScanInTheCallActivity;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity {

    @BindView(R.id.btn_select)
    Button btnSelect;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        startScan();

        final Intent intent = getIntent();
        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(MainActivity.this, SelectActivity.class);
                intent1.putExtra("userName", intent.getStringExtra("name"));
                intent1.putExtra("userPhone", intent.getStringExtra("phone"));
                startActivity(intent1);
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
                if (bianhao!=null){
                    SharedPrefOP.getInstance().saveBianHao(bianhao);
                }
                if (doorpos.equals("f")) {
                    //前门
                    Intent intent = new Intent(MainActivity.this, CallElevatorActivity.class);
                    //intent.putExtra("bianhao", bianhao);
                    startActivity(intent);
                } else if (doorpos.equals("n")) {
                    //轿内
                    Intent intent = new Intent(MainActivity.this, ScanInTheCallActivity.class);
                    //intent.putExtra("bianhao", bianhao);
                    startActivity(intent);
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
