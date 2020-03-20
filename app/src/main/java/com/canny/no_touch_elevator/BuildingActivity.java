package com.canny.no_touch_elevator;

import android.content.Context;
import android.telephony.TelephonyManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import com.canny.no_touch_elevator.base.BaseActivity;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.ButterKnife;

public class BuildingActivity extends BaseActivity{

    @BindView(R.id.lv_build)
    ListView lvBuild;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.sr_build)
    SwipeRefreshLayout srBuild;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_building;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        lvBuild.setEmptyView(findViewById(R.id.layout_empty));
        srBuild.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                srBuild.setRefreshing(false);
                TelephonyManager tm = (TelephonyManager) MyApp.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
                //用户小区及电梯信息
//                OkGo.post(Constants.UserBuildingUrl)
//                        //.tag(context)//唯一标示：取消请求需要用到
//                        .params("openId", "Canny"+tm.getDeviceId())
//                        .execute(new MyCallback() {
//                            @Override
//                            public void onSuccess(String s, okhttp3.Call call, Response response) {
//                                Log.e("userBuilding", s);
//                                Gson gson=new Gson();
//                                if (s.contains("1")){
//                                    UserBuildResponse buildResponse = gson.fromJson(s, UserBuildResponse.class);
//                                    if (buildResponse.getResult()==1){
//                                        List<UserBuildResponse.MsgBean> list = buildResponse.getMsg();
//                                        BuildInformAdapter adapter = new BuildInformAdapter(BuildingActivity.this, list);
//                                        lvBuild.setAdapter(adapter);
//                                    }
//                                }else {
//                                    Toast.makeText(MyApp.getApplication(),s,Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void MyOnError(okhttp3.Call call, Response response, Exception e, String errorMsg) {
//                                Toast.makeText(MyApp.getApplication(),e.toString(),Toast.LENGTH_LONG).show();
//                            }
//                        });
            }
        });

        progressBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TelephonyManager tm = (TelephonyManager) MyApp.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
                //用户小区及电梯信息
//                OkGo.post(Constants.UserBuildingUrl)
//                        //.tag(context)//唯一标示：取消请求需要用到
//                        .params("openId", "Canny"+tm.getDeviceId())
//                        .execute(new MyCallback() {
//                            @Override
//                            public void onSuccess(String s, okhttp3.Call call, Response response) {
//                                Log.e("userBuilding", s);
//                                Gson gson=new Gson();
//                                if (s.contains("1")){
//                                    UserBuildResponse buildResponse = gson.fromJson(s, UserBuildResponse.class);
//                                    if (buildResponse.getResult()==1){
//                                        List<UserBuildResponse.MsgBean> list = buildResponse.getMsg();
//                                        BuildInformAdapter adapter = new BuildInformAdapter(BuildingActivity.this, list);
//                                        lvBuild.setAdapter(adapter);
//                                    }
//                                }else {
//                                    Toast.makeText(MyApp.getApplication(),s,Toast.LENGTH_LONG).show();
//                                }
//                            }
//
//                            @Override
//                            public void MyOnError(okhttp3.Call call, Response response, Exception e, String errorMsg) {
//                                Toast.makeText(MyApp.getApplication(),e.toString(),Toast.LENGTH_LONG).show();
//                            }
//                        });
            }
        });
    }

    @Override
    protected void getData() {
        TelephonyManager tm = (TelephonyManager) MyApp.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        //用户小区及电梯信息
//        OkGo.post(Constants.UserBuildingUrl)
//                //.tag(context)//唯一标示：取消请求需要用到
//                .params("openId", "Canny"+tm.getDeviceId())
//                .execute(new MyCallback() {
//                    @Override
//                    public void onSuccess(String s, okhttp3.Call call, Response response) {
//                        Log.e("userBuilding", s);
//                        Gson gson=new Gson();
//                        if (s.contains("1")){
//                            UserBuildResponse buildResponse = gson.fromJson(s, UserBuildResponse.class);
//                            if (buildResponse.getResult()==1){
//                                List<UserBuildResponse.MsgBean> list = buildResponse.getMsg();
//                                BuildInformAdapter adapter = new BuildInformAdapter(BuildingActivity.this, list);
//                                lvBuild.setAdapter(adapter);
//                            }
//                        }else {
//                            Toast.makeText(MyApp.getApplication(),s,Toast.LENGTH_LONG).show();
//                        }
//                    }
//
//                    @Override
//                    public void MyOnError(okhttp3.Call call, Response response, Exception e, String errorMsg) {
//                        Toast.makeText(MyApp.getApplication(),e.toString(),Toast.LENGTH_LONG).show();
//                    }
//                });
    }
}
