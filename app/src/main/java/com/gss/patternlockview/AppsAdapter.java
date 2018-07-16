package com.gss.patternlockview;

import android.app.Notification;
import android.app.NotificationChannelGroup;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

/**
 * Created by geniee4 on 23/6/18.
 */



public class AppsAdapter extends RecyclerView.Adapter<AppsAdapter.ViewHolder>{

    public static String PACKAGE_NAME;


    Context context1;
    List<String> stringList;
    private String[] mList;

    public AppsAdapter(Context context, List<String> list){

        context1 = context;

        stringList = list;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        private Context mContext;
        private String[] mList;

        public CardView cardView;
        public ImageView imageView;
        public TextView textView_App_Name;
        public TextView textView_App_Package_Name;
        public Button onOff;

        public ViewHolder (View view){

            super(view);

            cardView = (CardView) view.findViewById(R.id.card_view);
            imageView = (ImageView) view.findViewById(R.id.imageview);
            textView_App_Name = (TextView) view.findViewById(R.id.Apk_Name);
            textView_App_Package_Name = (TextView) view.findViewById(R.id.Apk_Package_Name);
            onOff = (Button) view.findViewById(R.id.btn_onoff);

        }
    }

    @Override
    public AppsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        View view2 = LayoutInflater.from(context1).inflate(R.layout.cardview_layout,parent,false);

        ViewHolder viewHolder = new ViewHolder(view2);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int position){

        ApkInfoExtractor apkInfoExtractor = new ApkInfoExtractor(context1);

        final String ApplicationPackageName = (String) stringList.get(position);
        String ApplicationLabelName = apkInfoExtractor.GetAppName(ApplicationPackageName);
        Drawable drawable = apkInfoExtractor.getAppIconByPackageName(ApplicationPackageName);
        final String appApkInfo = String.valueOf(apkInfoExtractor.GetAllInstalledApkInfo());

        viewHolder.textView_App_Name.setText(ApplicationLabelName);

        viewHolder.textView_App_Package_Name.setText(ApplicationPackageName);

        viewHolder.imageView.setImageDrawable(drawable);

               //Adding click listener on CardView to open clicked application directly from here .
        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context1,"The Item Clicked is: "+position,Toast.LENGTH_SHORT).show();

                Intent intent = context1.getPackageManager().getLaunchIntentForPackage(ApplicationPackageName);
                if(intent != null){
                context1.startActivity(intent);
                }
                else {
                    Toast.makeText(context1,ApplicationPackageName + " Error, Please Try Again.", Toast.LENGTH_LONG).show();
                }
            }
        });

        viewHolder.onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Toast.makeText(context1,"The Item Clicked is: "+position,Toast.LENGTH_SHORT).show();

                Setting(context1);

                //context1.startActivity(new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS));//throws Exception
                //context1.startActivity(new Intent(Settings.ACTION_DISPLAY_SETTINGS));
                //context1.startActivity(new Intent(Settings.ACTION_DEVICE_INFO_SETTINGS));
                //context1.startActivity(new Intent(Settings.ACTION_SECURITY_SETTINGS));
                //context1.startActivity(new Intent(android.provider.Settings.ACTION_WIFI_SETTINGS));
                //context1.startActivity(new Intent(Settings.ACTION_NOTIFICATION_POLICY_ACCESS_SETTINGS));
                //context1.startActivity(new Intent(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS));



                /*//This code for Application Details Setting
                Intent intent = new Intent(Settings.ACTION_CHANNEL_NOTIFICATION_SETTINGS);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                Uri uri = Uri.fromParts("package", view.getContext().getPackageName(), null);
                intent.setData(uri);
                context1.startActivity(intent);*/
            }
        });

        /*viewHolder.onOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context1, "#" + position + " - " + ApkInfoExtractor[position] + " (Long click)", Toast.LENGTH_SHORT).show();
            }
        });*/

       /* ItemClickListener() {
            @Override
            public void onClick(View view, int position, boolean isLongClick) {
                if (isLongClick) {
                    Toast.makeText(context1, "#" + position + " - " + mList[position] + " (Long click)", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(context1, "#" + position + " - " + mList[position], Toast.LENGTH_SHORT).show();
                }
            }
        });*/
    }

    @Override
    public int getItemCount(){

        return stringList.size();
    }

    public void Setting(Context context)
    {
        Intent intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}