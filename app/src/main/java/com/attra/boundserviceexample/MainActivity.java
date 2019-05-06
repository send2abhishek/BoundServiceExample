package com.attra.boundserviceexample;

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.attra.boundserviceexample.Services.MyBoundService;

public class MainActivity extends AppCompatActivity {

    public static final String TAG="MyTag";
    
    private MyBoundService myBoundService;
    private boolean isServiceConnected=false;
    private ServiceConnection connection=new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            
            
            MyBoundService.MyBinder myBinder= (MyBoundService.MyBinder) service;
            myBoundService=myBinder.getService();
            Log.d(TAG, "onServiceConnected: ");
            isServiceConnected=true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();

        Intent intent=new Intent(this, MyBoundService.class);
        bindService(intent,connection, Service.BIND_AUTO_CREATE);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if(isServiceConnected){
            unbindService(connection);
            Log.d(TAG, "onStop: Service unbinded");
        }
    }
}
