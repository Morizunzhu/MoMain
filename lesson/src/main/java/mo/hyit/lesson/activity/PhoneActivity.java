package mo.hyit.lesson.activity;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import mo.hyit.lesson.R;

public class PhoneActivity extends AppCompatActivity {

    private EditText editText_phone;
    private TextView textView_num;
    private Button button_000,button_001,button_002,button_003,button_004,button_005,button_006,button_007,button_008,button_009,button_clear;
    private ImageView button_call;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone);

        initView();
        setListener();
    }

    private void initView(){
        editText_phone = findViewById(R.id.editText_phone);
        button_call = findViewById(R.id.button_call);
        textView_num = findViewById(R.id.textView_num);

        button_clear = findViewById(R.id.button_clear);

        button_000 = findViewById(R.id.button_000);
        button_001 = findViewById(R.id.button_001);
        button_002 = findViewById(R.id.button_002);
        button_003 = findViewById(R.id.button_003);
        button_004 = findViewById(R.id.button_004);
        button_005 = findViewById(R.id.button_005);
        button_006 = findViewById(R.id.button_006);
        button_007 = findViewById(R.id.button_007);
        button_008 = findViewById(R.id.button_008);
        button_009 = findViewById(R.id.button_009);
    }

    private void setListener(){
        button_call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_CALL);
                Uri uri = Uri.parse("tel://"+textView_num.getText());
                intent.setData(uri);
                startActivity(intent);
            }
        });
        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.setText("");
            }
        });

        button_000.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("0");
            }
        });
        button_001.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("1");
            }
        });
        button_002.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("2");
            }
        });
        button_003.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("3");
            }
        });
        button_004.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("4");
            }
        });
        button_005.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("5");
            }
        });
        button_006.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("6");
            }
        });
        button_007.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("7");
            }
        });
        button_008.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("8");
            }
        });
        button_009.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView_num.append("9");
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
