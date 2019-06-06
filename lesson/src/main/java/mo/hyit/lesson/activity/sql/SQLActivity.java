package mo.hyit.lesson.activity.sql;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import mo.hyit.lesson.R;
import mo.hyit.lesson.others.SQLHelper;
import mo.hyit.lesson.utils.ToastTool;

public class SQLActivity extends AppCompatActivity {

    private Button button_insert,button_query,button_update,button_delete;
    private EditText et_sql_uname,et_sql_pwd;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sql);

        initView();
        setListener();
    }

    private void setListener() {
        final SQLHelper helper = new SQLHelper(SQLActivity.this,"sjk.db",null,1);
        button_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_sql_uname.getText().toString().trim()) || TextUtils.isEmpty(et_sql_pwd.getText().toString().trim())){
                    ToastTool.showToast(SQLActivity.this,"Can be empty!",300);
                }else{
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("user",et_sql_uname.getText().toString().trim());
                    values.put("pwd",et_sql_pwd.getText().toString().trim());
                    db.insert("Logininfo",null,values);
                    db.close();
                }
            }
        });
        button_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.query("Logininfo",null,null,null,null,null,null);
                if(cursor.getCount() == 0){
                    ToastTool.showToast(SQLActivity.this,"No data!",300);
                }else{
                    cursor.moveToFirst();
                    tv_show.setText("user:"+cursor.getString(1)+"  pwd:"+cursor.getString(2));
                    while(cursor.moveToNext()){
                        tv_show.append("\nuser:"+cursor.getString(1)+"  pwd:"+cursor.getString(2));
                    }
                }
                cursor.close();
                db.close();
            }
        });
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_sql_uname.getText().toString().trim())){
                    ToastTool.showToast(SQLActivity.this,"Can be empty!",300);
                }else{
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    values.put("pwd",et_sql_pwd.getText().toString().trim());
                    db.update("Logininfo",values,"user=?",new String[]{et_sql_uname.getText().toString().trim()});
                    db.close();
                }
            }
        });
        button_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(et_sql_uname.getText().toString().trim())){
                    ToastTool.showToast(SQLActivity.this,"Can be empty!",300);
                }else{
                    SQLiteDatabase db = helper.getWritableDatabase();
                    ContentValues values = new ContentValues();
                    db.delete("Logininfo","user=?",new String[]{et_sql_uname.getText().toString().trim()});
                    db.close();
                }
            }
        });
    }

    private void initView() {
        button_insert = findViewById(R.id.button_insert);
        button_query = findViewById(R.id.button_query);
        et_sql_uname = findViewById(R.id.et_sql_uname);
        et_sql_pwd = findViewById(R.id.et_sql_pwd);
        button_update = findViewById(R.id.button_update);
        button_delete = findViewById(R.id.button_delete);
        tv_show = findViewById(R.id.tv_show);
    }
}
