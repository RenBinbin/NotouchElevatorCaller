package com.canny.no_touch_elevator;

import butterknife.BindView;
import butterknife.ButterKnife;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.util.List2String;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;
import com.canny.no_touch_elevator.webapi.response.EtorFloorInfoBean;
import com.canny.no_touch_elevator.webapi.response.UserInfoBean;
import com.canny.no_touch_elevator.wheelview.NumericWheelAdapter;
import com.canny.no_touch_elevator.wheelview.NumericWheelAdapter1;
import com.canny.no_touch_elevator.wheelview.WheelView;
import com.codert.rtmulticheckdialog_module.RTMultiCheckDialog;
import com.google.zxing.common.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

public class UserActivity extends BaseActivity implements CannyCallback {

    @BindView(R.id.tv_url)
    TextView tvUrl;
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
    @BindView(R.id.et_author_num)
    EditText etAuthorNum;
    @BindView(R.id.tv_time)
    TextView tvTime;
    @BindView(R.id.tv_time_show)
    TextView tvTimeShow;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private WheelView year;
    private WheelView month;
    private WheelView day;
    private WheelView hour;
    private WheelView mins;
    private UserInfoBean infoBean;
    private WheelView wvVisitor;
    private List<String> data=new ArrayList<>();
    private List<String> floor=new ArrayList<>();
    private List<String> isChecked=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        floor.add("全选");
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
        tvTimeShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow();
            }
        });

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPhone.getText().toString().length()!=11){
                    Toast.makeText(UserActivity.this,"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                }else if (tvAuthorElevator.getText().toString().equals("")){
                    Toast.makeText(UserActivity.this,"请选授权电梯",Toast.LENGTH_SHORT).show();
                }else if (tvAuthorFloor.getText().toString().equals("")){
                    Toast.makeText(UserActivity.this,"请选择授权楼层",Toast.LENGTH_SHORT).show();
                }else {
                    CannyApi.AuthUserReqest(SharedPrefOP.getInstance().getOpenId(),
                            infoBean.getOwnerList().get(wvVisitor.getCurrentItem()).getEtor_bianhao(),etPhone.getText().toString(),tvAuthorFloor.getText().toString(),
                            Integer.parseInt(etAuthorNum.getText().toString()),tvTimeShow.getText().toString(),"",UserActivity.this);
                }
            }
        });

        tvAuthorFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvAuthorElevator.getText().toString().equals("")){
                    Toast.makeText(UserActivity.this,"请选授权电梯",Toast.LENGTH_SHORT).show();
                }else {
                    showElevatorFloors();
                }
            }
        });
    }

    private void timeShow() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.date_layout);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);

        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        int curMonth = c.get(Calendar.MONTH) + 1;//通过Calendar算出的月数要+1
        int curDate = c.get(Calendar.DATE);
        int curHour = c.get(Calendar.HOUR_OF_DAY)+1;
        int curMin = c.get(Calendar.MINUTE);
        //int curSec = c.get(Calendar.SECOND);

        year = (WheelView) window.findViewById(R.id.year);
        initYear();
        month = (WheelView) window.findViewById(R.id.month);
        initMonth();
        day = (WheelView) window.findViewById(R.id.day);
        initDay(curYear,curMonth);
        hour = (WheelView) window.findViewById(R.id.hour);
        initHour();
        mins = (WheelView) window.findViewById(R.id.min);
        initMins();

        // 设置当前时间
        year.setCurrentItem(curYear - 2015);
        month.setCurrentItem(curMonth - 1);
        day.setCurrentItem(curDate - 1);
        hour.setCurrentItem(curHour);
        mins.setCurrentItem(curMin);

        year.setVisibleItems(7);
        month.setVisibleItems(7);
        day.setVisibleItems(7);
        hour.setVisibleItems(7);
        mins.setVisibleItems(7);


        // 设置监听
        TextView ok = (TextView) window.findViewById(R.id.set);
        TextView cancel = (TextView) window.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* String str = String.format(Locale.CHINA,"%4d%2d%2d%2d%2d%2d",year.getCurrentItem()+1950,month.getCurrentItem()+1,day.getCurrentItem()+1,
                        hour.getCurrentItem(),mins.getCurrentItem(),seconds.getCurrentItem());*/
                int years=year.getCurrentItem()+2015;
                int months=month.getCurrentItem()+1;
                int days=day.getCurrentItem()+1;
                int hours=hour.getCurrentItem();
                int minute=mins.getCurrentItem();
                // int second=seconds.getCurrentItem();
                //int year=Integer.parseInt(Integer.toString(years).substring(1,4));

                tvTimeShow.setText(years+"-"+months+"-"+days+" "+hours+":"+minute);
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        LinearLayout cancelLayout = (LinearLayout) window.findViewById(R.id.view_none);
        cancelLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.cancel();
                return false;
            }
        });
    }

    /**
     * 初始化年
     */
    private void initYear() {
        NumericWheelAdapter1 numericWheelAdapter = new NumericWheelAdapter1(this,2015, 2025);
        numericWheelAdapter.setLabel("");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        year.setViewAdapter(numericWheelAdapter);
        year.setCyclic(false);
    }

    /**
     * 初始化月
     */
    private void initMonth() {
        NumericWheelAdapter1 numericWheelAdapter = new NumericWheelAdapter1(this,1, 12, "%02d");
        numericWheelAdapter.setLabel("");
        month.setViewAdapter(numericWheelAdapter);
        month.setCyclic(false);
    }

    /**
     * 初始化天
     */
    private void initDay(int arg1, int arg2) {
        NumericWheelAdapter1 numericWheelAdapter=new NumericWheelAdapter1(this,1, getDay(arg1, arg2), "%02d");
        numericWheelAdapter.setLabel("");
        day.setViewAdapter(numericWheelAdapter);
        day.setCyclic(false);
    }

    /**
     * 初始化时
     */
    private void initHour() {
        NumericWheelAdapter1 numericWheelAdapter = new NumericWheelAdapter1(this,0, 23, "%02d");
        numericWheelAdapter.setLabel("");
        hour.setViewAdapter(numericWheelAdapter);
        hour.setCyclic(false);
    }

    /**
     * 初始化分
     */
    private void initMins() {
        NumericWheelAdapter1 numericWheelAdapter = new NumericWheelAdapter1(this,0, 59, "%02d");
        numericWheelAdapter.setLabel("");
        mins.setViewAdapter(numericWheelAdapter);
        mins.setCyclic(false);
    }

    private int getDay(int year, int month) {
        int day = 30;
        boolean flag = false;
        switch (year % 4) {
            case 0:
                flag = true;
                break;
            default:
                flag = false;
                break;
        }
        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                day = 31;
                break;
            case 2:
                day = flag ? 29 : 28;
                break;
            default:
                day = 30;
                break;
        }
        return day;
    }

    private void elevatorShowName() {
        showElevatorDialog();
    }

    private void showElevatorDialog() {
        final AlertDialog dialog = new AlertDialog.Builder(this).create();
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.visitor_style_layout);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);

        wvVisitor = (WheelView) window.findViewById(R.id.wv_visitor);
        initElevatorData();
        wvVisitor.setVisibleItems(7);

        // 设置监听
        TextView ok = (TextView) window.findViewById(R.id.set);
        TextView cancel = (TextView) window.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvAuthorElevator.setText(data.get(wvVisitor.getCurrentItem()));
                if (infoBean.getOwnerList()!=null){
                    CannyApi.GetEtorFloorInfo(SharedPrefOP.getInstance().getOpenId(),
                            infoBean.getOwnerList().get(wvVisitor.getCurrentItem()).getEtor_bianhao(),UserActivity.this);
                }
                dialog.cancel();
            }
        });
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        LinearLayout cancelLayout = (LinearLayout) window.findViewById(R.id.view_none);
        cancelLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                dialog.cancel();
                return false;
            }
        });
    }

    private void initElevatorData() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(this,data);
        numericWheelAdapter.setLabel("");
        //numericWheelAdapter.setTextSize(15);  设置字体大小
        wvVisitor.setViewAdapter(numericWheelAdapter);
        wvVisitor.setCyclic(false);
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
        if(method=="JudgeIfRegister"){
            infoBean = (UserInfoBean) other;
            for (int i = 0; i < infoBean.getOwnerList().size(); i++) {
                data.add(infoBean.getOwnerList().get(i).getBuild_number()+" "+infoBean.getOwnerList().get(i).getEtor_index_show());
            }
            tvAuthorElevator.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    elevatorShowName();
                }
            });
        }else if (method=="GetEtorFloorInfo"){
            EtorFloorInfoBean floorInfoBean= (EtorFloorInfoBean) other;
            String[] floorAry = floorInfoBean.getFloorName().replace(" ", "").split(",");
            for (int i = 0; i <floorAry.length ; i++) {
                floor.add(floorAry[i]);
            }
        }
    }

    private void showElevatorFloors() {
        RTMultiCheckDialog floorDialog= new RTMultiCheckDialog(this,0.7,0.6)
                .setTitleText("请选择")
                //.setIcon(R.drawable.image)
                .setConfirmText("确定")
                .setCancelText("取消")
                .setConfirmOnclicListener(new RTMultiCheckDialog.OnMultiCheckClickListener() {
                    @Override
                    public void onClick(RTMultiCheckDialog rtMultiCheckDialog) {
                        //点击确认事件
                        isChecked.clear();
                        for (int i = 1; i <rtMultiCheckDialog.getItemChecked().length ; i++) {
                            if (rtMultiCheckDialog.getItemChecked()[i]==true){
                               isChecked.add(floor.get(i));
                            }
                        }
                        tvAuthorFloor.setText(List2String.ListToString(isChecked));
                        rtMultiCheckDialog.dismiss();
                    }
                })
                .setCancelOnclicListener(new RTMultiCheckDialog.OnMultiCheckClickListener() {
                    @Override
                    public void onClick(RTMultiCheckDialog rtMultiCheckDialog) {
                        //点击取消事件
                        rtMultiCheckDialog.dismiss();
                    }
                })
                .setItemNames(floor);
        //显示dialog
        floorDialog.show();
    }
}
