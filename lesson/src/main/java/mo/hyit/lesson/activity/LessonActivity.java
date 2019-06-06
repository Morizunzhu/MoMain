package mo.hyit.lesson.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import mo.hyit.lesson.R;
import mo.hyit.lesson.activity.layout.NoLayoutActivity;

public class LessonActivity extends AppCompatActivity {

    private Button button_create_wo_layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson);

        initView();
        setListener();
    }

    private void initView(){
        button_create_wo_layout = findViewById(R.id.button_create_wo_layout);
    }

    private void setListener(){
        button_create_wo_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LessonActivity.this, NoLayoutActivity.class);
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
