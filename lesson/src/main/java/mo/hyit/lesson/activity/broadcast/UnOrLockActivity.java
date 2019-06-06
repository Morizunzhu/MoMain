package mo.hyit.lesson.activity.broadcast;

import android.content.IntentFilter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mo.hyit.lesson.R;
import mo.hyit.lesson.others.ScreenOffBroadcast;
import mo.hyit.lesson.others.ScreenOnBroadcast;

public class UnOrLockActivity extends AppCompatActivity {

    private ScreenOffBroadcast soff;
    private ScreenOnBroadcast son;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_un_or_lock);
    }

    @Override
    protected void onResume() {
        son = new ScreenOnBroadcast();
        soff = new ScreenOffBroadcast();

        registerReceiver(soff,new IntentFilter("android.intent.action.SCREEN_OFF"));
        registerReceiver(son,new IntentFilter("android.intent.action.SCREEN_ON"));
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(soff);
        unregisterReceiver(son);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
