package com.canny.no_touch_elevator.webapi;

/**
 * Created by 1006177 on 2020/2/17.
 */

public class Constants {

    public static final String TestServerUrl = "http://t.4001882367.com:12345/";
    public static final String OfficialServerUrl = "http://i.4001882367.com:8082/";

    public static final String PhoneRegister="api/lift/phonereg";//手机注册
    public static final String PhoneLogin="api/lift/phonelogin";//手机登录
    public static final String FloorInform="api/lift/infotable";//楼层显示信息
    public static final String PhoneRequest="api/lift/request";//呼梯请求
    public static final String PhoneStatus="api/lift/requeststatus";//呼梯请求结果状态
    public static final String UserBuilding="api/lift/getuserbuilding";//用户小区及电梯信息
    public static final String UserLastBuilding="api/lift/lastcalletor";//用户上次呼梯的小区及电梯信息
    public static final String JudgeRegister="api/lift/isuserreg";//判断用户是否注册接口
    public static final String AuthUserReqest="api/lift/authuserreqest";//电梯管理员/住户授权其他人使用电梯接口
    public static final String RunStateRequest="api/lift/reqgroupetorrunstate";//群控电梯查询运行状态请求
    public static final String RunStateResult="api/lift//reqgroupetorrunstateresult";//群控电梯查询运行状态请求结果

    //====
    public static boolean DebugFlag=false;
    public static String BaseUrl=(DebugFlag?TestServerUrl:OfficialServerUrl);

    //===
    public static String PhoneRegisterUrl=(BaseUrl+PhoneRegister);//手机注册
    public static String PhoneLoginUrl=(BaseUrl+PhoneLogin);//手机登录
    public static String EtorFloorInfoUrl =(BaseUrl+FloorInform);//楼层显示信息
    public static String PhoneRequestUrl=(BaseUrl+PhoneRequest);//呼梯请求
    public static String PhoneStatusUrl=(BaseUrl+PhoneStatus);//呼梯请求结果状态
    public static String UserBuildingUrl=(BaseUrl+UserBuilding);//用户小区及电梯信息
    public static String UserLastBuildingUrl=(BaseUrl+UserLastBuilding);//用户上次呼梯的小区及电梯信息
    public static String JudgeRegisterUrl=(BaseUrl+JudgeRegister);//判断用户是否注册接口
    public static String AuthUserReqestUrl=(BaseUrl+AuthUserReqest);//电梯管理员/住户授权其他人使用电梯接口
    public static String RunStateRequestUrl=(BaseUrl+RunStateRequest);//群控电梯查询运行状态请求
    public static String RunStateResultUrl=(BaseUrl+RunStateResult);//群控电梯查询运行状态请求结果

}