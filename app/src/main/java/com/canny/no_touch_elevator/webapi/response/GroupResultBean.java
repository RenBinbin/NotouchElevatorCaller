package com.canny.no_touch_elevator.webapi.response;

import java.util.List;

public class GroupResultBean {

    /**
     * result : 1
     * msg : [{"Index":1,"Floor":"","Direction":0,"IsOpening":false,"IsClosing":false,"IsDoorLock":false,"IsInGroup":false,"Bak":false},{"Index":2,"Floor":" 5","Direction":0,"IsOpening":false,"IsClosing":false,"IsDoorLock":true,"IsInGroup":true,"Bak":false},{"Index":3,"Floor":"B1","Direction":0,"IsOpening":false,"IsClosing":false,"IsDoorLock":true,"IsInGroup":true,"Bak":false},{"Index":4,"Floor":"B1","Direction":0,"IsOpening":false,"IsClosing":false,"IsDoorLock":true,"IsInGroup":true,"Bak":false},{"Index":5,"Floor":"","Direction":0,"IsOpening":false,"IsClosing":false,"IsDoorLock":false,"IsInGroup":false,"Bak":false},{"Index":6,"Floor":"","Direction":0,"IsOpening":false,"IsClosing":false,"IsDoorLock":false,"IsInGroup":false,"Bak":false},{"Index":7,"Floor":"","Direction":0,"IsOpening":false,"IsClosing":false,"IsDoorLock":false,"IsInGroup":false,"Bak":false},{"Index":8,"Floor":"","Direction":0,"IsOpening":false,"IsClosing":false,"IsDoorLock":false,"IsInGroup":false,"Bak":false}]
     */

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
