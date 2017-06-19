package com.example.dell2.androidexamgroup;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import collector.BaseActivity;

public class Test2_WYF_Activity extends AppCompatActivity {

    private MyService.MyBinder mBinder;
    private ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mBinder = (MyService.MyBinder)service;
            mBinder.start();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d("stop","stop");
        }
    };
    private Button myb;
    private Button myb1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test2_wyf);
        Log.d("test2","start");
        myb=(Button)findViewById(R.id.startButton);
        myb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Test2_WYF_Activity.this,MyService.class);
                startService(intent);
                bindService(intent,serviceConnection,BIND_AUTO_CREATE);
            }
        });
        myb1=(Button)findViewById(R.id.stopButton);
        myb1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //顺序不能反，否则服务停不掉
                Intent intent = new Intent(Test2_WYF_Activity.this,MyService.class);
                unbindService(serviceConnection);
                stopService(intent);
                Log.d("stop","stop");
            }
        });
    }



}
