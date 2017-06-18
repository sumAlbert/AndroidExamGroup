package com.example.dell2.androidexamgroup;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapt.Test6_ZQ_Adapt;
import broadcast.Test5_ZQ_BroadcastReceiver;
import collector.BaseActivity;

/**
 * Created by dell2 on 2017/6/16.
 */

public class Test5_ZQ_Activity extends BaseActivity implements View.OnClickListener{
    private LinearLayout test5_ZQ_LL_1;
    private Test5_ZQ_BroadcastReceiver test5_zq_broadcastReceiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_test5_zq);
        init();
    }
    public void init(){
        IntentFilter intentFilter=new IntentFilter();
        intentFilter.addAction("com.example.broadcast.Test5_zq");
        test5_zq_broadcastReceiver=new Test5_ZQ_BroadcastReceiver();
        registerReceiver(test5_zq_broadcastReceiver,intentFilter);
        LinearLayout test5_ZQ_LL_1=(LinearLayout)findViewById(R.id.test5_ZQ_LL_1);
        test5_ZQ_LL_1.setOnClickListener(this);
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Test5_ZQ_Activity.class);
        context.startActivity(intent);
    }
    public void onClick(View view){
        switch (view.getId()){
            case R.id.test5_ZQ_LL_1:
                Intent intent=new Intent("com.example.broadcast.Test5_zq");
                sendBroadcast(intent);
                break;
            default:
                break;
        }
    }
}
