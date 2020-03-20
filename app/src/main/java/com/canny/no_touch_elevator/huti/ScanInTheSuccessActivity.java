package com.canny.no_touch_elevator.huti;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.util.SharedPrefOP;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ScanInTheSuccessActivity extends BaseActivity {

    @BindView(R.id.btn_back)
    Button btnBack;
    @BindView(R.id.tv_floorNum)
    TextView tvFloorNum;
    @BindView(R.id.tv_showMsg)
    TextView tvShowMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_scan_in_the_success;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        final Intent intent=getIntent();
        tvFloorNum.setText(intent.getStringExtra("right"));
        //tvShowMsg.setText(intent.getStringExtra("build_name")+" "+intent.getStringExtra("build_number"));
        tvShowMsg.setText(SharedPrefOP.getInstance().getBuildName() +" "+SharedPrefOP.getInstance().getBuildNumber());
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(ScanInTheSuccessActivity.this,ScanInTheCallActivity.class);
                //intent1.putExtra("bianhao",intent.getStringExtra("bianhao"));
                startActivity(intent1);
                finish();
            }
        });
    }

    @Override
    protected void getData() {

    }
}
