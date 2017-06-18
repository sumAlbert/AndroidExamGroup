package com.example.dell2.androidexamgroup;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class Test1_WHQ_Activity2 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test1__whq_2);
        Intent intent=getIntent();
        String name=intent.getStringExtra("name");
        String sex=intent.getStringExtra("sex");
        String hobby=intent.getStringExtra("hobby");
        String blood=intent.getStringExtra("blood");
        Log.d("SecondActivity","name:"+name);//log
        Log.d("SecondActivity","sex:"+sex);
        Log.d("SecondActivity","hobby:"+hobby);
        Log.d("SecondActivity","blood:"+blood);
    }
}
