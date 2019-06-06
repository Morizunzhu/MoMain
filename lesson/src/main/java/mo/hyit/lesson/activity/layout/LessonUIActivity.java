package mo.hyit.lesson.activity.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import mo.hyit.lesson.R;

public class LessonUIActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_ui);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
