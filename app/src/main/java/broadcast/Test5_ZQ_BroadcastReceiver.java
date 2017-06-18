package broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * Created by dell2 on 2017/6/18.
 */

public class Test5_ZQ_BroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context,"received in Test5_ZQ_BroadcastReceiver",Toast.LENGTH_SHORT).show();
    }
}
