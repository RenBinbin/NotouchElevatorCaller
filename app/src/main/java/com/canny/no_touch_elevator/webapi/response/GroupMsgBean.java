package com.canny.no_touch_elevator.webapi.response;

import java.util.List;

public class GroupMsgBean {
    private List<MsgBean> msg;

    public List<MsgBean> getMsg() {
        return msg;
    }

    public void setMsg(List<MsgBean> msg) {
        this.msg = msg;
    }

    public static class MsgBean {
        private int Index;
        private String Floor;
        private String Direction;
        private boolean IsOpening;
        private boolean IsClosing;
        private boolean IsDoorLock;
        private boolean IsInGroup;
        private boolean Bak;

        public int getIndex() {
            return Index;
        }

        public void setIndex(int index) {
            Index = index;
        }

        public String getFloor() {
            return Floor;
        }

        public void setFloor(String floor) {
            Floor = floor;
        }

        public String getDirection() {
            return Direction;
        }

        public void setDirection(String direction) {
            Direction = direction;
        }

        public boolean isOpening() {
            return IsOpening;
        }

        public void setOpening(boolean opening) {
            IsOpening = opening;
        }

        public boolean isClosing() {
            return IsClosing;
        }

        public void setClosing(boolean isClosing) {
            IsClosing = isClosing;
        }

        public boolean isDoorLock() {
            return IsDoorLock;
        }

        public void setDoorLock(boolean doorLock) {
            IsDoorLock = doorLock;
        }

        public boolean isInGroup() {
            return IsInGroup;
        }

        public void setInGroup(boolean inGroup) {
            IsInGroup = inGroup;
        }

        public boolean isBak() {
            return Bak;
        }

        public void setBak(boolean bak) {
            Bak = bak;
        }
    }
}
