package com.canny.no_touch_elevator.webapi.response;

import java.util.List;

/**
 * Created by 1006177 on 2020/3/11.
 */

public class UserInfoBean {


    /**
     * userinfo : {"_id":8892,"_bianhao":"1006177","_account":"1006177","_pwd":"C6994BFECF88E9E1","_secre_key":"VJ4T","_user_type":1,"_realname":"任斌斌","_realname_spell":"RenBinBin","_sub_name":"","_sex":1,"_title":"1","_email":"renbinbin@canny-elevator.com","_phone":"13621566571","_caption":"cn=任斌斌,ou=KL_智能部件研发部,ou=康力电梯股份有限公司,ou=康力集团,dc=canny-elevator,dc=com","_enabled":1,"_single_login":0,"_mobile_login":1,"_is_limitip":0,"_is_alarm":1,"_is_sys":0,"_create_time":"2017-07-01T10:45:09","_create_userid":0,"_remark":"Android","_field_1":"","_field_2":"","_field_3":"","_open_id":"13621566571a00000552e111e"}
     * isOwner : true
     * ownerList : [{"etor_bianhao":"TEST-YD","build_number":"新里程","build_etorindex":"观光西","etor_index":2,"etor_index_show":"右边副梯"}]
     * isUser : false
     * userList : []
     * isWhiteList : false
     * whiteList :
     */

    private UserinfoBean userinfo;
    private boolean isOwner;
    private boolean isUser;
    private boolean isWhiteList;
    private String whiteList;
    private List<OwnerListBean> ownerList;
    private List<?> userList;

    public UserinfoBean getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(UserinfoBean userinfo) {
        this.userinfo = userinfo;
    }

    public boolean isIsOwner() {
        return isOwner;
    }

    public void setIsOwner(boolean isOwner) {
        this.isOwner = isOwner;
    }

    public boolean isIsUser() {
        return isUser;
    }

    public void setIsUser(boolean isUser) {
        this.isUser = isUser;
    }

    public boolean isIsWhiteList() {
        return isWhiteList;
    }

    public void setIsWhiteList(boolean isWhiteList) {
        this.isWhiteList = isWhiteList;
    }

    public String getWhiteList() {
        return whiteList;
    }

    public void setWhiteList(String whiteList) {
        this.whiteList = whiteList;
    }

    public List<OwnerListBean> getOwnerList() {
        return ownerList;
    }

    public void setOwnerList(List<OwnerListBean> ownerList) {
        this.ownerList = ownerList;
    }

    public List<?> getUserList() {
        return userList;
    }

    public void setUserList(List<?> userList) {
        this.userList = userList;
    }

    public static class UserinfoBean {
        /**
         * _id : 8892
         * _bianhao : 1006177
         * _account : 1006177
         * _pwd : C6994BFECF88E9E1
         * _secre_key : VJ4T
         * _user_type : 1
         * _realname : 任斌斌
         * _realname_spell : RenBinBin
         * _sub_name :
         * _sex : 1
         * _title : 1
         * _email : renbinbin@canny-elevator.com
         * _phone : 13621566571
         * _caption : cn=任斌斌,ou=KL_智能部件研发部,ou=康力电梯股份有限公司,ou=康力集团,dc=canny-elevator,dc=com
         * _enabled : 1
         * _single_login : 0
         * _mobile_login : 1
         * _is_limitip : 0
         * _is_alarm : 1
         * _is_sys : 0
         * _create_time : 2017-07-01T10:45:09
         * _create_userid : 0
         * _remark : Android
         * _field_1 :
         * _field_2 :
         * _field_3 :
         * _open_id : 13621566571a00000552e111e
         */

        private int _id;
        private String _bianhao;
        private String _account;
        private String _pwd;
        private String _secre_key;
        private int _user_type;
        private String _realname;
        private String _realname_spell;
        private String _sub_name;
        private int _sex;
        private String _title;
        private String _email;
        private String _phone;
        private String _caption;
        private int _enabled;
        private int _single_login;
        private int _mobile_login;
        private int _is_limitip;
        private int _is_alarm;
        private int _is_sys;
        private String _create_time;
        private int _create_userid;
        private String _remark;
        private String _field_1;
        private String _field_2;
        private String _field_3;
        private String _open_id;

        public int get_id() {
            return _id;
        }

        public void set_id(int _id) {
            this._id = _id;
        }

        public String get_bianhao() {
            return _bianhao;
        }

        public void set_bianhao(String _bianhao) {
            this._bianhao = _bianhao;
        }

        public String get_account() {
            return _account;
        }

        public void set_account(String _account) {
            this._account = _account;
        }

        public String get_pwd() {
            return _pwd;
        }

        public void set_pwd(String _pwd) {
            this._pwd = _pwd;
        }

        public String get_secre_key() {
            return _secre_key;
        }

        public void set_secre_key(String _secre_key) {
            this._secre_key = _secre_key;
        }

        public int get_user_type() {
            return _user_type;
        }

        public void set_user_type(int _user_type) {
            this._user_type = _user_type;
        }

        public String get_realname() {
            return _realname;
        }

        public void set_realname(String _realname) {
            this._realname = _realname;
        }

        public String get_realname_spell() {
            return _realname_spell;
        }

        public void set_realname_spell(String _realname_spell) {
            this._realname_spell = _realname_spell;
        }

        public String get_sub_name() {
            return _sub_name;
        }

        public void set_sub_name(String _sub_name) {
            this._sub_name = _sub_name;
        }

        public int get_sex() {
            return _sex;
        }

        public void set_sex(int _sex) {
            this._sex = _sex;
        }

        public String get_title() {
            return _title;
        }

        public void set_title(String _title) {
            this._title = _title;
        }

        public String get_email() {
            return _email;
        }

        public void set_email(String _email) {
            this._email = _email;
        }

        public String get_phone() {
            return _phone;
        }

        public void set_phone(String _phone) {
            this._phone = _phone;
        }

        public String get_caption() {
            return _caption;
        }

        public void set_caption(String _caption) {
            this._caption = _caption;
        }

        public int get_enabled() {
            return _enabled;
        }

        public void set_enabled(int _enabled) {
            this._enabled = _enabled;
        }

        public int get_single_login() {
            return _single_login;
        }

        public void set_single_login(int _single_login) {
            this._single_login = _single_login;
        }

        public int get_mobile_login() {
            return _mobile_login;
        }

        public void set_mobile_login(int _mobile_login) {
            this._mobile_login = _mobile_login;
        }

        public int get_is_limitip() {
            return _is_limitip;
        }

        public void set_is_limitip(int _is_limitip) {
            this._is_limitip = _is_limitip;
        }

        public int get_is_alarm() {
            return _is_alarm;
        }

        public void set_is_alarm(int _is_alarm) {
            this._is_alarm = _is_alarm;
        }

        public int get_is_sys() {
            return _is_sys;
        }

        public void set_is_sys(int _is_sys) {
            this._is_sys = _is_sys;
        }

        public String get_create_time() {
            return _create_time;
        }

        public void set_create_time(String _create_time) {
            this._create_time = _create_time;
        }

        public int get_create_userid() {
            return _create_userid;
        }

        public void set_create_userid(int _create_userid) {
            this._create_userid = _create_userid;
        }

        public String get_remark() {
            return _remark;
        }

        public void set_remark(String _remark) {
            this._remark = _remark;
        }

        public String get_field_1() {
            return _field_1;
        }

        public void set_field_1(String _field_1) {
            this._field_1 = _field_1;
        }

        public String get_field_2() {
            return _field_2;
        }

        public void set_field_2(String _field_2) {
            this._field_2 = _field_2;
        }

        public String get_field_3() {
            return _field_3;
        }

        public void set_field_3(String _field_3) {
            this._field_3 = _field_3;
        }

        public String get_open_id() {
            return _open_id;
        }

        public void set_open_id(String _open_id) {
            this._open_id = _open_id;
        }
    }

    public static class OwnerListBean {
        /**
         * etor_bianhao : TEST-YD
         * build_number : 新里程
         * build_etorindex : 观光西
         * etor_index : 2
         * etor_index_show : 右边副梯
         */

        private String etor_bianhao;
        private String build_number;
        private String build_etorindex;
        private int etor_index;
        private String etor_index_show;

        public String getEtor_bianhao() {
            return etor_bianhao;
        }

        public void setEtor_bianhao(String etor_bianhao) {
            this.etor_bianhao = etor_bianhao;
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
    }
}
