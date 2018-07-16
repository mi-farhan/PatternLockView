package com.gss.patternlockview;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class SimInfoActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<String> simDetailList;
    StringBuffer output;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sim_info);

        output = new StringBuffer();
        mListView = (ListView) findViewById(R.id.simList);
        simDetailList = new ArrayList<String>();

        TelephonyManager tm = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String imeiNumber1 = tm.getDeviceId(1); //(API level 23)
        String imeiNumber2 = tm.getDeviceId(2);

        Log.d("sim1", imeiNumber1);
        Log.d("sim2", imeiNumber2);


        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        String getSerialNumber = tm.getSimSerialNumber();
        //output.append("\n Serial Number : "+getSerialNumber);
        simDetailList.add("\nSerial Number : "+"\n \n"+getSerialNumber);
        String getSubscriberId=tm.getSubscriberId();
        //output.append("\n Subscriber Id : "+getSubscriberId);
        simDetailList.add("\nSubscriber ID"+"\n \n"+getSubscriberId);
        String getdetail1=tm.getNetworkOperator();
        //output.append("\n Network Operator code : "+getdetail1);
        simDetailList.add("\nNetwork Operator Code : "+"\n \n"+getdetail1);
        String getdetail2=tm.getLine1Number();
        //output.append("\n Mobile Number : "+getdetail2);
        simDetailList.add("\nMobile Number : "+"\n \n"+getdetail2);
        String getdetail3=tm.getNetworkOperatorName();
        //output.append("\n Network Operaot Name : "+getdetail3);
        simDetailList.add("\nNetwork Operator Name : "+"\n \n"+getdetail3);
        String getdetail4=tm.getSimOperatorName();
        //output.append("\n Sim operator Name : "+getdetail4);
        simDetailList.add("\nSim operator name : "+"\n \n"+getdetail4);
        //simDetailList.add(output.toString());

        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.sim_list_item,R.id.tv_simInfoText,simDetailList);
        mListView.setAdapter(adapter);
        Log.d("SerialNumber",getSerialNumber);
        Log.d("Subscriber Id",getSubscriberId);
        Log.d("Network Operator : ",getdetail1);
        Log.d("Line Number : ",getdetail2);
        Log.d("Network Operator Name: ",getdetail3);
        Log.d("Sim Operator Name : ",getdetail4);

    }
}
