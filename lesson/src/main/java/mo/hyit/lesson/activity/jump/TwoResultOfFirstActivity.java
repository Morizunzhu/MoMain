package mo.hyit.lesson.activity.jump;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import mo.hyit.lesson.R;

public class TwoResultOfFirstActivity extends AppCompatActivity {

    private EditText et_firslag;
    private Button button_twoac_fir_sure,button_twoac_fir_cancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_result_of_first);

        initView();
        setListener();
    }

    private void setListener() {
        button_twoac_fir_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(et_firslag.getText().toString().trim());
                Intent intent = new Intent();
                intent.setData(uri);
                setResult(RESULT_OK,intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });

        button_twoac_fir_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                setResult(RESULT_CANCELED,intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    private void initView() {
        et_firslag = findViewById(R.id.et_firslag);
        button_twoac_fir_sure = findViewById(R.id.button_twoac_fir_sure);
        button_twoac_fir_cancel = findViewById(R.id.button_twoac_fir_cancel);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
