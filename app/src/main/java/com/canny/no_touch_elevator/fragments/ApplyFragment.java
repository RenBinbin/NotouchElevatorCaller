package com.canny.no_touch_elevator.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseFragment;
import com.canny.no_touch_elevator.wheelview.NumericWheelAdapter;
import com.canny.no_touch_elevator.wheelview.NumericWheelAdapter1;
import com.canny.no_touch_elevator.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ApplyFragment extends BaseFragment {

    @BindView(R.id.et_phone)
    EditText etPhone;
    @BindView(R.id.tv_style_select)
    TextView tvStyleSelect;
    @BindView(R.id.tv_author_elevator)
    TextView tvAuthorElevator;
    @BindView(R.id.tv_author_floor)
    TextView tvAuthorFloor;
    @BindView(R.id.tv_author_num)
    TextView tvAuthorNum;
    @BindView(R.id.tv_time_show)
    TextView tvTimeShow;
    @BindView(R.id.btn_ok)
    Button btnOk;

    private WheelView year;
    private WheelView month;
    private WheelView day;
    private WheelView hour;
    private WheelView mins;
    private WheelView wvVisitor;
    private List<String> data=new ArrayList<>();
    public ApplyFragment() {

    }

    public static ApplyFragment newInstance() {
        ApplyFragment fragment = new ApplyFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_apply;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this,view);
        data.add("快递");
        data.add("外卖");
        data.add("商务");
        data.add("亲友");
        data.add("其他");
        Calendar c = Calendar.getInstance();
        int curYear = c.get(Calendar.YEAR);
        //通过Calendar算出的月数要+1
        int curMonth = c.get(Calendar.MONTH) + 1;
        int curDate = c.get(Calendar.DATE);
        int curHour = c.get(Calendar.HOUR_OF_DAY)+1;
        int curMin = c.get(Calendar.MINUTE);

        tvTimeShow.setText(curYear+"-"+curMonth+"-"+curDate+" "+curHour+":"+curMin);
        tvTimeShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeShow();
            }
        });
        tvStyleSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                visitorStyle();
            }
        });
        tvAuthorElevator.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        tvAuthorFloor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvAuthorElevator.getText().toString().trim()==null){
                    showDialog();
                }else {

                }
            }
        });
        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etPhone.getText().toString().length()!=11){
                    Toast.makeText(getContext(),"请输入正确的手机号",Toast.LENGTH_SHORT).show();
                }else if (tvStyleSelect.getText().toString().equals("")){
                    Toast.makeText(getContext(),"请选择访客类型",Toast.LENGTH_SHORT).show();
                }else if (tvAuthorElevator.getText().toString().equals("")){
                    Toast.makeText(getContext(),"请选择授权电梯",Toast.LENGTH_SHORT).show();
                }else if (tvAuthorFloor.getText().toString().equals("")){
                    Toast.makeText(getContext(),"请选择授权楼层",Toast.LENGTH_SHORT).show();
                }else {

                }
            }
        });
    }

    private void showDialog() {
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(getActivity());
        alterDiaglog.setTitle("提示");//文字
        alterDiaglog.setMessage("请先选择授权电梯");//提示消息
        //积极的选择
        alterDiaglog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //显示
        alterDiaglog.show();
    }

    private void visitorStyle() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();
        Window window = dialog.getWindow();
        // 设置布局
        window.setContentView(R.layout.visitor_style_layout);
        // 设置宽高
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // 设置弹出的动画效果
        window.setWindowAnimations(R.style.AnimBottom);

        wvVisitor = (WheelView) window.findViewById(R.id.wv_visitor);
        initVisitorStyle();
        wvVisitor.setVisibleItems(7);

        // 设置监听
        TextView ok = (TextView) window.findViewById(R.id.set);
        TextView cancel = (TextView) window.findViewById(R.id.cancel);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvStyleSelect.setText(data.get(wvVisitor.getCurrentItem()));
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

    private void initVisitorStyle() {
        NumericWheelAdapter numericWheelAdapter = new NumericWheelAdapter(getActivity(),data);
        numericWheelAdapter.setLabel("");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        wvVisitor.setViewAdapter(numericWheelAdapter);
        wvVisitor.setCyclic(false);
    }

    private void timeShow() {
        final AlertDialog dialog = new AlertDialog.Builder(getActivity()).create();
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
     *
     * @param year
     * @param month
     * @return
     */
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
    /**
     * 初始化年
     */
    private void initYear() {
        NumericWheelAdapter1 numericWheelAdapter = new NumericWheelAdapter1(getActivity(),2015, 2025);
        numericWheelAdapter.setLabel("");
        //		numericWheelAdapter.setTextSize(15);  设置字体大小
        year.setViewAdapter(numericWheelAdapter);
        year.setCyclic(false);
    }

    /**
     * 初始化月
     */
    private void initMonth() {
        NumericWheelAdapter1 numericWheelAdapter = new NumericWheelAdapter1(getActivity(),1, 12, "%02d");
        numericWheelAdapter.setLabel("");
        month.setViewAdapter(numericWheelAdapter);
        month.setCyclic(false);
    }

    /**
     * 初始化天
     */
    private void initDay(int arg1, int arg2) {
        NumericWheelAdapter1 numericWheelAdapter=new NumericWheelAdapter1(getActivity(),1, getDay(arg1, arg2), "%02d");
        numericWheelAdapter.setLabel("");
        day.setViewAdapter(numericWheelAdapter);
        day.setCyclic(false);
    }

    /**
     * 初始化时
     */
    private void initHour() {
        NumericWheelAdapter1 numericWheelAdapter = new NumericWheelAdapter1(getActivity(),0, 23, "%02d");
        numericWheelAdapter.setLabel("");
        hour.setViewAdapter(numericWheelAdapter);
        hour.setCyclic(false);
    }

    /**
     * 初始化分
     */
    private void initMins() {
        NumericWheelAdapter1 numericWheelAdapter = new NumericWheelAdapter1(getActivity(),0, 59, "%02d");
        numericWheelAdapter.setLabel("");
        mins.setViewAdapter(numericWheelAdapter);
        mins.setCyclic(false);
    }

    @Override
    protected void getData() {

    }
}
