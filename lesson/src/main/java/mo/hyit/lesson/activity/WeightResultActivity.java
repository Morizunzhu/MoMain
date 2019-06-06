package mo.hyit.lesson.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import mo.hyit.lesson.R;

public class WeightResultActivity extends AppCompatActivity {

    private TextView tv_show_weight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_weight_result);

        initView();
    }

    @Override
    protected void onStart() {
        super.onStart();
        getResult(getIntent());
    }

    private void getResult(Intent intent) {
        float bim = checkBMI(Float.valueOf(intent.getStringExtra("weight")), Float.valueOf(intent.getStringExtra("height")));
        tv_show_weight.setText("");
        tv_show_weight.append("您的BMI指数为:" + bim + "\n");
        StringBuffer sbf = new StringBuffer();
        if (bim < 18.5) {
            sbf.append("偏瘦\n");
        } else if (bim < 23.9) {
            sbf.append("正常\n");
        } else if (bim < 27.9) {
            sbf.append("偏胖\n");
        } else {
            sbf.append("肥胖\n");
        }
        if (intent.getStringExtra("sexKind").equals("男")) {
            tv_show_weight.append("先生您好,您" + sbf);
        } else {
            tv_show_weight.append("女士您好,您" + sbf);
        }
    }

    private float checkBMI(float weight, float height) {
        return weight / height / height * 10000;
    }

    private void initView() {
        tv_show_weight = findViewById(R.id.tv_show_weight);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
