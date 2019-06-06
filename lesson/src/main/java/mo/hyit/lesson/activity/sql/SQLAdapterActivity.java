package mo.hyit.lesson.activity.sql;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mo.hyit.lesson.R;
import mo.hyit.lesson.entity.Student;
import mo.hyit.lesson.others.SQLHelper;
import mo.hyit.lesson.utils.ToastTool;

    public class SQLAdapterActivity extends AppCompatActivity {

        private Button button_a_insert,button_a_query,button_a_update,button_a_delete;
        private EditText et_sqla_uname,et_sqla_pwd;
        private ListView listview_stu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqladapter);

        initView();
        setListener();
    }

    private void setListener() {
        final SQLHelper helper = new SQLHelper(SQLAdapterActivity.this,"sjk.db",null,1);
        button_a_insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        button_a_query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SQLiteDatabase db = helper.getWritableDatabase();
                Cursor cursor = db.rawQuery("select * from Logininfo",null);
                List<Student> list = new ArrayList<>();
                while (cursor.moveToNext()){
                    Student student = new Student();
                    student.setId(cursor.getInt(0));
                    student.setUser(cursor.getString(1));
                    student.setPwd(cursor.getString(2));
                    list.add(student);
                }
                cursor.close();
                db.close();
                List<Map<String,Object>> data = new ArrayList<>();
                for (Student s:list){
                    Map<String,Object> map = new HashMap<>();
                    map.put("id",s.getId());
                    map.put("user",s.getUser());
                    map.put("pwd",s.getPwd());
                    data.add(map);
                }
                String[] from={"id","user","pwd"};
                int to[] = {R.id.tv_id,R.id.tv_uname,R.id.tv_pwd};

                SimpleAdapter sa = new SimpleAdapter(SQLAdapterActivity.this,data,R.layout.list_item,from,to);
                listview_stu.setAdapter(sa);
            }
        });
        button_a_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        button_a_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        listview_stu.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                ToastTool.showToast(SQLAdapterActivity.this,"...",100);
                return false;
            }
        });
    }

    private void initView() {
        button_a_insert = findViewById(R.id.button_a_insert);
        button_a_query = findViewById(R.id.button_a_query);
        button_a_update = findViewById(R.id.button_a_update);
        button_a_delete = findViewById(R.id.button_a_delete);
        et_sqla_uname = findViewById(R.id.et_sqla_uname);
        et_sqla_pwd = findViewById(R.id.et_sqla_pwd);
        listview_stu = findViewById(R.id.listview_stu);
    }
}
