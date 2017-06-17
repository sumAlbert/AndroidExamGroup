package com.example.dell2.androidexamgroup;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import collector.BaseActivity;
import db.DbHelp_ZQ;

/**
 * Created by dell2 on 2017/6/17.
 */

public class Test8_ZQ_Activity extends BaseActivity {
    private DbHelp_ZQ dbHelp_zq;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_test8_zq);
        init();
    }
    public void init(){
        dbHelp_zq=new DbHelp_ZQ(this,"Name.db",null,1);
        displayDb();
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Test8_ZQ_Activity.class);
        context.startActivity(intent);
    }
    public void displayDb(){
        SQLiteDatabase sqLiteDatabase=dbHelp_zq.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("name",null,null,null,null,null,null);
        int i=0;
        if(cursor.moveToFirst()){
            do{
                i++;
                String name=cursor.getString(cursor.getColumnIndex("name"));
                Log.d("Test8_ZQ","Name"+name);
            }while(cursor.moveToNext());
        }
        cursor.close();
        Toast.makeText(Test8_ZQ_Activity.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
    }
}
