package mo.hyit.lesson.others;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Broadcast_a  extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String ds = getResultData();
        Log.i("@-@",ds);
        setResultData("666");
        //abortBroadcast();
    }
}
