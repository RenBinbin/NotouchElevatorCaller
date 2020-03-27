package com.canny.no_touch_elevator;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.canny.no_touch_elevator.adapter.MyFragmentPagerAdapter;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.fragments.ApplyFragment;
import com.canny.no_touch_elevator.fragments.CallDetailFragment;
import com.canny.no_touch_elevator.fragments.SelectContentFragment;
import com.canny.no_touch_elevator.util.SharedPrefOP;

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
    @BindView(R.id.rl_inform)
    RelativeLayout rlInform;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_select;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        Intent intent=getIntent();
        vpSelect.setScanScroll(false);
        List<Fragment> aList = new ArrayList<>(); //new一个List<Fragment>
        aList.add(new SelectContentFragment());
        aList.add(new CallDetailFragment());
        aList.add(new ApplyFragment());
        MyFragmentPagerAdapter mAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), aList);
        vpSelect.setAdapter(mAdapter);
        vpSelect.setCurrentItem(0);


        if (intent.getStringExtra("page2")!=null){
            vpSelect.setCurrentItem(Integer.parseInt(intent.getStringExtra("page2")));
            rlInform.setVisibility(View.GONE);
        }else if (intent.getStringExtra("page3")!=null){
            vpSelect.setCurrentItem(Integer.parseInt(intent.getStringExtra("page3")));
            rlInform.setVisibility(View.GONE);
        }

        tvName.setText(SharedPrefOP.getInstance().getUsername());
        tvPhoneNum.setText(SharedPrefOP.getInstance().getPhoneNum());
    }

    @Override
    protected void getData() {

    }

}
