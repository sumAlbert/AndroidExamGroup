package com.example.dell2.androidexamgroup;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapt.Test6_ZQ_Adapt;
import collector.BaseActivity;

/**
 * Created by dell2 on 2017/6/16.
 */

public class Test5_ZQ_Activity extends BaseActivity {
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

    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Test5_ZQ_Activity.class);
        context.startActivity(intent);
    }
}
