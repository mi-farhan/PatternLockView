package com.gss.patternlockview.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * Created by geniee4 on 27/6/18.
 */


public class PlugInControlReceiver extends BroadcastReceiver {
    public void onReceive(Context context , Intent intent) {
        String action = intent.getAction();

        if(action.equals(Intent.ACTION_POWER_CONNECTED)) {
            // Do something when power connected
        }
        else if(action.equals(Intent.ACTION_POWER_DISCONNECTED)) {
            // Do something when power disconnected
        }
    }
}