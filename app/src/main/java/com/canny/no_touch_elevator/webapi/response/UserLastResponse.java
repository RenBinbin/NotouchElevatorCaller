package com.canny.no_touch_elevator.webapi.response;

import java.util.List;

/**
 * Created by 1006177 on 2020/3/5.
 */

public class UserLastResponse{

    /**
     * result : 1
     * msg : [{"bianhao":"TEST-YD","buildName":"康力大道799号新里程","build_number":"新里程",
     * "build_etorindex":"观光西","etor_index_show":"右边副梯","etor_index":2,"last_call_from":"1","last_call_to":"3"}]
     *
     * buildName  //  build_number +etor_index_show
     */

    private int result;
    private List<MsgBean> msg;

    public UserLastResponse(int result, List<MsgBean> msg) {
        this.result = result;
        this.msg = msg;
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        /**
         * bianhao : TEST-YD
         * buildName : 康力大道799号新里程
         * build_number : 新里程
         * build_etorindex : 观光西
         * etor_index_show : 右边副梯
         * etor_index : 2
         * last_call_from : 1
         * last_call_to : 3
         */

        private String bianhao;
        private String buildName;
        private String build_number;
        private String build_etorindex;
        private String etor_index_show;
        private int etor_index;
        private String last_call_from;
        private String last_call_to;

        public String getBianhao() {
            return bianhao;
        }

        public void setBianhao(String bianhao) {
            this.bianhao = bianhao;
        }

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
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

        public String getEtor_index_show() {
            return etor_index_show;
        }

        public void setEtor_index_show(String etor_index_show) {
            this.etor_index_show = etor_index_show;
        }

        public int getEtor_index() {
            return etor_index;
        }

        public void setEtor_index(int etor_index) {
            this.etor_index = etor_index;
        }

        public String getLast_call_from() {
            return last_call_from;
        }

        public void setLast_call_from(String last_call_from) {
            this.last_call_from = last_call_from;
        }

        public String getLast_call_to() {
            return last_call_to;
        }

        public void setLast_call_to(String last_call_to) {
            this.last_call_to = last_call_to;
        }
    }
}
