package com.canny.no_touch_elevator.webapi;


/**
 * Created by renbinbin1 on 2018/7/12.
 */

public interface CannyCallback {
    void onFailure(String method, String reason, Object other);
    void onSuccess(String method, Object result, Object other);
}
