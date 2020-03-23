package com.canny.no_touch_elevator.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.telephony.SmsMessage;

public class SMSReceiver extends BroadcastReceiver {

    public static final String ACTION_SMS_RECEIVER = "android.provider.Telephony.SMS_RECEIVED";

    final String MsgHead="【康力电梯】";
    ISmsReceived msgGetHandle;
    public SMSReceiver(ISmsReceived msgHandle){
        super();
        msgGetHandle=msgHandle;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(ACTION_SMS_RECEIVER)){
            Object[] pdus=(Object[])intent.getExtras().get("pdus");
            //不知道为什么明明只有一条消息，传过来的却是数组，也许是为了处理同时同分同秒同毫秒收到多条短信
            //但这个概率有点小
            SmsMessage[] message=new SmsMessage[pdus.length];
            StringBuilder sb=new StringBuilder();

            for(int i=0;i<pdus.length;i++){
                //虽然是循环，其实pdus长度一般都是1
                message[i]=SmsMessage.createFromPdu((byte[])pdus[i]);
                sb.append("接收到短信来自:\n");
                String address=message[i].getDisplayOriginatingAddress();
                sb.append(address+"\n");
                String msgBody=message[i].getDisplayMessageBody();
                sb.append("内容:"+msgBody);

                if(msgBody.startsWith((MsgHead))){
                    String passcode=msgBody.substring(MsgHead.length());
                    if(4==passcode.length()){
                        msgGetHandle.onMsgGet(passcode);
                    }
                }
            }
            System.out.println(sb.toString());
        }
    }

}