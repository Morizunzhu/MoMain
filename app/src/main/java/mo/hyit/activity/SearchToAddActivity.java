package mo.hyit.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import java.util.List;

import mo.hyit.dao.UserService;
import mo.hyit.entity.User;
import mo.hyit.momain.R;
import mo.hyit.utils.StaticValue;
import mo.hyit.view.SearchListAdapter;

public class SearchToAddActivity extends AppCompatActivity {

    private SearchView sv_svf;
    private ListView lv_searchlist;
    private List<User> userList;
    private UserService userService;
    private Handler handler;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_friend);

        user = (User) getIntent().getSerializableExtra("user");
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                SearchListAdapter adp = new SearchListAdapter(userList,SearchToAddActivity.this);
                lv_searchlist.setAdapter(adp);
                lv_searchlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent intent = new Intent(SearchToAddActivity.this,FriendDetailActivity.class);
                        intent.putExtra("user",user);
                        intent.putExtra("friendId",((User)parent.getItemAtPosition(position)).getChat_number());
                        startActivityForResult(intent,StaticValue.activity_friend_detail);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }
        };
        initView();
        setListener();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case StaticValue.activity_friend_detail:{
                break;
            }
        }
        switch (resultCode){
            case StaticValue.return_list_main:{
                Intent intent = new Intent();
                setResult(StaticValue.return_list_main,intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            }
            case StaticValue.return_to_chat:{
                Intent intent = new Intent();
                intent.putExtra("friendId",getIntent().getStringExtra("friendId"));
                setResult(StaticValue.return_to_chat,intent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                break;
            }
        }
    }

    private void initView() {
        if (userService == null) {
            userService = new UserService(this);
        }
        sv_svf = findViewById(R.id.sv_svf);
        lv_searchlist = findViewById(R.id.lv_searchlist);
    }

    private void setListener() {
        sv_svf.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                final String tmp = query;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Message msg = handler.obtainMessage();
                        Bundle data = new Bundle();
                        data.putString("1", "2");
                        userList = userService.findUserByUserName(tmp);
                        msg.setData(data);
                        msg.what = 0x01;
                        handler.sendMessage(msg);
                    }
                }).start();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
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


