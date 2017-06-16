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

public class Test6_ZQ_Activity extends BaseActivity {
    private ListView test6_ZQ_ListView;
    private TextView test6_ZQ_textView;
    private DrawerLayout test6_ZQ_DrawerLayout;
    private List<String> data=new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_test6_zq);
        init();
    }
    public void init(){
        data.add("fragment1");
        data.add("fragment2");
        test6_ZQ_ListView=(ListView)findViewById(R.id.test6_ZQ_ListView);
        test6_ZQ_textView=(TextView)findViewById(R.id.test6_ZQ_textView);
        test6_ZQ_DrawerLayout=(DrawerLayout)findViewById(R.id.test6_ZQ_DrawerLayout);
        test6_ZQ_textView.setText("fragment1");
        Test6_ZQ_Adapt test6_zq_adapt=new Test6_ZQ_Adapt(Test6_ZQ_Activity.this,R.layout.listview_test6_zq,data);
        test6_ZQ_ListView.setAdapter(test6_zq_adapt);
        test6_ZQ_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String str=data.get(i);
                test6_ZQ_textView.setText(str);
                Toast.makeText(Test6_ZQ_Activity.this,str,Toast.LENGTH_SHORT).show();
                test6_ZQ_DrawerLayout.closeDrawers();
            }
        });
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Test6_ZQ_Activity.class);
        context.startActivity(intent);
    }
}
