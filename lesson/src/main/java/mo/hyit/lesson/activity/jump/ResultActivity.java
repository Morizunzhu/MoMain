package mo.hyit.lesson.activity.jump;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import mo.hyit.lesson.R;
import mo.hyit.lesson.activity.HeadActivity;

public class ResultActivity extends AppCompatActivity {

    private ImageView imageView_head;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        initView();
        setListener();
    }

    private void setListener() {
        imageView_head.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ResultActivity.this,HeadActivity.class);
                startActivityForResult(intent,1);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        int resultId = -1;
        if(data != null){
            if(requestCode == 1 && resultCode == 2){
                resultId = data.getIntExtra("id",-1);
            }
            switch (resultId){
                case -1:{
                    break;
                }
                case R.id.imageView2:{
                    imageView_head.setImageResource(R.drawable.crossfile);
                    break;
                }
                case R.id.imageView3:{
                    imageView_head.setImageResource(R.drawable.csgo);
                    break;
                }
                case R.id.imageView4:{
                    imageView_head.setImageResource(R.drawable.dota);
                    break;
                }
                case R.id.imageView5:{
                    imageView_head.setImageResource(R.drawable.wow);
                    break;
                }
            }
        }
    }

    private void initView() {
        imageView_head = findViewById(R.id.imageView_head);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
