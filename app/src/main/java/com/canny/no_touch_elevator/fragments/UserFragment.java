package com.canny.no_touch_elevator.fragments;

import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseFragment;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFragment extends BaseFragment {

    @BindView(R.id.tv_url)
    TextView tvUrl;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_author)
    TextView tvAuthor;
    @BindView(R.id.tv_author_elevator)
    TextView tvAuthorElevator;
    @BindView(R.id.tv_floor)
    TextView tvFloor;
    @BindView(R.id.tv_author_floor)
    TextView tvAuthorFloor;
    @BindView(R.id.tv_num)
    TextView tvNum;
    @BindView(R.id.tv_author_num)
    TextView tvAuthorNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_time_show)
    TextView tvTimeShow;
    @BindView(R.id.btn_ok)
    Button btnOk;

    public static UserFragment newInstance() {
        UserFragment fragment = new UserFragment();
        return fragment;
    }


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        tvUrl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("http://i.4001882367.com:81/login.aspx");
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.setData(uri);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void getData() {

    }
}
