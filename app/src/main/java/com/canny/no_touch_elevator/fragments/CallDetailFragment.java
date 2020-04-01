package com.canny.no_touch_elevator.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.canny.no_touch_elevator.MyApp;
import com.canny.no_touch_elevator.R;
import com.canny.no_touch_elevator.base.BaseFragment;
import com.canny.no_touch_elevator.huti.CallElevatorActivity;
import com.canny.no_touch_elevator.huti.ScanInTheCallActivity;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.Constants;
import com.canny.no_touch_elevator.webapi.customcallback.JsonCallback;
import com.canny.no_touch_elevator.webapi.response.ElevatorBean;
import com.canny.no_touch_elevator.webapi.response.UserBuildResponse;
import com.canny.no_touch_elevator.webapi.response.UserLastResponse;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.internal.LinkedTreeMap;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.Response;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class  CallDetailFragment extends BaseFragment{

    @BindView(R.id.ll_huti)
    LinearLayout llHuti;
    @BindView(R.id.rb_front)
    RadioButton rbFront;
    @BindView(R.id.rb_jiaonei)
    RadioButton rbJiaonei;
    @BindView(R.id.rb_back)
    RadioButton rbBack;
    @BindView(R.id.btn_callok)
    Button btnCallok;
    @BindView(R.id.rg_style)
    RadioGroup rgStyle;
    @BindView(R.id.tv_select)
    TextView tvSelect;

    private String styleNum="";
    //电梯数据源
    ArrayList<UserBuildResponse.MsgBean> list;
    ArrayList<ElevatorBean> listName = new ArrayList<>();
    //添加具体位置
    ArrayList<ElevatorBean.MsgBean> area = new ArrayList<>();
    private ArrayList<ElevatorBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private int index1=0,index2=0;
    private UserLastResponse lastResponse;

    public CallDetailFragment() {
    }

    public static CallDetailFragment newInstance() {
        CallDetailFragment fragment = new CallDetailFragment();
        return fragment;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_call_detail;
    }

    @Override
    protected void initView(View view) {
        ButterKnife.bind(this, view);
        getInitData();
        final Map<String,Integer> map=new HashMap<>();
        rgStyle.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_front:
                        styleNum=rbFront.getText().toString();
                        map.put("前门",0);
                        break;
                    case R.id.rb_jiaonei:
                        styleNum=rbJiaonei.getText().toString();
                        map.put("轿内",0xff);
                        break;
                    case R.id.rb_back:
                        styleNum=rbBack.getText().toString();
                        map.put("后门",1);
                        break;
                    default:break;
                }
            }
        });

        initUserInform();
        tvSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OptionsPickerView optionsPickerView=new OptionsPickerBuilder(getActivity(), new OnOptionsSelectListener() {
                    @Override
                    public void onOptionsSelect(int options1, int options2, int options3, View v) {
                        //返回的分别是2个级别的选中位置
                        String tx = options1Items.get(options1).getPickerViewText()
                                + options2Items.get(options1).get(options2);
                        tvSelect.setText(tx);
                    }
                }) .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                        index1=options1;
                        index2=options2;
                    }
                }).build();
                if (options1Items.size()!=0&&options2Items.size()!=0){
                    optionsPickerView.setPicker(options1Items,options2Items);
                    optionsPickerView.show();
                }
            }
        });
    }

    private void initUserInform() {
        //用户小区及电梯信息
        OkGo.post(Constants.UserBuildingUrl)
                .params("openId",SharedPrefOP.getInstance().getOpenId())
                .execute(new JsonCallback<Object>() {
                    @Override
                    public void onSuccess(Response<Object> response) {
                        LinkedTreeMap tm = (LinkedTreeMap) response.body();
                        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                        String jsonString=gson.toJson(tm);
                        if (jsonString.contains("1")){
                            UserBuildResponse buildResponse = gson.fromJson(jsonString, UserBuildResponse.class);
                            if (buildResponse.getResult()==1){
                                list= (ArrayList<UserBuildResponse.MsgBean>) buildResponse.getMsg();
                                ElevatorBean.MsgBean cityBean = new ElevatorBean.MsgBean();
                                for (int i = 0; i <list.size() ; i++) {
                                    ElevatorBean data=new ElevatorBean();
                                    data.setName(list.get(i).getBuildName());
                                    ArrayList<String> areaList = new ArrayList<>();
                                    for (int j = 0; j <list.get(i).getEtorList().size() ; j++) {
                                        cityBean.setName(list.get(i).getEtorList().get(j).getBuild_number()+" "+
                                                list.get(i).getEtorList().get(j).getBuild_etorindex());
                                        areaList.add(list.get(i).getEtorList().get(j).getBuild_number()+" "+
                                                list.get(i).getEtorList().get(j).getBuild_etorindex());
                                    }
                                    options2Items.add(areaList);
                                    listName.add(data);
                                    area.add(cityBean);
                                    options1Items=listName;
                                }
                            }else {
                                Toast.makeText(MyApp.getApplication(),response.message(),Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void getInitData() {
        //用户上次呼梯的小区及电梯信息
        OkGo.post(Constants.UserLastBuildingUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("openId",SharedPrefOP.getInstance().getOpenId())
                .execute(new JsonCallback<Object>() {
                    @Override
                    public void onSuccess(Response<Object> response) {
                        LinkedTreeMap tm = (LinkedTreeMap) response.body();
                        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                        String jsonString=gson.toJson(tm);
                        if (jsonString.contains("1")){
                            lastResponse = gson.fromJson(jsonString, UserLastResponse.class);
                            if(1== lastResponse.getResult()) {
                                if (lastResponse.getMsg()!=null){
                                    tvSelect.setText(lastResponse.getMsg().get(0).getBuildName()+" "+
                                            lastResponse.getMsg().get(0).getBuild_number()+" "+
                                            lastResponse.getMsg().get(0).getEtor_index_show());

                                    btnCallok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            if (styleNum==null||styleNum.equals("前门")){
                                                styleNum="0";
                                            }else if (styleNum.equals("轿内")){
                                                styleNum="0xFF";
                                            }else if (styleNum.equals("后门")){
                                                styleNum="1";
                                            }
                                            if (index1==0&&index2==0&&tvSelect.getText().toString().contains(lastResponse.getMsg().get(0).getBuildName())){
                                                showAlterDialog();
                                            }else {
                                                if (styleNum.equals("0")||styleNum.equals("")){
                                                    Intent intent=new Intent(getActivity(), CallElevatorActivity.class);
                                                    intent.putExtra("styleNum","0");
                                                    SharedPrefOP.getInstance().saveBianHao(list.get(index1).getEtorList().get(index2).getBianhao());
                                                    startActivity(intent);
                                                    getActivity().finish();
                                                }else if (styleNum.equals("0xFF")){
                                                    Intent intent=new Intent(getActivity(), ScanInTheCallActivity.class);
                                                    intent.putExtra("styleNum","0xFF");
                                                    SharedPrefOP.getInstance().saveBianHao(list.get(index1).getEtorList().get(index2).getBianhao());
                                                    startActivity(intent);
                                                    getActivity().finish();
                                                }
                                            }
                                        }
                                    });
                                }
                            }else {
                                Toast.makeText(MyApp.getApplication(), response.message(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }
                });
    }

    private void showAlterDialog(){
        AlertDialog.Builder alterDiaglog = new AlertDialog.Builder(getActivity());
        alterDiaglog.setTitle("提示");//文字
        alterDiaglog.setMessage("当前为最近一次呼叫的电梯,确认呼叫?");//提示消息
        //积极的选择
        alterDiaglog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (styleNum.equals("0")||styleNum.equals("")){
                    Intent intent=new Intent(getActivity(), CallElevatorActivity.class);
                    intent.putExtra("styleNum","0");
                    //if (index1==0&&index2==0&& tvSelect.getText().toString().contains(lastResponse.getMsg().get(0).getBuildName())){
                        //intent.putExtra("build_name", lastResponse.getMsg().get(0).getBuildName());
                        //intent.putExtra("build_number", lastResponse.getMsg().get(0).getBuild_number());
                        //intent.putExtra("bianhao", lastResponse.getMsg().get(0).getBianhao());
                    SharedPrefOP.getInstance().saveBianHao(lastResponse.getMsg().get(0).getBianhao());
                   // }
                    //else {
                        //intent.putExtra("build_name",listName.get(index1).getName());
                        //intent.putExtra("build_number",options2Items.get(index1).get(index2));
                        //intent.putExtra("bianhao",list.get(index1).getEtorList().get(index2).getBianhao());
                       // SharedPrefOP.getInstance().saveBianHao(list.get(index1).getEtorList().get(index2).getBianhao());
                   // }
                    startActivity(intent);
                    getActivity().finish();
                }else if (styleNum.equals("0xFF")){
                    Intent intent=new Intent(getActivity(), ScanInTheCallActivity.class);
                    intent.putExtra("styleNum","0xFF");
                   // if (index1==0&&index2==0&& tvSelect.getText().toString().contains(lastResponse.getMsg().get(0).getBuildName())){
                        // intent.putExtra("build_name", lastResponse.getMsg().get(0).getBuildName());
                        //intent.putExtra("build_number", lastResponse.getMsg().get(0).getBuild_number());
                        // intent.putExtra("bianhao", lastResponse.getMsg().get(0).getBianhao());
                    SharedPrefOP.getInstance().saveBianHao(lastResponse.getMsg().get(0).getBianhao());
                    //}
                    //else {
                        // intent.putExtra("build_name",listName.get(index1).getName());
                        // intent.putExtra("build_number",options2Items.get(index1).get(index2));
                        //intent.putExtra("bianhao",list.get(index1).getEtorList().get(index2).getBianhao());
                    //    SharedPrefOP.getInstance().saveBianHao(list.get(index1).getEtorList().get(index2).getBianhao());
                   // }
                    startActivity(intent);
                    getActivity().finish();
                }
            }
        });
        //消极的选择
        alterDiaglog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        //显示
        alterDiaglog.show();
    }

    @Override
    protected void getData() {

    }
}
