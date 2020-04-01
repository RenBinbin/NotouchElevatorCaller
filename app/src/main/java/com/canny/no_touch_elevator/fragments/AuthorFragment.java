package com.canny.no_touch_elevator.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.canny.no_touch_elevator.MyApp;
import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseFragment;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;
import com.canny.no_touch_elevator.webapi.response.UserInfoBean;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AuthorFragment extends BaseFragment implements CannyCallback{

    @BindView(R.id.rl_apply)
    RelativeLayout rlApply;
    @BindView(R.id.rl_user)
    RelativeLayout rlUser;

    public AuthorFragment() {

    }

    public static AuthorFragment newInstance() {
        AuthorFragment fragment = new AuthorFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_author;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        rlApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),ApplyFragment.class));
            }
        });
    }

    @Override
    protected void getData() {
        CannyApi.JudgeIfRegister(SharedPrefOP.getInstance().getOpenId(),this);
    }

    @Override
    public void onFailure(String method, String reason, Object other) {

    }

    @Override
    public void onSuccess(String method, Object result, Object other) {

    }
}
