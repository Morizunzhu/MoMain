package mo.hyit.lesson_new;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mo.hyit.lesson_new.utils.SQLHelper;

public class MainActivity extends AppCompatActivity {

    private Button button_insert;
    private EditText et_sql_uname,et_sql_pwd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        setListener();
    }

    private void setListener() {
    }

    private void initView() {
    }
}
