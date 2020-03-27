package com.canny.no_touch_elevator.fragments;

import android.content.Intent;
import android.view.View;
import android.widget.RelativeLayout;

import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.SelectActivity;
import com.canny.no_touch_elevator.base.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SelectContentFragment extends BaseFragment {

    @BindView(R.id.rl_call)
    RelativeLayout rlCall;
    @BindView(R.id.rl_renzheng)
    RelativeLayout rlRenzheng;
    @BindView(R.id.rl_set)
    RelativeLayout rlSet;
    @BindView(R.id.rl_apply)
    RelativeLayout rlApply;


    public SelectContentFragment() {
    }

    public static SelectContentFragment newInstance() {
        SelectContentFragment fragment = new SelectContentFragment();
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_call;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);

        rlCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                Intent intent=new Intent(getContext(), SelectActivity.class);
                intent.putExtra("page2","1");
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });

        rlApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vw) {
                Intent intent=new Intent(getContext(),SelectActivity.class);
                intent.putExtra("page3","2");
                startActivity(intent);
                getActivity().overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
        });
    }

    @Override
    protected void getData() {

    }

}
