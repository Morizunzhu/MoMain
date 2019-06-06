package mo.hyit.lesson;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import mo.hyit.lesson.activity.broadcast.BroadcastDemoActivity;
import mo.hyit.lesson.activity.BundleDataActivity;
import mo.hyit.lesson.activity.LessonActivity;
import mo.hyit.lesson.activity.layout.LessonLayoutActivity;
import mo.hyit.lesson.activity.layout.LessonUIActivity;
import mo.hyit.lesson.activity.PhoneActivity;
import mo.hyit.lesson.activity.jump.ResultActivity;
import mo.hyit.lesson.activity.SMSMonitorActivity;
import mo.hyit.lesson.activity.sql.SQLActivity;
import mo.hyit.lesson.activity.sql.SQLAdapterActivity;
import mo.hyit.lesson.activity.SaveFileActivity;
import mo.hyit.lesson.activity.SaveSMSActivity;
import mo.hyit.lesson.activity.SaveToMermoryActivity;
import mo.hyit.lesson.activity.TestSocketActivity;
import mo.hyit.lesson.activity.TestWeightActivity;
import mo.hyit.lesson.activity.broadcast.ThreeBroadcastActivity;
import mo.hyit.lesson.activity.jump.TwoResultActivity;
import mo.hyit.lesson.activity.broadcast.UnOrLockActivity;

public class MainActivity extends AppCompatActivity {

    private Button button_lessonUI, button_activity, button_layout,button_callactivity,
            button_browse,button_sms,button_bundledate,button_head,button_two,
            button_weight,button_savefile,button_savetomemory,button_sql_api,button_sql_adpter,
            button_save_sms,button_smsmonitor,button_test_socket,button_broadcast,button_lock,button_three_bdc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();

        Log.i("MainActivity-mo", "生命周期:onCreate()");
    }

    private void initView() {
        button_test_socket = findViewById(R.id.button_test_socket);
        button_lessonUI = findViewById(R.id.button_lessonUI);
        button_layout = findViewById(R.id.button_layout);
        button_activity = findViewById(R.id.button_activity);
        button_callactivity = findViewById(R.id.button_callactivity);
        button_browse = findViewById(R.id.button_browse);
        button_sms = findViewById(R.id.button_sms);
        button_bundledate = findViewById(R.id.button_bundledate);
        button_head = findViewById(R.id.button_head);
        button_two = findViewById(R.id.button_two);
        button_weight = findViewById(R.id.button_weight);
        button_savefile = findViewById(R.id.button_savefile);
        button_savetomemory = findViewById(R.id.button_savetomemory);
        button_sql_api = findViewById(R.id.button_sql_api);
        button_sql_adpter = findViewById(R.id.button_sql_adpter);
        button_save_sms = findViewById(R.id.button_save_sms);
        button_smsmonitor = findViewById(R.id.button_smsmonitor);
        button_broadcast = findViewById(R.id.button_broadcast);
        button_lock = findViewById(R.id.button_lock);
        button_three_bdc = findViewById(R.id.button_three_bdc);
    }

    private void setListener() {
        button_lessonUI.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LessonUIActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LessonLayoutActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_activity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LessonActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_callactivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PhoneActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_browse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW").setData(Uri.parse("http://4399.com"));
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                intent.addCategory("android.intent.category.BROWSABLE");
                intent.setData(Uri.parse("sms:110001"));
                intent.putExtra("sms_body","talk is cheap, show me the code!");
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_bundledate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BundleDataActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ResultActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_two.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TwoResultActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_weight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestWeightActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_savefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SaveFileActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_savetomemory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SaveToMermoryActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_sql_api.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SQLActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_sql_adpter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SQLAdapterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_save_sms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SaveSMSActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_smsmonitor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,SMSMonitorActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_test_socket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,TestSocketActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_broadcast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,BroadcastDemoActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,UnOrLockActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_three_bdc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,ThreeBroadcastActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }
}
