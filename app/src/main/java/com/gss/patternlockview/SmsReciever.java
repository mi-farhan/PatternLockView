package com.gss.patternlockview;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsMessage;
import android.util.Log;

/**
 * Created by geniee4 on 23/6/18.
 */


public class SmsReciever extends BroadcastReceiver {
    private static SmsListener smsListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Method","OnReceive");
        Bundle data=intent.getExtras();
        Object[] pdus=(Object[]) data.get("pdus");

        for(int i=0;i<pdus.length;i++){
            SmsMessage smsMessage = SmsMessage.createFromPdu((byte[]) pdus[i]);
            String msg=smsMessage.getMessageBody();
            String sender = smsMessage.getDisplayOriginatingAddress();
            //Check the sender to filter messages which we require to read
            Log.d("sender",msg);
            if (sender.equals("TM-WAYSMS")){
                Log.d("sender",sender);
                smsListener.messageReceived(msg);
            }

        }
    }

    public static void bindListener(SmsListener listener) {
        smsListener = listener;
        Log.d("Method","BindListener");
    }
}
