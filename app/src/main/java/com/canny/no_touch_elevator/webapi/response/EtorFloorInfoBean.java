package com.canny.no_touch_elevator.webapi.response;

public class EtorFloorInfoBean {

    /**
     * floorCnt : 5
     * floorForbidden : 1,1,1,1,1
     * floorName :  1, 2, 3, 4, 5
     * build_name : 汾湖康力大道888号
     * build_number : 一期综合楼
     * build_etorindex : T3
     * last_call_from : 1
     * last_call_to : 3
     * isGroupEtor : false
     * need_auth : false
     * public_floors :
     * user_floors :
     */

    private String floorCnt;
    private String floorForbidden;
    private String floorName;
    private String build_name;
    private String build_number;
    private String build_etorindex;
    private int last_call_from;
    private int last_call_to;
    private boolean isGroupEtor;
    private boolean need_auth;
    private String public_floors;
    private String user_floors;

    public String getFloorCnt() {
        return floorCnt;
    }

    public void setFloorCnt(String floorCnt) {
        this.floorCnt = floorCnt;
    }

    public String getFloorForbidden() {
        return floorForbidden;
    }

    public void setFloorForbidden(String floorForbidden) {
        this.floorForbidden = floorForbidden;
    }

    public String getFloorName() {
        return floorName;
    }

    public void setFloorName(String floorName) {
        this.floorName = floorName;
    }

    public String getBuild_name() {
        return build_name;
    }

    public void setBuild_name(String build_name) {
        this.build_name = build_name;
    }

    public String getBuild_number() {
        return build_number;
    }

    public void setBuild_number(String build_number) {
        this.build_number = build_number;
    }

    public String getBuild_etorindex() {
        return build_etorindex;
    }

    public void setBuild_etorindex(String build_etorindex) {
        this.build_etorindex = build_etorindex;
    }

    public int getLast_call_from() {
        return last_call_from;
    }

    public void setLast_call_from(int last_call_from) {
        this.last_call_from = last_call_from;
    }

    public int getLast_call_to() {
        return last_call_to;
    }

    public void setLast_call_to(int last_call_to) {
        this.last_call_to = last_call_to;
    }

    public boolean isIsGroupEtor() {
        return isGroupEtor;
    }

    public void setIsGroupEtor(boolean isGroupEtor) {
        this.isGroupEtor = isGroupEtor;
    }

    public boolean isNeed_auth() {
        return need_auth;
    }

    public void setNeed_auth(boolean need_auth) {
        this.need_auth = need_auth;
    }

    public String getPublic_floors() {
        return public_floors;
    }

    public void setPublic_floors(String public_floors) {
        this.public_floors = public_floors;
    }

    public String getUser_floors() {
        return user_floors;
    }

    public void setUser_floors(String user_floors) {
        this.user_floors = user_floors;
    }
}
