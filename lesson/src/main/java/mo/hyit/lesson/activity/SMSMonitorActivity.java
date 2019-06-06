package mo.hyit.lesson.activity;

import android.content.ContentResolver;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import mo.hyit.lesson.R;

public class SMSMonitorActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_smsmonitor);

        init();
    }

    private void init() {
        ContentResolver cv = getContentResolver();
        Uri uri = Uri.parse("content://sms");
        cv.registerContentObserver(uri,true,new MyOberserver(new Handler()));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
    class MyOberserver extends ContentObserver {

        /**
         * Creates a content observer.
         *
         * @param handler The handler to run {@link #onChange} on, or null if none.
         */
        public MyOberserver(Handler handler) {
            super(handler);
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            ContentResolver cv = getContentResolver();
            Uri uri = Uri.parse("content://sms");
            Cursor cursor = cv.query(uri,new String[]{"address","date","type","body"},null,null,null);
            cursor.moveToNext();
            String addr = cursor.getString(cursor.getColumnIndex("address"));
            String type = cursor.getString(cursor.getColumnIndex("type"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String body = cursor.getString(cursor.getColumnIndex("body"));
            TextView tv = findViewById(R.id.textView_sho);
            tv.setText("addr:"+addr+"\n");
            tv.append("type:"+type+"\n");
            tv.append("date:"+date+"\n");
            tv.append("body:"+body+"\n");
        }
    }
}

