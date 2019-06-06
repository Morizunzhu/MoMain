package mo.hyit.lesson.activity.jump;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mo.hyit.lesson.R;

public class TwoResultOfSecondActivity extends AppCompatActivity {

    private EditText et_seclag;
    private Button button_twoac_sec_sure,button_twoac_sec_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_result_of_second);

        initView();
        setListener();
    }


    private void setListener() {
        button_twoac_sec_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(et_seclag.getText().toString().trim());
                Intent intent = new Intent();
                intent.setData(uri);
                setResult(RESULT_FIRST_USER,intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        button_twoac_sec_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(4,intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void initView() {
        et_seclag = findViewById(R.id.et_seclag);
        button_twoac_sec_sure = findViewById(R.id.button_twoac_sec_sure);
        button_twoac_sec_cancel = findViewById(R.id.button_twoac_sec_cancel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
