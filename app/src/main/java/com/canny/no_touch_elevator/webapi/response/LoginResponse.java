package com.canny.no_touch_elevator.webapi.response;

/**
 * Created by 1006177 on 2020/2/18.
 */

public class LoginResponse {
    private int result;
    private String msg;

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
