package mo.hyit.lesson_new.utils;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mo.hyit.lesson_new.R;

public class ContentResovlerActivity extends AppCompatActivity {

    private Button button_ins;
    private EditText et_sql_uname,et_sql_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_resovler);

        initView();
        setListener();
    }

    private void initView() {
        button_ins = findViewById(R.id.button_ins);
        et_sql_uname = findViewById(R.id.et_sql_uname);
        et_sql_pwd = findViewById(R.id.et_sql_pwd);
    }

    private void setListener() {
        button_ins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentResolver cr = getContentResolver();
                Uri uri = Uri.parse("content://mo.hyit.lesson.entity.student/insert");
                ContentValues cv = new ContentValues();
                cv.put("user",et_sql_uname.getText().toString().trim());
                cv.put("pwd",et_sql_pwd.getText().toString().trim());
                cr.insert(uri,cv);
            }
        });
    }
}
