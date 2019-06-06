package mo.hyit.lesson.activity;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mo.hyit.lesson.R;

public class SaveSMSActivity extends AppCompatActivity {

    private Button bt_sms_read, bt_sms_resave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_sms);

        initView();
        setListener();
    }

    private void setListener() {
        bt_sms_read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLHelper sql = new MySQLHelper(SaveSMSActivity.this, "mysmsinfo.db", null, 1);
                SQLiteDatabase db = sql.getWritableDatabase();
                ContentResolver cr = getContentResolver();
                Uri uri = Uri.parse("content://sms");
                Cursor cursor = cr.query(uri, null, null, null, null);
                while (cursor.moveToNext()) {
                    String addr = cursor.getString(cursor.getColumnIndex("address"));
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    ContentValues cv = new ContentValues();
                    cv.put("type", type);
                    cv.put("address", addr);
                    cv.put("date", date);
                    cv.put("body", body);
                    db.insert("smsinfo", null, cv);
                }
                cursor.close();
                db.close();
            }
        });
        bt_sms_resave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MySQLHelper sql = new MySQLHelper(SaveSMSActivity.this, "mysmsinfo.db", null, 1);
                SQLiteDatabase db = sql.getWritableDatabase();
                ContentResolver cr = getContentResolver();
                Uri uri = Uri.parse("content://sms");
                Cursor cursor = db.query("smsinfo", null, null, null, null, null, null);
                while (cursor.moveToNext()) {
                    String addr = cursor.getString(cursor.getColumnIndex("address"));
                    String type = cursor.getString(cursor.getColumnIndex("type"));
                    String date = cursor.getString(cursor.getColumnIndex("date"));
                    String body = cursor.getString(cursor.getColumnIndex("body"));
                    ContentValues cv = new ContentValues();
                    cv.put("type", type);
                    cv.put("address", addr);
                    cv.put("date", date);
                    cv.put("body", body);
                    cr.insert(uri, cv);
                }
                cursor.close();
                db.close();
            }
        });
    }

    private void initView() {
        bt_sms_read = findViewById(R.id.bt_sms_read);
        bt_sms_resave = findViewById(R.id.bt_sms_resave);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}

class MySQLHelper extends SQLiteOpenHelper {
    public MySQLHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table smsinfo(_id integer primary key autoincrement,address varchar(20) ,date varchar(20),type varchar(20),body varchar(20))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
