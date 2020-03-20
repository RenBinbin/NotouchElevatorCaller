package com.canny.no_touch_elevator.webapi.response;

import java.util.List;

/**
 * Created by 1006177 on 2020/2/20.
 */

public class UserBuildResponse{

    /**
     * result : 1
     * msg : [{"buildName":"汾湖康力大道888号","etorList":[{"bianhao":"10027038-0020","etor_id":953,"build_number":"一期综合楼","build_etorindex":"T2","etor_index":1,"etor_index_show":"T2","last_call_from":"5","last_call_to":"1"}]},{"buildName":"康力大道799号新里程","etorList":[{"bianhao":"10018311-0030-03","etor_id":37338,"build_number":"北侧","build_etorindex":"货梯","etor_index":1,"etor_index_show":"甲","last_call_from":"1","last_call_to":"3"},{"bianhao":"TEST-YD","etor_id":47353,"build_number":"新里程","build_etorindex":"观光西","etor_index":2,"etor_index_show":"右边副梯","last_call_from":"3","last_call_to":"1"},{"bianhao":"TEST-D13","etor_id":47357,"build_number":"新里程","build_etorindex":"观光东","etor_index":1,"etor_index_show":"左边主梯","last_call_from":"3","last_call_to":"1"}]},{"buildName":"康力电梯","etorList":[{"bianhao":"TestKLA-group-1","etor_id":48407,"build_number":"办公室测试","build_etorindex":"","etor_index":1,"etor_index_show":"A","last_call_from":"4","last_call_to":"1"}]}]
     */

    private int result;
    private List<MsgBean> msg;

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
         * buildName : 汾湖康力大道888号
         * etorList : [{"bianhao":"10027038-0020","etor_id":953,"build_number":"一期综合楼","build_etorindex":"T2","etor_index":1,"etor_index_show":"T2","last_call_from":"5","last_call_to":"1"}]
         */

        private String buildName;
        private List<EtorListBean> etorList;

        public String getBuildName() {
            return buildName;
        }

        public void setBuildName(String buildName) {
            this.buildName = buildName;
        }

        public List<EtorListBean> getEtorList() {
            return etorList;
        }

        public void setEtorList(List<EtorListBean> etorList) {
            this.etorList = etorList;
        }

        public static class EtorListBean {
            /**
             * bianhao : 10027038-0020
             * etor_id : 953
             * build_number : 一期综合楼
             * build_etorindex : T2
             * etor_index : 1
             * etor_index_show : T2
             * last_call_from : 5
             * last_call_to : 1
             */

            private String bianhao;
            private int etor_id;
            private String build_number;
            private String build_etorindex;
            private int etor_index;
            private String etor_index_show;
            private String last_call_from;
            private String last_call_to;

            public String getBianhao() {
                return bianhao;
            }

            public void setBianhao(String bianhao) {
                this.bianhao = bianhao;
            }

            public int getEtor_id() {
                return etor_id;
            }

            public void setEtor_id(int etor_id) {
                this.etor_id = etor_id;
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

            public int getEtor_index() {
                return etor_index;
            }

            public void setEtor_index(int etor_index) {
                this.etor_index = etor_index;
            }

            public String getEtor_index_show() {
                return etor_index_show;
            }

            public void setEtor_index_show(String etor_index_show) {
                this.etor_index_show = etor_index_show;
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
}
