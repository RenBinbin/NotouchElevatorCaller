package com.canny.no_touch_elevator.webapi.response;

import com.contrarywind.interfaces.IPickerViewData;

import java.util.List;

/**
 * Created by 1006177 on 2020/3/13.
 */

public class ElevatorBean implements IPickerViewData {
    private String name;
    private List<MsgBean> city;
    @Override
    public String getPickerViewText() {
        return name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<MsgBean> getCity() {
        return city;
    }

    public void setCity(List<MsgBean> city) {
        this.city = city;
    }

    public static class MsgBean {
        private String name;
        private List<String> area;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<String> getArea() {
            return area;
        }

        public void setArea(List<String> area) {
            this.area = area;
        }
    }
}

