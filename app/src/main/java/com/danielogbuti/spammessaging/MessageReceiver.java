package com.danielogbuti.spammessaging;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;

import java.util.ArrayList;
import java.util.List;

public class MessageReceiver extends BroadcastReceiver {
    private static MessageListener mListener;
    List<String> messages;

    @Override
    public void onReceive(Context context, Intent intent) {
        messages = new ArrayList<>();
        Bundle data = intent.getExtras();
        Object[] pdus = (Object[]) data.get("pdus");
        for (int i = 0; i < pdus.length; i++) {
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String message = smsMessage.getDisplayOriginatingAddress();
//                    + "Display message body: "+smsMessage.getDisplayMessageBody()
//                    + "message: "+ smsMessage.getMessageBody();
            String message1 = smsMessage.getDisplayMessageBody() + " "+ smsMessage.getMessageBody();
            messages.add(0,message);
            messages.add(1,message1);
            mListener.messageReceived(messages);
        }
    }
    public static void bindListener(MessageListener listener){
        mListener = listener;
    }
}
