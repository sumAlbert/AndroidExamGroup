package com.example.dell2.androidexamgroup;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import adapt.Test6_ZQ_Adapt;
import collector.BaseActivity;
import db.DBHandler_ZQ;
import db.DbHelp_ZQ;
import hash.HashName;

/**
 * Created by dell2 on 2017/6/17.
 */

public class Test7_ZQ_Activity extends BaseActivity implements View.OnClickListener{
    private DbHelp_ZQ dbHelp_zq;
    private DBHandler_ZQ dbHandler_zq;
    private LinearLayout test7_ZQ_LL_1;
    private LinearLayout test7_ZQ_LL_2;
    private LinearLayout test7_ZQ_LL_3;
    private LinearLayout test7_ZQ_LL_4;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getSupportActionBar() != null){
            getSupportActionBar().hide();
        }
        setContentView(R.layout.activity_test7_zq);
        init();
    }
    public void init(){
        test7_ZQ_LL_1=(LinearLayout)findViewById(R.id.test7_ZQ_LL_1);
        test7_ZQ_LL_2=(LinearLayout)findViewById(R.id.test7_ZQ_LL_2);
        test7_ZQ_LL_3=(LinearLayout)findViewById(R.id.test7_ZQ_LL_3);
        test7_ZQ_LL_4=(LinearLayout)findViewById(R.id.test7_ZQ_LL_4);
        test7_ZQ_LL_1.setOnClickListener(this);
        test7_ZQ_LL_2.setOnClickListener(this);
        test7_ZQ_LL_3.setOnClickListener(this);
        test7_ZQ_LL_4.setOnClickListener(this);
        dbHelp_zq=new DbHelp_ZQ(this,"Name.db",null,1);
        dbHandler_zq=new DBHandler_ZQ(new WeakReference<BaseActivity>(this));
    }
    public static void actionStart(Context context){
        Intent intent=new Intent(context,Test7_ZQ_Activity.class);
        context.startActivity(intent);
    }
    @Override
    public void onClick(View view) {
        SQLiteDatabase sqLiteDatabase;
        switch (view.getId()){
            case R.id.test7_ZQ_LL_1:
                dbHelp_zq.getWritableDatabase();
                break;
            case R.id.test7_ZQ_LL_2:
                dbHandler_zq.sendEmptyMessageDelayed(DBHandler_ZQ.DOING,1000);
                break;
            case R.id.test7_ZQ_LL_3:
                dbHandler_zq.sendEmptyMessage(DBHandler_ZQ.OVER);
                Toast.makeText(Test7_ZQ_Activity.this,"over",Toast.LENGTH_SHORT).show();
                break;
            case R.id.test7_ZQ_LL_4:
                displayDb();
                break;
            default:
                break;
        }
    }
    public void insertDb(){
        SQLiteDatabase sqLiteDatabase=dbHelp_zq.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put("name", HashName.getHashRandom(8));
        sqLiteDatabase.insert("name",null,contentValues);
        Toast.makeText(Test7_ZQ_Activity.this,"insert success",Toast.LENGTH_SHORT).show();
    }
    public void displayDb(){
        SQLiteDatabase sqLiteDatabase=dbHelp_zq.getWritableDatabase();
        Cursor cursor=sqLiteDatabase.query("name",null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                String name=cursor.getString(cursor.getColumnIndex("name"));
                Log.d("Test7_ZQ","Name"+name);
            }while(cursor.moveToNext());
        }
        cursor.close();
    }
    public DBHandler_ZQ getHandler(){
        return this.dbHandler_zq;
    }
}
