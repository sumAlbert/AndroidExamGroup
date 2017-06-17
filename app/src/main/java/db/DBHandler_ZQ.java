package db;

import android.os.Handler;
import android.os.Message;

import com.example.dell2.androidexamgroup.Test7_ZQ_Activity;

import java.lang.ref.WeakReference;

import collector.BaseActivity;

/**
 * Created by dell2 on 2017/6/17.
 */

public class DBHandler_ZQ extends Handler {
    public static final int START=1;
    public static final int DOING=2;
    public static final int OVER=3;
    private WeakReference<BaseActivity> wr;
    public DBHandler_ZQ(WeakReference<BaseActivity> wr){
        this.wr=wr;
    }
    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        Test7_ZQ_Activity activity=(Test7_ZQ_Activity)wr.get();
        if(activity==null)
            return;
        if(activity.getHandler().hasMessages(DOING))
            activity.getHandler().removeMessages(DOING);
        switch (msg.what){
            case START:
                activity.insertDb();
                activity.getHandler().sendEmptyMessageDelayed(DOING,5000);
                break;
            case DOING:
                activity.insertDb();
                activity.getHandler().sendEmptyMessageDelayed(DOING,5000);
                break;
            case OVER:
                break;
            default:
                break;
        }
    }
}
