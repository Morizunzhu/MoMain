package mo.hyit.lesson.others;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Broadcast_b extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String ds = getResultData();
        Log.i("0-0",ds);
        setResultData("777");
    }
}
