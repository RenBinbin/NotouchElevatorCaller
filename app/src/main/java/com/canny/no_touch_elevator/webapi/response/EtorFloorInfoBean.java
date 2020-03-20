package com.canny.no_touch_elevator.webapi.response;

public class EtorFloorInfoBean {
    /**
     * floorCnt : 5
     * floorForbidden : 1,1,1,1,1
     * floorName :  1, 2, 3, 4, 5
     * build_name : 康力大道799号新里程
     * build_number : 新里程
     * build_etorindex : 观光西
     * last_call_from : 1
     * last_call_to : 1
     */

    private String floorCnt;
    private String floorForbidden;
    private String floorName;
    private String build_name;
    private String build_number;
    private String build_etorindex;
    private int last_call_from;
    private int last_call_to;

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
}
