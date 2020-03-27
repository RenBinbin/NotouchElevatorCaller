package com.canny.no_touch_elevator;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.canny.no_touch_elevator.adapter.GroupResultAdapter;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;
import com.canny.no_touch_elevator.webapi.response.GroupMsgBean;
import com.canny.no_touch_elevator.webapi.response.GroupResultBean;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class GroupElevatorActivity extends BaseActivity implements CannyCallback {

    @BindView(R.id.lv_group_result)
    ListView lvGroupResult;
    @BindView(R.id.tv_showMsg)
    TextView tvShowMsg;
    @BindView(R.id.rf_group)
    SwipeRefreshLayout rfGroup;

    private List<GroupMsgBean.MsgBean> data=new ArrayList<>();
    private List<String> direction=new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_group_elevator;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        direction.add("静止");
        direction.add("上行未运行");
        direction.add("上行运行中");
        direction.add("下行运行中");
        direction.add("下行未运行");
        CannyApi.GroupRunStateResult(SharedPrefOP.getInstance().getOpenId(), SharedPrefOP.getInstance().getBianHao(),
                SharedPrefOP.getInstance().getRequestId() + "", this);
        tvShowMsg.setText(SharedPrefOP.getInstance().getBuildName()+" "+ SharedPrefOP.getInstance().getBuildNumber());

        rfGroup.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                data.clear();
                //模拟网络请求需要3000毫秒，请求完成，设置setRefreshing 为false
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        rfGroup.setRefreshing(false);
                        CannyApi.GroupRunStateResult(SharedPrefOP.getInstance().getOpenId(), SharedPrefOP.getInstance().getBianHao(),
                                SharedPrefOP.getInstance().getRequestId() + "",GroupElevatorActivity.this);
                    }
                }, 2000);

            }
        });
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onFailure(String method, String reason, Object other) {

    }

    @Override
    public void onSuccess(String method, Object result, Object other) {
        if (method=="GroupRunStateResult"){
            GroupResultBean resultBean= (GroupResultBean) result;
            if (resultBean!=null){
                String msg=resultBean.getMsg();
                try {
                    JSONArray jsonArray = new JSONArray(msg);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        GroupMsgBean.MsgBean groupMsgBean=new GroupMsgBean.MsgBean();
                        JSONObject jsonObject = (JSONObject)jsonArray.opt(i);
                        groupMsgBean.setIndex(jsonObject.getInt("Index"));
                        groupMsgBean.setFloor(jsonObject.getString("Floor"));
                        groupMsgBean.setDirection(direction.get(jsonObject.getInt("Direction")));
                       // groupMsgBean.setOpening(jsonObject.getBoolean("IsOpening"));
                        //groupMsgBean.setClosing(jsonObject.getBoolean("IsClosing"));
                        //groupMsgBean.setDoorLock(jsonObject.getBoolean("IsDoorLock"));
                        //groupMsgBean.setBak(jsonObject.getBoolean("Bak"));
                        //groupMsgBean.setInGroup(jsonObject.getBoolean("IsInGroup"));
                        data.add(groupMsgBean);
                        for (int j = 0; j < data.size(); j++) {
                            if (data.get(j).getFloor().equals("")){
                                data.remove(j);
                            }
                        }

                        GroupResultAdapter resultAdapter=new GroupResultAdapter(this,data);
                        lvGroupResult.setAdapter(resultAdapter);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
