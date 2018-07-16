package com.gss.patternlockview;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class InstallAppListActivity extends AppCompatActivity {

    ListView mListView;
    ArrayList<String> apps= new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_install_app_list);


        mListView=(ListView)findViewById(R.id.lv_installapplist);

        getAppPackages();

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                try {
                    getGrantedPermissions(apps.get(position));
                } catch (PackageManager.NameNotFoundException e) {
                    e.printStackTrace();
                }
            }
        });

    }


    private void getGrantedPermissions(String s) throws PackageManager.NameNotFoundException {
        ArrayList<String> permissions=new ArrayList<String>();
        PackageInfo pi=getPackageManager().getPackageInfo(s,PackageManager.GET_PERMISSIONS);
        for (int i=0;i<pi.requestedPermissions.length;i++){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0){
                    String st[]=pi.requestedPermissions;
                    for (String p:st) {
                        permissions.add(p);
                    }
                }
            }
        }
        Toast.makeText(getApplicationContext(),"Permissions : \n"+permissions,Toast.LENGTH_LONG).show();
    }

   /* ArrayList<String> getGrantedPermissions(final String appPackage) {

        try {
            PackageInfo pi = getPackageManager().getPackageInfo(appPackage, PackageManager.GET_PERMISSIONS);
            for (int i = 0; i < pi.requestedPermissions.length; i++) {
                if ((pi.requestedPermissionsFlags[i] & PackageInfo.REQUESTED_PERMISSION_GRANTED) != 0) {
                    Log.d("Package",appPackage);
                    Log.d("Permission",pi.requestedPermissions.toString());
                    granted.add(pi.requestedPermissions[i]);
                }
            }
        } catch (Exception e) {
        }
        return granted;
    }*/

    public void getAppPackages(){
        final PackageManager pm = getPackageManager();
//get a list of installed apps.
        List<ApplicationInfo> packages = pm.getInstalledApplications(PackageManager.GET_META_DATA);

        for (ApplicationInfo packageInfo : packages) {
            Log.d("Package NAme",packageInfo.packageName);
            apps.add(packageInfo.packageName);
            //getGrantedPermissions(packageInfo.packageName);
        }
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.sim_list_item,R.id.tv_simInfoText,apps);
        mListView.setAdapter(adapter);
    }
}
