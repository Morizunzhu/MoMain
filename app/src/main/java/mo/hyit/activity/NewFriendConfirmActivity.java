package mo.hyit.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import mo.hyit.dao.UserService;
import mo.hyit.entity.User;
import mo.hyit.momain.R;
import mo.hyit.view.FriendingListAdapter;

public class NewFriendConfirmActivity extends AppCompatActivity {

    private User user;
    private UserService userService;
    private ListView lv_friendinglist;
    private List<Integer> userList;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_friend_confirm);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                FriendingListAdapter adp = new FriendingListAdapter(userList,user.getChat_number(),NewFriendConfirmActivity.this);
                lv_friendinglist.setAdapter(adp);

            }
        };

        user = (User) getIntent().getSerializableExtra("user");
        initView();
        initData();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void setListener() {
    }

    private void initView() {
        lv_friendinglist = findViewById(R.id.lv_friendinglist);
    }

    private void initData(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(userService == null){
                    userService = new UserService(NewFriendConfirmActivity.this);
                }
                userList = userService.ListAskforfriend(user.getChat_number());
                handler.sendEmptyMessage(0x01);
            }
        }).start();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
