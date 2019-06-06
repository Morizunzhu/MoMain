package mo.hyit.lesson.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import mo.hyit.lesson.R;

public class TestWeightActivity extends AppCompatActivity {

    private Button button_test_submit;
    private Spinner spinner_weight_sex;
    private EditText et_weight,editText_height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_weight);

        initView();
        setListener();
    }

    private void initView() {
        button_test_submit = findViewById(R.id.button_test_submit);
        spinner_weight_sex = findViewById(R.id.spinner_weight_sex);
        editText_height = findViewById(R.id.editText_height);
        et_weight = findViewById(R.id.et_weight);
    }

    private void setListener() {
        button_test_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TestWeightActivity.this,WeightResultActivity.class);
                intent.putExtra("sexKind",(String) spinner_weight_sex.getSelectedItem());
                intent.putExtra("weight",et_weight.getText().toString().trim());
                intent.putExtra("height",editText_height.getText().toString().trim());
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
