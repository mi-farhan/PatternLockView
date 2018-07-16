package com.gss.patternlockview;

import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class BatteryInfoActivity extends AppCompatActivity {


    Button btn;
    IntentFilter intentfilter;
    int deviceStatus;
    int status;
    boolean isCharging;
    String currentBatteryStatus="Battery Info";
    TextView tv;
    int lessCount;
    int enoughCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_battery_info);

        intentfilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        //btn=(Button)findViewById(R.id.btn_check);

        lessCount=0;
        enoughCount=0;

        Log.d("Button","Pressed");

        registerReceiver(BatteryInfoActivity.this.broadcastReceiver,intentfilter);

        /*btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lessCount=0;
                enoughCount=0;
                Log.d("Button","Pressed");
                registerReceiver(BatteryInfoActivity.this.broadcastReceiver,intentfilter);
            }
        });*/
        tv = (TextView)findViewById(R.id.level);

    }

    BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
            Intent battery= context.registerReceiver(null, ifilter);
            int status = battery.getIntExtra(BatteryManager.EXTRA_STATUS, -1);
            boolean isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING ||
                    status == BatteryManager.BATTERY_STATUS_FULL;


            deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS,-1);
            Log.d("Battery", String.valueOf(deviceStatus));
            int level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, -1);
            int scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, -1);
            int batteryLevel=(int)(((float)level / (float)scale) * 100.0f);


            tv.setText("Total Charging:  "+Integer.toString(batteryLevel)+ " % ");
            if (batteryLevel <= 10 && lessCount==0){

                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(BatteryInfoActivity.this);
                alertBuilder.setMessage("Battery is Less Than 10%");
                AlertDialog alertDialog=alertBuilder.create();
                alertDialog.show();
                lessCount++;
            }else if (enoughCount==0){
                AlertDialog.Builder alertBuilder=new AlertDialog.Builder(BatteryInfoActivity.this);
                alertBuilder.setMessage("Enough Battery");
                AlertDialog alertDialog=alertBuilder.create();
                alertDialog.show();
                enoughCount++;
            }

            if (isCharging){
                       Log.d("Battery","Charger Connected");
                Toast.makeText(context,"Charger Connected",Toast.LENGTH_LONG).show();
            }else if (!isCharging){
                       Log.d("Battery","Charger Disconnected");
                Toast.makeText(context,"Charger  Disconnected",Toast.LENGTH_LONG).show();
            }else {
                Log.d("Battery","Charger Disconnected");
            }

            if (deviceStatus == BatteryManager.BATTERY_STATUS_FULL){
                Log.d("Battery","Full");
                Toast.makeText(BatteryInfoActivity.this,"Charging full",Toast.LENGTH_LONG).show();

            }else if(deviceStatus == BatteryManager.BATTERY_STATUS_UNKNOWN){
                Log.d("Battery","unkown");
                Toast.makeText(BatteryInfoActivity.this,"Unknown",Toast.LENGTH_LONG).show();
            }else if (deviceStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING){
                Log.d("Battery","Not Charging");
                Toast.makeText(BatteryInfoActivity.this,"Charger Removed",Toast.LENGTH_LONG).show();

            }

        }
    };
}
