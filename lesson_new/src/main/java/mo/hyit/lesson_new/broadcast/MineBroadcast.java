package mo.hyit.lesson_new.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import mo.hyit.lesson_new.MainActivity;

public class MineBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        Intent in = new Intent(context,MainActivity.class);
        in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(in);
    }
}
