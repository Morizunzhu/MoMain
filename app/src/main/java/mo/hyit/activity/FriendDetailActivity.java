package mo.hyit.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mo.hyit.dao.UserService;
import mo.hyit.entity.User;
import mo.hyit.momain.R;
import mo.hyit.utils.StaticValue;

import static mo.hyit.utils.StaticValue.UPDATE_UI;

public class FriendDetailActivity extends AppCompatActivity {

    private User user, friend;
    private boolean isFriendflag;
    private Button btn_makechat, btn_add_new_friend;
    private TextView tv_showname, tv_showip, tv_show_port;
    private Handler handler;
    private UserService userService;
    private String friendId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_detail);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {

                switch (msg.what) {
                    case UPDATE_UI: {
                        User friend_t = (User) msg.obj;
                        tv_showname.setText(friend_t.getName());
                        tv_showip.setText(friend_t.getLatest_ip());
                        break;
                    }
                }
            }
        };
        user = (User) getIntent().getSerializableExtra("user");
        userService = new UserService(this);
        initView();
        setListener();
    }

    private void initView() {
        tv_showname = findViewById(R.id.tv_showname);
        tv_showip = findViewById(R.id.tv_showip);
        tv_show_port = findViewById(R.id.tv_show_port);

        btn_makechat = findViewById(R.id.btn_makechat);
        btn_add_new_friend = findViewById(R.id.btn_add_new_friend);

        isFriendflag = false;
        friendId = getIntent().getStringExtra("friendId");
        Log.i("用户详细信息", "用户的Id为：" + friendId != null ? friendId : "***");
    }

    @Override
    protected void onStart() {
        super.onStart();
        new Thread(new Runnable() {
            @Override
            public void run() {
                friend = userService.findFriendByFriendId(friendId);
                Message msg = handler.obtainMessage();
                msg.what = UPDATE_UI;
                msg.obj = friend;
                handler.sendMessage(msg);
                //tv_show_port.setText(friend.getLatest_port());
            }
        }).start();
    }


    private void setListener() {
        btn_makechat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO 是好友才可以聊天
                Intent intent = new Intent();
                intent.putExtra("friendId", getIntent().getStringExtra("friendId"));

                Log.i("FriendDetailActivity:9", getIntent().getStringExtra("friendId"));

                setResult(StaticValue.return_to_chat, intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        });
        btn_add_new_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userService == null) {
                    userService = new UserService(FriendDetailActivity.this);
                }
                new SweetAlertDialog(FriendDetailActivity.this, SweetAlertDialog.SUCCESS_TYPE).setTitleText("成功").setContentText("已发送请求").show();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        userService.askForFriend(user.getChat_number(), friendId);
                    }
                }).start();
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
