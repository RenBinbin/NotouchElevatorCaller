package com.canny.no_touch_elevator.webapi;

import android.util.Log;
import android.widget.Toast;

import com.canny.no_touch_elevator.MyApp;
import com.canny.no_touch_elevator.util.Assistant;
import com.canny.no_touch_elevator.util.SharedPrefOP;
import com.canny.no_touch_elevator.webapi.customcallback.CannyResponse;
import com.canny.no_touch_elevator.webapi.customcallback.JsonCallback;
import com.canny.no_touch_elevator.webapi.customcallback.JsonConvert;
import com.canny.no_touch_elevator.webapi.customcallback.SimpleResponse;
import com.canny.no_touch_elevator.webapi.response.ElevatorBean;
import com.canny.no_touch_elevator.webapi.response.EtorFloorInfoBean;
import com.canny.no_touch_elevator.webapi.response.GroupRequestBean;
import com.canny.no_touch_elevator.webapi.response.GroupResultBean;
import com.canny.no_touch_elevator.webapi.response.StatusInforBean;
import com.canny.no_touch_elevator.webapi.response.UserBuildResponse;
import com.canny.no_touch_elevator.webapi.response.UserInfoBean;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.internal.LinkedTreeMap;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by renbinbin1 on 2018/7/12.
 */

public  class CannyApi {
    public static void ReqPasscode(final String phoneNum, final CannyCallback cannyCallback) {
        final String deviceInfo = Assistant.getDeviceInfo();
        final String softwareInfo = Assistant.getSoftwareInfo();
        //访问网络
        OkGo.<CannyResponse>post(Constants.PhoneRegisterUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("phoneNum", phoneNum)
                .params("clientId", "Android")
                .params("deviceInfo", deviceInfo)
                .params("softwareInfo", softwareInfo)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        if (1 == cannyResponse.result) {
                            cannyCallback.onSuccess("ReqPasscode", cannyResponse.msg.toString(), null);
                        } else {
                            cannyCallback.onFailure("ReqPasscode", cannyResponse.msg.toString(), null);
                            Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            cannyCallback.onFailure("ReqPasscode",response.message(), null);
                        }
                    }
                });
    }

    public static void Login(final String phoneNum, String passCode,final CannyCallback cannyCallback) {
        final String deviceId =Assistant.getDeviceId();
        OkGo.<CannyResponse>post(Constants.PhoneLoginUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("phoneNum", phoneNum)
                .params("passCode", passCode)
                .params("clientId", "Android")
                .params("openId", phoneNum+""+deviceId)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        if(1==cannyResponse.result) {
                            cannyCallback.onSuccess("Login", cannyResponse.msg.toString(), null);
                        }else{
                            cannyCallback.onFailure("Login", cannyResponse.msg.toString(), null);
                            Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            cannyCallback.onFailure("Login",response.message(), null);
                        }
                    }
                });
    }

    //通过openId判断用户是否注册过了
    public static void JudgeIfRegister(final String openId,final CannyCallback cannyCallback) {
        //呼梯请求结果状态
        OkGo.<CannyResponse>post(Constants.JudgeRegisterUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("openId",openId)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        if(1==cannyResponse.result) {
                            LinkedTreeMap tm = (LinkedTreeMap) cannyResponse.msg;
                            //UserInfoBean
                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            String jsonString = gson.toJson(tm);
                            UserInfoBean userInfoBean = gson.fromJson(jsonString, UserInfoBean.class);
                            String realname = "" + userInfoBean.getUserinfo().get_realname();
                            if (null != realname && !realname.isEmpty()) {
                                SharedPrefOP.getInstance().saveUsername(realname);
                            }
                            cannyCallback.onSuccess("JudgeIfRegister", cannyResponse.msg.toString(), userInfoBean);
                        }else{
                            cannyCallback.onFailure("JudgeIfRegister", cannyResponse.msg.toString(), null);
                            Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            cannyCallback.onFailure("JudgeIfRegister",response.message(), null);
                        }
                    }
                });
    }

    public static void GetEtorFloorInfo(final String openId,final String qrbarcode, final CannyCallback cannyCallback) {
        //访问网络
        OkGo.<CannyResponse>post(Constants.EtorFloorInfoUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("openId",openId)
                .params("qrbarcode", qrbarcode)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        Log.e("floor",cannyResponse.msg.toString() );
                        if(1==cannyResponse.result) {
                            LinkedTreeMap tm = (LinkedTreeMap) cannyResponse.msg;
                            //etorFloorInfoBean
                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            String jsonString = gson.toJson(tm);
                            EtorFloorInfoBean etorFloorInfoBean = gson.fromJson(jsonString, EtorFloorInfoBean.class);
                            String build_name=etorFloorInfoBean.getBuild_name();
                            String build_number=etorFloorInfoBean.getBuild_number();
                            if (build_name!=null){
                                SharedPrefOP.getInstance().saveBuildName(build_name);
                            }
                            if (build_number!=null){
                                SharedPrefOP.getInstance().saveBuildNumber(build_number);
                            }
                            cannyCallback.onSuccess("GetEtorFloorInfo", etorFloorInfoBean, etorFloorInfoBean);
                        }else{
                            cannyCallback.onFailure("GetEtorFloorInfo", cannyResponse.msg.toString(), null);
                            Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            cannyCallback.onFailure("GetEtorFloorInfo", response.message(), null);
                        }

                    }
                });
    }

    public static void CallRequest(String openId,String qrbarcode,String gpsLatitude,String gpsLongitude,int doorFrontOrBack,
                                   String srcFloorNum,String desFloorNum,final CannyCallback callCallback) {
        OkGo.<CannyResponse>post(Constants.PhoneRequestUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("openId",openId)
                .params("qrbarcode", qrbarcode)
                .params("gpsLatitude", gpsLatitude)
                .params("gpsLongitude",gpsLongitude )
                .params("doorFrontOrBack",doorFrontOrBack )
                .params("srcFloorNum",srcFloorNum)
                .params("desFloorNum",desFloorNum)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        if(1==cannyResponse.result) {
                          /*  LinkedTreeMap tm = (LinkedTreeMap) cannyResponse.msg;
                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            String jsonString = gson.toJson(tm);
                            LoginResponse callRsponse = gson.fromJson(jsonString, LoginResponse.class);*/
                            callCallback.onSuccess("CallRequest", cannyResponse,cannyResponse);
                        }else{
                            callCallback.onFailure("CallRequest", cannyResponse.msg.toString(), null);
                            Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            callCallback.onFailure("CallRequest", response.message(), null);
                        }
                    }
                });
    }

    public static void PhoneStatus(String openId,String qrbarcode,String requestId,final CannyCallback statusCallback) {
        //呼梯请求结果状态
        OkGo.<CannyResponse>post(Constants.PhoneStatusUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("openId",openId)
                .params("qrbarcode", qrbarcode)
                .params("requestId", requestId)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        if(1==cannyResponse.result) {
                            LinkedTreeMap tm = (LinkedTreeMap) cannyResponse.msg;
                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            String jsonString = gson.toJson(tm);
                            StatusInforBean statusInforBean = gson.fromJson(jsonString, StatusInforBean.class);
                            statusCallback.onSuccess("phoneStatus", statusInforBean,statusInforBean);
                        }else{
                            statusCallback.onFailure("phoneStatus", cannyResponse.msg.toString(), cannyResponse.msg.toString());
                            //Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            statusCallback.onFailure("phoneStatus", response.message(), null);
                        }
                    }
                });
    }

    public static void VisitorPermissRequest(String openId,String qrbarcode,String visitor_name,
                                             String visitor_phone,String respondent_name,String respondent_phone,
                                             String reason,String visiter_time,final CannyCallback cannyCallback) {
        //用户请求访客权限记录提交
        OkGo.<CannyResponse>post(Constants.VisitorPermissRequestUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("openId",openId)
                .params("qrbarcode", qrbarcode)
                .params("visitor_name", visitor_name)
                .params("visitor_phone", visitor_phone)
                .params("respondent_name", respondent_name)
                .params("respondent_phone", respondent_phone)
                .params("reason", reason)
                .params("visiter_time", visiter_time)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        if(1==cannyResponse.result) {
                            LinkedTreeMap tm = (LinkedTreeMap) cannyResponse.msg;
                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            String jsonString = gson.toJson(tm);

                            //cannyCallback.onSuccess("VisitorPermissRequest", statusInforBean,statusInforBean);
                        }else{
                            cannyCallback.onFailure("VisitorPermissRequest", cannyResponse.msg.toString(), cannyResponse.msg.toString());
                            //Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            cannyCallback.onFailure("VisitorPermissRequest", response.message(), null);
                        }
                    }
                });
    }

    public static void VisitorPermissResport(String openId,String requestId,boolean result,
                                             int allow_times,String respondent_name,String expire_date, final CannyCallback cannyCallback) {
        //用户应答请求访客权限,记录提交
        OkGo.<CannyResponse>post(Constants.VisitorPermissResportUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("openId",openId)
                .params("requestId", requestId)
                .params("result", result)
                .params("allow_times", allow_times)
                .params("expire_date", expire_date)
                .params("respondent_name", respondent_name)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        if(1==cannyResponse.result) {
                            LinkedTreeMap tm = (LinkedTreeMap) cannyResponse.msg;
                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            String jsonString = gson.toJson(tm);

                            //cannyCallback.onSuccess("VisitorPermissRequest", statusInforBean,statusInforBean);
                        }else{
                            cannyCallback.onFailure("VisitorPermissRequest", cannyResponse.msg.toString(), cannyResponse.msg.toString());
                            //Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            cannyCallback.onFailure("VisitorPermissRequest", response.message(), null);
                        }
                    }
                });
    }

    public static void GroupRunStateRequest(String openId,String qrbarcode,final CannyCallback cannyCallback) {
        //群控电梯查询运行状态请求接口
        OkGo.<CannyResponse>post(Constants.RunStateRequestUrl)
                //.tag(context)//唯一标示：取消请求需要用到
                .params("openId",openId)
                .params("qrbarcode", qrbarcode)
                .execute(new JsonCallback<CannyResponse>() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<CannyResponse> response) {
                        CannyResponse cannyResponse = response.body();
                        if(1==cannyResponse.result) {
                            LinkedTreeMap tm = (LinkedTreeMap) cannyResponse.msg;
                            Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                            String jsonString = gson.toJson(tm);
                            GroupRequestBean requestBean = gson.fromJson(jsonString, GroupRequestBean.class);
                            int requestID=requestBean.getRequestId();
                            if (requestID+""!=null){
                                SharedPrefOP.getInstance().saveRequestId(requestID+"");
                            }
                            cannyCallback.onSuccess("GroupRunStateRequest", requestBean,requestBean);
                        }else{
                            cannyCallback.onFailure("GroupRunStateRequest", cannyResponse.msg.toString(), cannyResponse.msg.toString());
                            //Toast.makeText(MyApp.getApplication(), cannyResponse.msg.toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onError(com.lzy.okgo.model.Response<CannyResponse> response) {
                        if (response.message()!=null){
                            cannyCallback.onFailure("GroupRunStateRequest", response.message(), null);
                        }
                    }
                });
    }

    public static void GroupRunStateResult(String openId,String qrbarcode,String requestId,final CannyCallback cannyCallback) {
        //群控电梯查询运行状态请求接口
        OkGo.post(Constants.RunStateResultUrl)
                .params("openId",openId)
                .params("qrbarcode", qrbarcode)
                .params("requestId", requestId)
                .execute(new JsonCallback<Object>() {
                    @Override
                    public void onSuccess(Response<Object> response) {
                        LinkedTreeMap tm = (LinkedTreeMap) response.body();
                        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().create();
                        String jsonString=gson.toJson(tm);
                        if (jsonString.contains("1")){
                            GroupResultBean resultBean=gson.fromJson(jsonString,GroupResultBean.class);
                            if (resultBean.getResult()==1){
                                cannyCallback.onSuccess("GroupRunStateResult",resultBean,resultBean);
                            }
                        }
                    }

                    @Override
                    public void onError(Response<Object> response) {
                        super.onError(response);

                    }
                });

    }
}
