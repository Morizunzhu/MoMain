package mo.hyit.lesson.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import mo.hyit.lesson.R;

public class ReceiveDataActivity extends AppCompatActivity {

    private TextView tv_rc_un,tv_rc_pwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receive_data);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        initView();
        setData(bundle,intent);
    }

    private void initView(){
        tv_rc_un = findViewById(R.id.tv_rc_un);
        tv_rc_pwd = findViewById(R.id.tv_rc_pwd);
    }


    private void setData(Bundle bundle,Intent intent){
        if (intent.getBooleanExtra("method", false)) {
            tv_rc_un.setText("intent:"+intent.getStringExtra("username"));
            tv_rc_pwd.setText("intent:"+intent.getStringExtra("passwd"));
        }else{
            tv_rc_un.setText("bundle:"+bundle.getCharSequence("username"));
            tv_rc_pwd.setText("bundle:"+bundle.getCharSequence("passwd"));
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
