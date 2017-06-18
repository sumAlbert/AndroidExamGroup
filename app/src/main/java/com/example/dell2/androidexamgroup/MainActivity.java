package com.example.dell2.androidexamgroup;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import collector.BaseActivity;

public class MainActivity extends BaseActivity implements View.OnClickListener{
    private LinearLayout test1;
    private LinearLayout test2;
    private LinearLayout test3;
    private LinearLayout test4;
    private LinearLayout test5;
    private LinearLayout test6;
    private LinearLayout test7;
    private LinearLayout test8;
    private LinearLayout test3_ZQ;
    private LinearLayout test5_ZQ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_main);
        init();
    }
    public void init(){
        test1=(LinearLayout)findViewById(R.id.test1);
        test2=(LinearLayout)findViewById(R.id.test2);
        test3=(LinearLayout)findViewById(R.id.test3);
        test4=(LinearLayout)findViewById(R.id.test4);
        test5=(LinearLayout)findViewById(R.id.test5);
        test6=(LinearLayout)findViewById(R.id.test6);
        test7=(LinearLayout)findViewById(R.id.test7);
        test8=(LinearLayout)findViewById(R.id.test8);
        test3_ZQ=(LinearLayout)findViewById(R.id.test3_ZQ);
        test5_ZQ=(LinearLayout)findViewById(R.id.test5_ZQ);
        test1.setOnClickListener(this);
        test2.setOnClickListener(this);
        test3.setOnClickListener(this);
        test4.setOnClickListener(this);
        test5.setOnClickListener(this);
        test6.setOnClickListener(this);
        test7.setOnClickListener(this);
        test8.setOnClickListener(this);
        test3_ZQ.setOnClickListener(this);
        test5_ZQ.setOnClickListener(this);//
    }

    public void onClick(View view){
        switch (view.getId()){
            case R.id.test1:
                Toast.makeText(MainActivity.this,"test1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.test2:
                Toast.makeText(MainActivity.this,"test2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.test3:
                Toast.makeText(MainActivity.this,"test3",Toast.LENGTH_SHORT).show();
                break;
            case R.id.test4:
//                Toast.makeText(MainActivity.this,"test4",Toast.LENGTH_SHORT).show();
                Test4_Hlp_Activity.actionStart(MainActivity.this);
                break;
            case R.id.test5:
                Toast.makeText(MainActivity.this,"test5",Toast.LENGTH_SHORT).show();
                break;
            case R.id.test6:
                Test6_ZQ_Activity.actionStart(MainActivity.this);
                break;
            case R.id.test7:
                Test7_ZQ_Activity.actionStart(MainActivity.this);
                break;
            case R.id.test8:
                Test8_ZQ_Activity.actionStart(MainActivity.this);
                break;
            case R.id.test3_ZQ:
                Test3_ZQ_Activity.actionStart(MainActivity.this);
                break;
            case R.id.test5_ZQ:
                Test5_ZQ_Activity.actionStart(MainActivity.this);
                break;
            default:
                break;
        }
    }
}
