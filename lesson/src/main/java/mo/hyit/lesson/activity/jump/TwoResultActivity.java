package mo.hyit.lesson.activity.jump;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import mo.hyit.lesson.R;

public class TwoResultActivity extends AppCompatActivity {

    private TextView tv_showresult;
    private Button button_goto_first,button_goto_second;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_result);

        initView();
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 2:{
                switch (resultCode){
                    case RESULT_OK:{
                        Uri uri = data.getData();
                        tv_showresult.setText("First: "+uri.toString());
                        Toast.makeText(TwoResultActivity.this,"you are from first!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case RESULT_CANCELED:{
                        tv_showresult.setText("First: No data!");
                        Toast.makeText(TwoResultActivity.this,"you are from first!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case RESULT_FIRST_USER:{
                        Uri uri = data.getData();
                        tv_showresult.setText("Second "+uri.toString());
                        Toast.makeText(TwoResultActivity.this,"you are from second!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                    case 4:{
                        tv_showresult.setText("Second No data!");
                        Toast.makeText(TwoResultActivity.this,"you are from second!",Toast.LENGTH_SHORT).show();
                        break;
                    }
                }
                break;
            }
        }
    }

    private void setListener() {
        button_goto_first.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoResultActivity.this,TwoResultOfFirstActivity.class);
                startActivityForResult(intent,2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        button_goto_second.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoResultActivity.this,TwoResultOfSecondActivity.class);
                startActivityForResult(intent,2);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    private void initView() {

        tv_showresult = findViewById(R.id.tv_showresult);
        button_goto_first = findViewById(R.id.button_goto_first);
        button_goto_second = findViewById(R.id.button_goto_second);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
