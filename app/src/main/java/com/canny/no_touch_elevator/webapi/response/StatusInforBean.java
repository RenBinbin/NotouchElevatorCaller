package com.canny.no_touch_elevator.webapi.response;

public class StatusInforBean {

    /**
     * des_etor_index : 0
     * des_etor_index_show : 左边主梯
     * des_etor_floor_show :  3
     */

    private int des_etor_index;
    private String des_etor_index_show;
    private String des_etor_floor_show;

    public int getDes_etor_index() {
        return des_etor_index;
    }

    public void setDes_etor_index(int des_etor_index) {
        this.des_etor_index = des_etor_index;
    }

    public String getDes_etor_index_show() {
        return des_etor_index_show;
    }

    public void setDes_etor_index_show(String des_etor_index_show) {
        this.des_etor_index_show = des_etor_index_show;
    }

    public String getDes_etor_floor_show() {
        return des_etor_floor_show;
    }

    public void setDes_etor_floor_show(String des_etor_floor_show) {
        this.des_etor_floor_show = des_etor_floor_show;
    }
}
