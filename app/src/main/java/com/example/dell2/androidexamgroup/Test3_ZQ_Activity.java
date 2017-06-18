package com.example.dell2.androidexamgroup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v4.content.ContextCompat;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.jar.Manifest;

import adapt.Test3_ZQ_Adapt;
import collector.BaseActivity;
import entity.Touch;

/**
 * Created by dell2 on 2017/6/18.
 */

public class Test3_ZQ_Activity extends BaseActivity {
    private ListView test3_ZQ_ListView;
    private List<Touch> touchList=new ArrayList<>();
    private Test3_ZQ_Adapt test3_zq_adapt;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_test3_zq);
        init();
    }
    public void init(){
        getTouchs();
        ListView test3_ZQ_ListView=(ListView)findViewById(R.id.test3_ZQ_ListView);
        test3_zq_adapt=new Test3_ZQ_Adapt(Test3_ZQ_Activity.this,R.layout.listview_test3_zq,touchList);
        test3_ZQ_ListView.setAdapter(test3_zq_adapt);
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Test3_ZQ_Activity.class);
        context.startActivity(intent);
    }
    public void getTouchs(){
        Cursor cursor=null;
        try{
            cursor=getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null);
            while(cursor.moveToNext()){
                String displayName=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String displayTel=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                Touch touch=new Touch();
                touch.setName(displayName);
                touch.setTel(displayTel);
                touchList.add(touch);
            }
        }catch (Exception exception){
            exception.printStackTrace();
        }finally {
            if(cursor!=null)
                cursor.close();
        }
    }
}
