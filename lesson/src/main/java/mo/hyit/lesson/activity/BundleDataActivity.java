package mo.hyit.lesson.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mo.hyit.lesson.R;

public class BundleDataActivity extends AppCompatActivity {


    private EditText et_username,et_pwd;
    private Button bd_button,button_intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bundle_data);

        initView();
        setListener();
    }

    private void initView(){
        bd_button = findViewById(R.id.bd_button);
        button_intent = findViewById(R.id.button_intent);
        et_username = findViewById(R.id.et_username);
        et_pwd = findViewById(R.id.et_pwd);
    }

    private void setListener(){
        bd_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BundleDataActivity.this, ReceiveDataActivity.class);
                Bundle bundle = new Bundle();
                bundle.putCharSequence("username",et_username.getText().toString().trim());
                bundle.putCharSequence("passwd",et_pwd.getText().toString().trim());
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        button_intent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(BundleDataActivity.this, ReceiveDataActivity.class);
                intent.putExtra("passwd",et_pwd.getText().toString().trim());
                intent.putExtra("username",et_username.getText().toString().trim());
                intent.putExtra("method",true);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
