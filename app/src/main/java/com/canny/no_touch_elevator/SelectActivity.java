package com.canny.no_touch_elevator;

import android.content.Intent;
import android.widget.TextView;

import com.canny.no_touch_elevator.adapter.MyFragmentPagerAdapter;
import com.canny.no_touch_elevator.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectActivity extends BaseActivity {

    @BindView(R.id.tv_phoneNum)
    TextView tvPhoneNum;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.vp_select)
    CustomViewPager vpSelect;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_select;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);

        vpSelect.setScanScroll(false);
        List<Fragment> aList = new ArrayList<>(); //new一个List<Fragment>
        aList.add(new SelectContentFragment());
        aList.add(new CallDetailFragment());
        MyFragmentPagerAdapter mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), aList);
        vpSelect.setAdapter(mAdapter);
        vpSelect.setCurrentItem(0);

        Intent intent=getIntent();
        if (intent.getStringExtra("page2")!=null){
            vpSelect.setCurrentItem(Integer.parseInt(intent.getStringExtra("page2")));
        }

        tvName.setText(intent.getStringExtra("userName"));
        tvPhoneNum.setText(intent.getStringExtra("userPhone"));
    }

    @Override
    protected void getData() {

    }

}
