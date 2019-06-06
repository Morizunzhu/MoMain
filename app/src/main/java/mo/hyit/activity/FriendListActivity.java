package mo.hyit.activity;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.AdapterView;
import android.widget.SearchView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mo.hyit.entity.ChatMessage;
import mo.hyit.entity.Friends;
import mo.hyit.entity.User;
import mo.hyit.momain.R;
import mo.hyit.dao.UserService;
import mo.hyit.service.ServerRunnerService;
import mo.hyit.utils.NetWorkTool;
import mo.hyit.utils.StaticValue;
import mo.hyit.utils.ToastTool;
import mo.hyit.utils.XmlTool;
import mo.hyit.view.SearchListAdapter;

import static mo.hyit.utils.StaticValue.ChatMessage_message;
import static mo.hyit.utils.StaticValue.ChatMessage_updateIP;
import static mo.hyit.utils.StaticValue.activity_chatter;
import static mo.hyit.utils.StaticValue.activity_friend_detail;
import static mo.hyit.utils.StaticValue.activity_main;
import static mo.hyit.utils.StaticValue.return_to_chat;

public class FriendListActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private FloatingActionButton fab;
    private DrawerLayout drawer;
    private ListView listview_firendlist;
    private List<Map<String, String>> friendList;
    private Button button_list, button_chat;
    private TextView tv_show_name, textView_show_test;
    private LinearLayout include_friendlist, include_chatter;
    private SearchView sv_searchfriend;
    private User user;
    private Friends user_friend;
    private UserService userService;
    private Handler handler;

    private ServiceConnection communicate = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            server_service = ((ServerRunnerService.MyBinder) service).getListenerService();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };
    private BroadcastReceiver msgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final ChatMessage data = (ChatMessage) intent.getSerializableExtra("chat_message");
            Log.e("主界面接收到广播", "接收到Socket消息："+data.getMessage());
            switch (data.getType()) {
                case ChatMessage_updateIP: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean res_tmp = userService.updateIPandPort(user.getChat_number(),NetWorkTool.getLocalIp(),Integer.parseInt(data.getMessage()));
                            Log.i("更新数据端口","更新结果："+res_tmp);
                        }
                    }).start();
                    break;
                }
            }
        }
    };
    private ServerRunnerService server_service;

    private XmlTool xmlTool;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_list);

        init();
        initView();
        setListener();

        Intent bd_intent = new Intent(this, ServerRunnerService.class);
        bindService(bd_intent, communicate, BIND_AUTO_CREATE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();

        IntentFilter filter = new IntentFilter();
        filter.addAction("mo.hyit.listener");
        registerReceiver(msgReceiver, filter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(msgReceiver);
        unbindService(communicate);
        Intent tmpIntent = new Intent(this, ServerRunnerService.class);
        stopService(tmpIntent);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    private void init() {
        user = (User) getIntent().getSerializableExtra("user");
        friendList = new ArrayList<>();
        xmlTool = new XmlTool(this);
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case StaticValue.get_friends: {
                        readListView();
                        break;
                    }
                }
            }
        };

    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        fab = (FloatingActionButton) findViewById(R.id.fab);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        listview_firendlist = findViewById(R.id.listview_firendlist);
        button_list = findViewById(R.id.button_list);
        button_chat = findViewById(R.id.button_chat);
        include_friendlist = findViewById(R.id.include_friendlist);
        include_chatter = findViewById(R.id.include_chatter);
        sv_searchfriend = findViewById(R.id.sv_searchfriend);
        tv_show_name = navigationView.getHeaderView(0).findViewById(R.id.tv_show_name);
        textView_show_test = navigationView.getHeaderView(0).findViewById(R.id.textView_show_test);

        if (userService == null) {
            userService = new UserService(this);
        }
    }

    private void setListener() {
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        navigationView.setNavigationItemSelectedListener(this);

        button_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                include_friendlist.setVisibility(View.VISIBLE);
                include_chatter.setVisibility(View.GONE);
            }
        });
        button_chat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                include_friendlist.setVisibility(View.GONE);
                include_chatter.setVisibility(View.VISIBLE);
            }
        });

        sv_searchfriend.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                ToastTool.showToast(FriendListActivity.this, s, 500);
                sv_searchfriend.clearFocus();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    private void initData() {

        tv_show_name.setText(user.getName());
        textView_show_test.setText(user.getLatest_ip());

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (user_friend != null) {
                    user_friend.getFriends().clear();
                }
                user_friend = userService.findFriendsByUserNum(user.getChat_number());
                handler.sendEmptyMessage(StaticValue.get_friends);
            }
        }).start();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case return_to_chat: {
                Intent intent = new Intent(FriendListActivity.this, ChatterActivity.class);
                String friendId = data.getStringExtra("friendId");

                Log.i("FriendListActivity:2", friendId);

                intent.putExtra("user", user);
                intent.putExtra("friendId", friendId);
                startActivityForResult(intent, activity_chatter);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                break;
            }
        }
        switch (requestCode) {
            case activity_friend_detail: {
                break;
            }
        }
    }

    private void readListView() {
        SearchListAdapter adp = new SearchListAdapter(user_friend.getFriends(), FriendListActivity.this);
        listview_firendlist.setAdapter(adp);
        listview_firendlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(FriendListActivity.this, FriendDetailActivity.class);
                intent.putExtra("user", user);
                intent.putExtra("friendId", ((User) parent.getItemAtPosition(position)).getChat_number());

                Log.i("主页面用户列表点击事件", "点击的用户的Id为："+((User) parent.getItemAtPosition(position)).getChat_number());
                startActivityForResult(intent, activity_main);
//                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.friend_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            SharedPreferences sp = getSharedPreferences("login_cookie", Context.MODE_PRIVATE);
            sp.edit().putInt("login_status", 1).putString("login_user", user.getChat_number()).apply();
            finish();
            return true;
        } else if (id == R.id.button_addfriend) {
            Intent intent = new Intent(FriendListActivity.this, SearchToAddActivity.class);
            intent.putExtra("user",user);
            startActivityForResult(intent, StaticValue.activity_search_to_add);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_friend_apply) {
            Intent intent = new Intent(FriendListActivity.this, NewFriendConfirmActivity.class);
            intent.putExtra("user",user);
            startActivity(intent);
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
