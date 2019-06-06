package mo.hyit.lesson.activity.broadcast;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mo.hyit.lesson.R;
import mo.hyit.lesson.others.Broadcast_b;

public class ThreeBroadcastActivity extends AppCompatActivity {

    private Button btn_three;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_three_broadcast);

        initView();
        setListener();
    }

    private void initView() {
        btn_three = findViewById(R.id.btn_three);
    }

    private void setListener() {
        btn_three.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction("mains_a");
                sendOrderedBroadcast(intent,null,null,null,0,"max cost 500",null);
                //sendOrderedBroadcast(intent,null,new Broadcast_b(),null,0,"max cost 500",null);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
