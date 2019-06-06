package mo.hyit.lesson.activity.layout;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import mo.hyit.lesson.R;

public class LessonLayoutActivity extends AppCompatActivity {

    private Button button_Linear,button_linear_x,button_linear_y,button_linear_xy,button_layout_table;
    private View include_linear,include_linear_x,include_linear_y,include_linear_xy,include_layout_table;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_layout);

        initView();
        setListener();
    }

    private void initView(){
        button_Linear = findViewById(R.id.button_Linear);
        button_linear_x = findViewById(R.id.button_linear_x);
        button_linear_y = findViewById(R.id.button_linear_y);
        button_linear_xy = findViewById(R.id.button_linear_xy);
        button_layout_table = findViewById(R.id.button_layout_table);


        include_linear = findViewById(R.id.include_linear);
        include_linear_x = findViewById(R.id.include_linear_x);
        include_linear_y = findViewById(R.id.include_linear_y);
        include_linear_xy = findViewById(R.id.include_linear_xy);
        include_layout_table = findViewById(R.id.include_layout_table);
    }

    private void setListener(){
        button_Linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInclude(1);
            }
        });

        button_linear_x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInclude(2);
            }
        });

        button_linear_y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInclude(3);
            }
        });

        button_linear_xy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInclude(4);
            }
        });

        button_layout_table.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectInclude(5);
            }
        });
    }

    private void selectInclude(int x){
        List<View> list = new ArrayList<>();
        list.add(include_linear);
        list.add(include_linear_x);
        list.add(include_linear_y);
        list.add(include_linear_xy);
        list.add(include_layout_table);
        int i = 1;
        for(View v: list){
            if(i == x){
                v.setVisibility(View.VISIBLE);
            }else{
                v.setVisibility(View.GONE);
            }
            ++i;
        }
    }
}
