package com.gss.patternlockview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.andrognito.patternlockview.PatternLockView;
import com.andrognito.patternlockview.listener.PatternLockViewListener;
import com.andrognito.patternlockview.utils.PatternLockUtils;

import java.util.List;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    String save_pattern_key = "pattern_code";
    PatternLockView mPatternLockView;
    String final_pattern = "";
    private HiddenCameraFragment mHiddenCameraFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Paper.init(this);
        final String save_pattern = Paper.book().read(save_pattern_key);
        if (save_pattern != null && !save_pattern.equals("null"))
        {
            setContentView(R.layout.pattern_screen);
            mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener()   {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern);
                    if (final_pattern.equals(save_pattern)){
                        Toast.makeText(MainActivity.this, "Correct Pattern", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(MainActivity.this,HomeActivity.class);
                        startActivity(intent);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Wrong Pattern", Toast.LENGTH_SHORT).show();

                       /* Intent intent = new Intent(MainActivity.this,AppListWithIconActivity.class);
                        startActivity(intent);*/

                        if (mHiddenCameraFragment != null) {    //Remove fragment from container if present
                            getSupportFragmentManager()
                                    .beginTransaction()
                                    .remove(mHiddenCameraFragment)
                                    .commit();
                            mHiddenCameraFragment = null;
                        }

                        startService(new Intent(MainActivity.this, DemoCamService.class));
                    }
                }

                @Override
                public void onCleared() {

                }
            });

        }else{
            setContentView(R.layout.activity_main);
            mPatternLockView = (PatternLockView) findViewById(R.id.pattern_lock_view);
            mPatternLockView.addPatternLockListener(new PatternLockViewListener() {
                @Override
                public void onStarted() {

                }

                @Override
                public void onProgress(List<PatternLockView.Dot> progressPattern) {

                }

                @Override
                public void onComplete(List<PatternLockView.Dot> pattern) {
                    final_pattern = PatternLockUtils.patternToString(mPatternLockView, pattern);

                }

                @Override
                public void onCleared() {

                }
            });

            Button btnSetup = (Button) findViewById(R.id.btnSetPattern);

            btnSetup.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Paper.book().write(save_pattern_key,final_pattern);
                    Toast.makeText(MainActivity.this, "Save Pattern Okay!!!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }
    }
}
