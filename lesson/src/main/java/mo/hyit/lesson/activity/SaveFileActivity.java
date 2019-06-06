package mo.hyit.lesson.activity;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import mo.hyit.lesson.R;

public class SaveFileActivity extends AppCompatActivity {

    private Button button_uploadinfo,button_findinfo;
    private EditText editText_name,editText_passwd;
    private TextView textView_fileinfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_file);

        initView();
        setListener();
    }

    private void setListener() {
        button_uploadinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
            }
        });
        button_findinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_fileinfo.setText(readFromFile());
            }
        });
    }

    private void saveToFile(){
        try {
            FileOutputStream fos = openFileOutput("test.txt", Context.MODE_PRIVATE);
            fos.write(editText_name.getText().toString().trim().getBytes());
            fos.write("\n".getBytes());
            fos.write(editText_passwd.getText().toString().trim().getBytes());
            Toast.makeText(SaveFileActivity.this,"save success!",Toast.LENGTH_SHORT).show();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String readFromFile(){
        try {
            FileInputStream fis = openFileInput("test.txt");
            byte [] buf = new byte[fis.available()];
            fis.read(buf);
            fis.close();
            return new String(buf);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void initView() {
        button_uploadinfo = findViewById(R.id.button_uploadinfo);
        button_findinfo = findViewById(R.id.button_findinfo);
        editText_name = findViewById(R.id.editText_name);
        editText_passwd = findViewById(R.id.editText_passwd);
        textView_fileinfo = findViewById(R.id.textView_fileinfo);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
