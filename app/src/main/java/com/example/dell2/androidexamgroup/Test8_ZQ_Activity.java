package com.example.dell2.androidexamgroup;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import adapt.Test8_ZQ_Adapt;
import collector.BaseActivity;
import db.DbHelp_ZQ;

/**
 * Created by dell2 on 2017/6/17.
 */

public class Test8_ZQ_Activity extends BaseActivity {
    private DbHelp_ZQ dbHelp_zq;
    private ListView test8_ZQ_ListView;
    private Test8_ZQ_Adapt test8_zq_adapt;
    private List<String> data=new ArrayList<>();
    private int pos=0;
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
        test8_ZQ_ListView=(ListView)findViewById(R.id.test8_ZQ_ListView);
        test8_zq_adapt=new Test8_ZQ_Adapt(Test8_ZQ_Activity.this,R.layout.listview_test8_zq,data);
        test8_ZQ_ListView.setAdapter(test8_zq_adapt);
        test8_ZQ_ListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                AlertDialog.Builder dialog=new AlertDialog.Builder(Test8_ZQ_Activity.this);
                pos=i;
                dialog.setTitle("Delete this item?");
                dialog.setMessage(data.get(i));
                dialog.setCancelable(false);
                dialog.setPositiveButton("OK",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteItem(data.get(pos));
                        data.clear();
                        displayDb();
                        test8_zq_adapt=new Test8_ZQ_Adapt(Test8_ZQ_Activity.this,R.layout.listview_test8_zq,data);
                        test8_ZQ_ListView.setAdapter(test8_zq_adapt);
                    }
                });
                dialog.setNegativeButton("Cancel",new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Toast.makeText(Test8_ZQ_Activity.this,"delete cancel",Toast.LENGTH_SHORT).show();
                    }
                });
                dialog.show();
            }
        });

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
                data.add(name);
            }while(cursor.moveToNext());
        }
        cursor.close();
        Toast.makeText(Test8_ZQ_Activity.this,String.valueOf(i),Toast.LENGTH_SHORT).show();
    }
    public void deleteItem(String str){
        SQLiteDatabase sqLiteDatabase=dbHelp_zq.getWritableDatabase();
        sqLiteDatabase.delete("Name"," name = ?",new String[] {str});
        Toast.makeText(Test8_ZQ_Activity.this,"delete success",Toast.LENGTH_SHORT).show();
    }
}
