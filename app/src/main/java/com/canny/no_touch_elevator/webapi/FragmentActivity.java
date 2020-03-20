package com.canny.no_touch_elevator.webapi;

/**
 * Created by 1006177 on 2020/3/5.
 */

public interface FragmentActivity {
    void onFailure(String method, String reason, Object other);
    void onSuccess(String method, Object result, Object other);
}
