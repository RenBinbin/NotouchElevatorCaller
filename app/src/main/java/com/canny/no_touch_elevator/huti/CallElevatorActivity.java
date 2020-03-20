package com.canny.no_touch_elevator.huti;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseActivity;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.CannyApi;
import com.canny.no_touch_elevator.webapi.CannyCallback;
import com.canny.no_touch_elevator.webapi.customcallback.CannyResponse;
import com.canny.no_touch_elevator.webapi.response.EtorFloorInfoBean;
import com.canny.no_touch_elevator.webapi.response.StatusInforBean;
import com.canny.no_touch_elevator.wheelview.NumericWheelAdapter;
import com.canny.no_touch_elevator.wheelview.WheelView;

import java.util.Timer;
import java.util.TimerTask;

import androidx.annotation.RequiresApi;
import butterknife.BindView;
import butterknife.ButterKnife;

public class CallElevatorActivity extends BaseActivity implements CannyCallback,AMapLocationListener{

    @BindView(R.id.tv_showMsg)
    TextView tvShowMsg;
    @BindView(R.id.iv_set)
    ImageView ivSet;
    @BindView(R.id.wv_left)
    WheelView wvLeft;
    @BindView(R.id.wv_right)
    WheelView wvRight;
    @BindView(R.id.btn_neihu)
    Button btnNeihu;
    @BindView(R.id.iv_up)
    ImageView ivUp;
    @BindView(R.id.iv_down)
    ImageView ivDown;
    @BindView(R.id.activity_call_landing)
    RelativeLayout rlCallLanding;

    private String[] floorAry;
    private int index=0;

    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    //声明定位回调监听器
    //public AMapLocationListener mLocationListener = null;
    //声明mLocationOption对象
    public AMapLocationClientOption mLocationOption = null;
    private Timer t;
    //private Intent intent;
    private EtorFloorInfoBean etorFloorInfoBean;
    private Handler handler;
    private NetworkInfo mNetworkInfo;
    private String etor_bianhao;
    private String openId;
    private CannyResponse response;
    private double longitude;
    private double latitude;
    private int nowNum;
    private int destNum;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_call_elevator;
    }

    @Override
    public void initData() {
        ButterKnife.bind(this);
        ConnectivityManager mConnectivityManager = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        //创建1个定时器
        t = new Timer();
        handler = new Handler();
        //intent = getIntent();
        etor_bianhao = SharedPrefOP.getInstance().getBianHao();
        openId = SharedPrefOP.getInstance().getOpenId();
        getNetWork();

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(this);
        initLocationOption();

        btnNeihu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent(CallElevatorActivity.this,InTheCallActivity.class);
                intent1.putExtra("floorAry",floorAry);
                //intent1.putExtra("bianhao",intent.getStringExtra("bianhao"));
               // Log.e("len",floorAry.length+"" );
                startActivity(intent1);
                finish();
            }
        });
    }

    private void getNetWork() {
        if (mNetworkInfo ==null){

        }else {
            CannyApi.GetEtorFloorInfo(openId,etor_bianhao, this);
        }
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

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onLocationChanged(AMapLocation amapLocation) {
        nowNum = wvLeft.getCurrentItem()+1;
        destNum = wvRight.getCurrentItem()+1;
        if (amapLocation != null) {
            StringBuffer sb = new StringBuffer();
            if (amapLocation.getErrorCode() == 0) {
                //定位成功回调信息，设置相关消息
                amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                //获取纬度
                latitude = amapLocation.getLatitude();
                //获取经度
                longitude = amapLocation.getLongitude();
                amapLocation.getAccuracy();//获取精度信息
                if (nowNum == destNum){
                    Toast.makeText(CallElevatorActivity.this,"非法楼层登记",Toast.LENGTH_SHORT).show();
                }else {
                    CannyApi.CallRequest(SharedPrefOP.getInstance().getOpenId(), SharedPrefOP.getInstance().getBianHao(),
                            amapLocation.getLatitude()+ "", amapLocation.getLongitude() + "",0,
                            nowNum + "", destNum + "", CallElevatorActivity.this);//呼梯请求
                }
               // SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
               // Date date = new Date(amapLocation.getTime());
               // df.format(date);//定位时间
                amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                amapLocation.getCountry();//国家信息
                amapLocation.getProvince();//省信息
                amapLocation.getCity();//城市信息
                amapLocation.getDistrict();//城区信息
                amapLocation.getStreet();//街道信息
                amapLocation.getStreetNum();//街道门牌号信息
                amapLocation.getCityCode();//城市编码
                amapLocation.getAdCode();//地区编码
                sb.append("定位成功" + "\n");
                sb.append("定位类型: " + amapLocation.getLocationType() + "\n");
                sb.append("经    度    : " + amapLocation.getLongitude() + "\n");
                sb.append("纬    度    : " + amapLocation.getLatitude() + "\n");
                sb.append("精    度    : " + amapLocation.getAccuracy() + "米" + "\n");
                sb.append("提供者    : " + amapLocation.getProvider() + "\n");

                if (amapLocation.getProvider().equalsIgnoreCase(
                        android.location.LocationManager.GPS_PROVIDER)) {
                    // 以下信息只有提供者是GPS时才会有
                    sb.append("速    度    : " + amapLocation.getSpeed() + "米/秒" + "\n");
                    sb.append("角    度    : " + amapLocation.getBearing() + "\n");
                     //获取当前提供定位服务的卫星个数
                    sb.append("星    数    : "
                            + amapLocation.getSatellites() + "\n");
                } else {
                    // 提供者是GPS时是没有以下信息的
                    sb.append("国    家    : " + amapLocation.getCountry() + "\n");
                    sb.append("省            : " + amapLocation.getProvince() + "\n");
                    sb.append("市            : " + amapLocation.getCity() + "\n");
                    sb.append("城市编码 : " + amapLocation.getCityCode() + "\n");
                    sb.append("区            : " + amapLocation.getDistrict() + "\n");
                    sb.append("区域 码   : " + amapLocation.getAdCode() + "\n");
                    sb.append("地    址    : " + amapLocation.getAddress() + "\n");
                    sb.append("兴趣点    : " + amapLocation.getPoiName() + "\n");
                }
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + amapLocation.getErrorCode() + ", errInfo:"
                        + amapLocation.getErrorInfo());
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

    private void ShowContent() {
        initNowFloor();
        initDestFloor();
        wvLeft.setVisibleItems(7);
        wvRight.setVisibleItems(7);

        ivSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mNetworkInfo==null){
                    changePageState(PageState.ERROR);
                    rlCallLanding.setVisibility(View.GONE);
                }else {
                    //启动定位
                    mLocationClient.startLocation();rlCallLanding.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void initNowFloor() {
        NumericWheelAdapter numericWheelAdapter =
                new NumericWheelAdapter(this, Integer.parseInt(floorAry[0]), Integer.parseInt(floorAry[floorAry.length - 1]));
        numericWheelAdapter.setLabel("");
        //numericWheelAdapter.setTextSize(15);  设置字体大小
        wvLeft.setViewAdapter(numericWheelAdapter);
        wvLeft.setCyclic(false);
    }

    private void initDestFloor() {
        NumericWheelAdapter numericWheelAdapter =
                new NumericWheelAdapter(this, Integer.parseInt(floorAry[0]), Integer.parseInt(floorAry[floorAry.length - 1]));
        numericWheelAdapter.setLabel("");
        wvRight.setViewAdapter(numericWheelAdapter);
        wvRight.setCyclic(false);
    }

    @Override
    protected void getData() {

    }

    @Override
    public void onFailure(String method, String reason, Object other) {

    }

    @Override
    public void onSuccess(String method, Object result, Object other) {
        if (method == "GetEtorFloorInfo") {
            etorFloorInfoBean = (EtorFloorInfoBean) other;
            String floorName = etorFloorInfoBean.getFloorName();
            floorAry = floorName.replace(" ", "").split(",");
            ShowContent();

            tvShowMsg.setText(SharedPrefOP.getInstance().getBuildName()+" "+ SharedPrefOP.getInstance().getBuildNumber());

            if (etorFloorInfoBean.getLast_call_from()> etorFloorInfoBean.getLast_call_to()){
                ivUp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wvRight.setCurrentItem(etorFloorInfoBean.getLast_call_to()-1);
                        wvLeft.setCurrentItem(etorFloorInfoBean.getLast_call_from()-1);
                    }
                });
            }else if (etorFloorInfoBean.getLast_call_from()< etorFloorInfoBean.getLast_call_to()){
                ivDown.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        wvRight.setCurrentItem(etorFloorInfoBean.getLast_call_to()-1);
                        wvLeft.setCurrentItem(etorFloorInfoBean.getLast_call_from()-1);
                    }
                });
            }else {

            }
        } else if (method == "CallRequest") {
            response = (CannyResponse) result;
            ivSet.setImageResource(R.drawable.calling);
            t.schedule(new TimerTask() {
                @Override
                public void run() {
                    CannyApi.PhoneStatus(SharedPrefOP.getInstance().getOpenId(),SharedPrefOP.getInstance().getBianHao(),
                            response.msg.toString(), CallElevatorActivity.this);
                    index++;
                    if(index>15) {
                        t.cancel();
                        index=0;
                        handler.post(runAbleUi);
                    }
                }
            }, 0, 2000);// 首次运行,延迟0毫秒,循环间隔1000毫秒

        } else if (method == "phoneStatus") {
            t.cancel();
            index=0;
            StatusInforBean statusResopnse= (StatusInforBean) other;
            Intent intent1=new Intent(this,CallSucessActivity.class);
            intent1.putExtra("des_etor_index_show",statusResopnse.getDes_etor_index_show());
            intent1.putExtra("left",wvLeft.getCurrentItem()+1+"");
            intent1.putExtra("right",wvRight.getCurrentItem()+1+"");
            //intent1.putExtra("bianhao",intent.getStringExtra("bianhao"));
            startActivity(intent1);
            finish();
        }
    }
    // 构建Runnable对象，在runnable中更新界面
    Runnable runAbleUi=new Runnable(){
        @Override
        public void run() {
            //更新界面
            Toast.makeText(CallElevatorActivity.this,"系统繁忙,请重试",Toast.LENGTH_SHORT).show();
            ivSet.setImageResource(R.drawable.ok1);
        }
    };
}
