package com.pm.an;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

import com.common.ux.LoadingDialog;

/**
 * @author pm
 */
public class MainActivity extends AppCompatActivity {

    private Intent intent;
    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {

        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        intent = new Intent(MainActivity.this, MyService.class);
//        startService(intent);
        bindService(intent, connection, Context.BIND_ADJUST_WITH_ACTIVITY);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        bindService(intent, connection, Context.BIND_ADJUST_WITH_ACTIVITY);
    }


    @Override
    protected void onResume() {
        super.onResume();
        startService(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        unbindService(connection);
//        stopService(intent);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
