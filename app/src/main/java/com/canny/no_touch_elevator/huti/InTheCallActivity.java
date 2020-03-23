package com.canny.no_touch_elevator.huti;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.util.WeiboDialogUtils;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;
import com.canny.no_touch_elevator.webapi.customcallback.CannyResponse;
import com.canny.no_touch_elevator.webapi.response.EtorFloorInfoBean;
import com.canny.no_touch_elevator.webapi.response.LoginResponse;
import com.canny.no_touch_elevator.wheelview.NumericWheelAdapter;
import com.canny.no_touch_elevator.wheelview.OnWheelClickedListener;
import com.canny.no_touch_elevator.wheelview.WheelView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InTheCallActivity extends BaseActivity implements CannyCallback,AMapLocationListener{

    @BindView(R.id.wv_neihu)
    WheelView wvNeihu;
    @BindView(R.id.btn_ok)
    Button btnOk;
    @BindView(R.id.btn_waihu)
    Button btnWaihu;
    @BindView(R.id.tv_showMsg)
    TextView tvShowMsg;

    private String[] floorAry;
    private String[] forbidAry1;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    //public AMapLocationListener mLocationListener = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private Timer t;
    private int index=0;
    public static Dialog mWeiboDialog;
    private String[] floorAry1;
    private String[] forbidAry;
    private List<Integer> newIndex=new ArrayList<>();
    private Intent intent;
    private EtorFloorInfoBean informResponse;
    private Handler handler;
    private NetworkInfo mNetworkInfo;
    private String openId;
    private String etor_bianhao;
    private int destNum;
    private List<String> arrList;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_in_the_call;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        t = new Timer();
        handler = new Handler();
        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        initLocationOption();
        intent = getIntent();
        floorAry = intent.getStringArrayExtra("floorAry");
        forbidAry1 = intent.getStringArrayExtra("forbidAry1");

        tvShowMsg.setText(SharedPrefOP.getInstance().getBuildName()+" "+SharedPrefOP.getInstance().getBuildNumber());
        ShowContent();
    }

    private void initLocationOption() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(3000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
    }

    private void ShowContent() {
        initDestFloor();
        wvNeihu.setVisibleItems(7);
        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mLocationClient.startLocation();
                mWeiboDialog = WeiboDialogUtils.createLoadingDialog(InTheCallActivity.this, "传输中...");
                mHandler.sendEmptyMessageDelayed(1, 30000);
                if (mNetworkInfo ==null){
                    mWeiboDialog.dismiss();
                    changePageState(PageState.ERROR);
                }
            }
        });

        btnWaihu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(InTheCallActivity.this, CallElevatorActivity.class);
                //intent1.putExtra("bianhao",intent.getStringExtra("bianhao"));
                startActivity(intent1);
                finish();
            }
        });
    }

    private void initDestFloor() {
        if (floorAry!=null){
            showPermit1();
            NumericWheelAdapter numericWheelAdapter =
                    new NumericWheelAdapter(this,arrList);
            numericWheelAdapter.setLabel("");
            wvNeihu.setViewAdapter(numericWheelAdapter);
            wvNeihu.setCyclic(false);
            WheelOnClick();
        }
    }

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    WeiboDialogUtils.closeDialog(mWeiboDialog);
                    break;
            }
        }
    };

    @Override
    protected void getData() {
        openId = SharedPrefOP.getInstance().getOpenId();
        etor_bianhao = SharedPrefOP.getInstance().getBianHao();
        CannyApi.GetEtorFloorInfo(openId, etor_bianhao,this);
    }

    @Override
    public void onFailure(String method, String reason, Object other) {
        if (method=="CallRequest"){
            mWeiboDialog.dismiss();
        }
    }

    @Override
    public void onSuccess(String method, Object result, Object other) {
        if (method == "CallRequest") {
            CannyResponse cannyResponse= (CannyResponse) other;
            final String requestId = cannyResponse.msg.toString();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    CannyApi.PhoneStatus(SharedPrefOP.getInstance().getOpenId(),
                            SharedPrefOP.getInstance().getBianHao(), requestId, InTheCallActivity.this);
                    index++;
                    if(index>15) {
                        t.cancel();
                        index=0;
                        handler.post(runAbleUi);
                    }
                }
            }, 0, 2000);// 首次运行,延迟0毫秒,循环间隔1000毫秒
        } else if (method == "phoneStatus") {
            mWeiboDialog.dismiss();
            t.cancel();
            index=0;
            Intent intent1 = new Intent(this, InTheCallSucessActivity.class);
            intent1.putExtra("right", wvNeihu.getCurrentItem() + 1 + "");
            //intent1.putExtra("build_name",informResponse.get);
            //intent1.putExtra("build_number",informResponse.getMsg().getBuild_number());
            //intent1.putExtra("bianhao",intent.getStringExtra("bianhao"));
            startActivity(intent1);
            finish();
        }else if (method=="GetEtorFloorInfo"){
            informResponse = (EtorFloorInfoBean) result;
            String floorName = informResponse.getFloorName();
            String forbid= informResponse.getFloorForbidden();
            forbidAry=forbid.replace(" ", "").split(",");
            floorAry1 = floorName.replace(" ", "").split(",");
            //List<String> list1= Arrays.asList(forbidAry);
            showPermit();

        }
    }

    private void showPermit() {
        arrList = new ArrayList<>();
        if (floorAry==null){
            for (int i = 0; i < forbidAry.length; i++) {
                if (forbidAry[i].equals("1")){
                    newIndex.add(i);
                }
            }
            for (int i = 0; i < newIndex.size(); i++) {
                arrList.add(floorAry1[newIndex.get(i)]);
            }
            NumericWheelAdapter numericWheelAdapter =
                    new NumericWheelAdapter(this, arrList);
            numericWheelAdapter.setLabel("");
            wvNeihu.setViewAdapter(numericWheelAdapter);
            wvNeihu.setCyclic(false);
        }
    }

    private void showPermit1() {
        arrList = new ArrayList<>();
        for (int i = 0; i < forbidAry1.length; i++) {
            if (forbidAry1[i].equals("1")){
                newIndex.add(i);
            }
        }
        for (int i = 0; i < newIndex.size(); i++) {
            arrList.add(floorAry[newIndex.get(i)]);
        }
        NumericWheelAdapter numericWheelAdapter =
                new NumericWheelAdapter(this, arrList);
        numericWheelAdapter.setLabel("");
        wvNeihu.setViewAdapter(numericWheelAdapter);
        wvNeihu.setCyclic(false);
    }

    // 构建Runnable对象，在runnable中更新界面
    Runnable runAbleUi=new Runnable(){
        @Override
        public void run() {
            //更新界面
            Toast.makeText(InTheCallActivity.this,"系统繁忙,请重试",Toast.LENGTH_SHORT).show();
            mWeiboDialog.dismiss();
        }
    };

    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (amapLocation != null) {
            StringBuffer sb = new StringBuffer();
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息

                destNum = wvNeihu.getCurrentItem() + 1;
                CannyApi.CallRequest(SharedPrefOP.getInstance().getOpenId(),SharedPrefOP.getInstance().getBianHao(),
                        amapLocation.getLatitude() + "", amapLocation.getLongitude() + "", 0xff,
                        "1", destNum + "", InTheCallActivity.this);//呼梯请求

//                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
//                amapLocation.getLatitude();//获取纬度
//                amapLocation.getLongitude();//获取经度
//                amapLocation.getAccuracy();//获取精度信息


//                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                Date date = new Date(amapLocation.getTime());
//                df.format(date);//定位时间
//                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
//                amapLocation.getCountry();//国家信息
//                amapLocation.getProvince();//省信息
//                amapLocation.getCity();//城市信息
//                amapLocation.getDistrict();//城区信息
//                amapLocation.getStreet();//街道信息
//                amapLocation.getStreetNum();//街道门牌号信息
//                amapLocation.getCityCode();//城市编码
//                amapLocation.getAdCode();//地区编码

//                sb.append("定位成功" + "\n");
//                sb.append("定位类型: " + amapLocation.getLocationType() + "\n");
//                sb.append("经    度    : " + amapLocation.getLongitude() + "\n");
//                sb.append("纬    度    : " + amapLocation.getLatitude() + "\n");
//                sb.append("精    度    : " + amapLocation.getAccuracy() + "米" + "\n");
//                sb.append("提供者    : " + amapLocation.getProvider() + "\n");

                if (amapLocation.getProvider().equalsIgnoreCase(
                        android.location.LocationManager.GPS_PROVIDER)) {
                    // 以下信息只有提供者是GPS时才会有
                    //  sb.append("速    度    : " + amapLocation.getSpeed() + "米/秒" + "\n");
                    // sb.append("角    度    : " + amapLocation.getBearing() + "\n");
                    // 获取当前提供定位服务的卫星个数
                    // sb.append("星    数    : "
                    // + amapLocation.getSatellites() + "\n");
                } else {
                    // 提供者是GPS时是没有以下信息的
//                    sb.append("国    家    : " + amapLocation.getCountry() + "\n");
//                    sb.append("省            : " + amapLocation.getProvince() + "\n");
//                    sb.append("市            : " + amapLocation.getCity() + "\n");
//                    sb.append("城市编码 : " + amapLocation.getCityCode() + "\n");
//                    sb.append("区            : " + amapLocation.getDistrict() + "\n");
//                    sb.append("区域 码   : " + amapLocation.getAdCode() + "\n");
//                    sb.append("地    址    : " + amapLocation.getAddress() + "\n");
//                    sb.append("兴趣点    : " + amapLocation.getPoiName() + "\n");
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
                mWeiboDialog.dismiss();
                Toast.makeText(this,"定位失败,"+amapLocation.getErrorInfo(),Toast.LENGTH_SHORT).show();
                //定位失败
                sb.append("定位失败" + "\n");
                sb.append("错误码:" + amapLocation.getErrorCode() + "\n");
                sb.append("错误信息:" + amapLocation.getErrorInfo() + "\n");
                sb.append("错误描述:" + amapLocation.getLocationDetail() + "\n");
            }

            /*mLocationStatus.append(sb.toString());
            mLocationStatus.append("\n---\n");*/
            mLocationClient.stopLocation();
        }
    }

    public void WheelOnClick(){
        wvNeihu.addClickingListener(new OnWheelClickedListener() {
            @Override
            public void onItemClicked(WheelView wheel, int itemIndex) {
                wheel.setCurrentItem(itemIndex);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mLocationClient) {
            /**
             * 如果AMapLocationClient是在当前Activity实例化的，
             * 在Activity的onDestroy中一定要执行AMapLocationClient的onDestroy
             */
            mLocationClient.onDestroy();
            mLocationClient = null;
            mLocationOption = null;
        }
    }
}
