package mo.hyit.lesson.activity.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import mo.hyit.lesson.R;

public class NoLayoutActivity extends AppCompatActivity {

    private TextView tv;
    private LinearLayout ly;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makeView();

        setContentView(ly);
    }

    private void makeView(){
        ly = new LinearLayout(this);
        ly.setPadding(20,20,20,20);
        ly.setGravity(Gravity.CENTER_HORIZONTAL);
        tv = new TextView(this);
        tv.setText("New Activity without Layout.xml");
        tv.setTextSize(18);
        ly.addView(tv);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
