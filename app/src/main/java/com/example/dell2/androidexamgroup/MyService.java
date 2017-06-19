package com.example.dell2.androidexamgroup;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
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
import java.util.Timer;
import java.util.TimerTask;

public class MyService extends Service {

    protected Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what == 200){
                Log.d("toa","toa");
                Toast.makeText(MyService.this, (String)msg.obj, Toast.LENGTH_SHORT).show();
            }
            else if(msg.what == 1001) {
                Log.d("SEND_FAIL",msg.obj.toString());
                Toast.makeText(MyService.this, "请求发送失败，请重试", Toast.LENGTH_SHORT).show();
            } else if (msg.what == 1002) {
                Log.d("RECEIVE_FAIL",msg.obj.toString());
                Toast.makeText(MyService.this, "请求接受失败，请重试", Toast.LENGTH_SHORT).show();
            }
            else{
                Log.d("t","eeeee");
            }
        }
    };
    private MyBinder myBinder = new MyBinder();
    private Timer timer =null;
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        return myBinder;
    }

    @Override
    public int onStartCommand(Intent intent,int flags, int startId){
        int n =  super.onStartCommand(intent,flags,startId);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                StringBuilder resultBuf = new StringBuilder();
                try {
                    //URL按需要改
                    URL url = new URL("http://10.0.2.2:8080/SerTest1/");
                    Log.d("U",url.toString());
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
                    connection.setRequestMethod("POST");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoOutput(true);
                    connection.setDoInput(true);
                    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                    //这里写发送报文，按照JSON数据格式也可以用JSONObject自己搞，
                    // 下面response是用JSONObject转的
                    out.writeBytes("{\"userID\":\"WYF\"}");
                    out.flush();
                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // 通过连接的输入流获取下发报文，然后就是Java的流处理
                        InputStream in = connection.getInputStream();
                        BufferedReader read = new BufferedReader(new InputStreamReader(in));
                        String line;
                        while ((line = read.readLine()) != null) {
                            resultBuf.append(line);
                        }
                        Log.d("tt", resultBuf.toString());
                        JSONObject res;
                        String code="",value="";
                        try {
                            res = new JSONObject(resultBuf.toString());
                            //getString里写key值，获取什么写什么
                            code = res.getString("returnCode");
                            value = res.getString("returnValue");
                        }
                        catch(JSONException e){
                            e.printStackTrace();
                            Log.d("ex",e.getMessage());
                        }

                        Log.d("co",code);
                        Log.d("vv",value);
                        mHandler.obtainMessage(200,
                                "[ "+ code +" ]" + value).sendToTarget() ;
                    } else {
                        // 异常情况，如404/500...
                        mHandler.obtainMessage(1002,
                                "[" + responseCode + "]" + connection.getResponseMessage()).sendToTarget();
                    }
                } catch (IOException e) {
                    // 网络请求过程中发生IO异常
                    Log.d("IO", e.toString());
                    mHandler.obtainMessage(1001,
                            e.getClass().getName() + " : " + e.getMessage()).sendToTarget();
                }
            }
        },0,6000);
        return n;
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        Log.d("dso","stop");
        if(timer!=null){
            Log.d("so","stop");
            timer.cancel();
        }
    }

    class MyBinder extends Binder{
        public void start(){
            Log.d("test","Start");
        }

    }
}
