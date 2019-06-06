package mo.hyit.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import mo.hyit.core.Client;
import mo.hyit.entity.ChatMessage;
import mo.hyit.entity.ChatterUIMessage;
import mo.hyit.entity.User;
import mo.hyit.momain.R;
import mo.hyit.dao.UserService;
import mo.hyit.utils.NetWorkTool;
import mo.hyit.utils.XmlTool;
import mo.hyit.view.MineRecyclerViewAdapter;

import static mo.hyit.utils.StaticValue.ChatMessage_message;
import static mo.hyit.utils.StaticValue.ChatMessage_updateIP;
import static mo.hyit.utils.StaticValue.UPDATE_UI;
import static mo.hyit.utils.StaticValue.net_handler_find_friend_compelete;

public class ChatterActivity extends AppCompatActivity {

    private EditText et_inputText;
    private Button btn_sender;
    private RecyclerView rcv;
    private List<ChatterUIMessage> msgList;// = new ArrayList<>();
    private XmlTool xmlTool;

    private MineRecyclerViewAdapter rcv_adapter;

    private User user, friend;
    private String friendId;
    private Handler handler;
    private UserService userService;
    private BroadcastReceiver msgReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final ChatMessage data = (ChatMessage) intent.getSerializableExtra("chat_message");
            Log.e("聊天界面接收到广播", "接收到Socket消息：" + data.getMessage());
            switch (data.getType()) {
                case ChatMessage_updateIP: {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            boolean res_tmp = userService.updateIPandPort(user.getChat_number(), NetWorkTool.getLocalIp(), Integer.parseInt(data.getMessage()));
                            Log.i("更新数据端口", "更新结果：" + res_tmp);
                        }
                    }).start();
                    break;
                }
                case ChatMessage_message: {
                    String[] res = data.getMessage().split("@");
                    //唤醒列表更新
                    Log.i("聊天界面接收到广播", "接收到新消息，唤醒列表更新");
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            getChatData();
                            handler.sendEmptyMessage(UPDATE_UI);
                        }
                    }).start();

                    break;
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chatter);

        init();

        initView();
        setListener();
    }

    @Override
    protected void onResume() {
        super.onResume();

        IntentFilter filter = new IntentFilter();
        filter.addAction("mo.hyit.listener");
        registerReceiver(msgReceiver, filter);
        initData();
    }

    @Override
    protected void onPause() {
        super.onPause();

        unregisterReceiver(msgReceiver);
    }

    private void initData() {
        user = (User) getIntent().getSerializableExtra("user");
        friendId = getIntent().getStringExtra("friendId");

        Log.i("聊天Activity", "initData通过意图获得friendId的值是:" + friendId);
        new Thread(new Runnable() {
            @Override
            public void run() {
                friend = userService.findFriendByFriendId(friendId);
                handler.sendEmptyMessage(net_handler_find_friend_compelete);
            }
        }).start();

    }

    private void setListener() {
        btn_sender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String content = et_inputText.getText().toString();
                Log.v("点击发送按钮", "获取到edittext控件内容" + content);
                if ("".equals(content))
                    return;
                Log.v("客户端准备发射", "即将发送文字：" + content + "," + user.getChat_number() + "给另一个用户" + friend.getChat_number() + "，他的ip和端口分别是" +friend.getLatest_ip()+","+ friend.getLatest_port());
                new Client().sendMessage(user.getChat_number() + "@" + content, friend.getLatest_ip(), friend.getLatest_port());

                //写入文件
                if(xmlTool == null){
                    xmlTool = new XmlTool(ChatterActivity.this);
                }
                xmlTool.writeChatInfoToFile(user.getChat_number(),friendId,content,"me");
                msgList.add(new ChatterUIMessage(content, ChatterUIMessage.TYPE.SENT));

                //如果有新消息，则设置适配器的长度（通知适配器，有新的数据被插入），并让 RecyclerView 定位到最后一行
                rcv_adapter.notifyItemInserted(msgList.size() - 1);
                rcv.scrollToPosition(msgList.size() - 1);

                //清空输入框中的内容
                et_inputText.setText("");
            }
        });
    }

    private void initView() {
        if (userService == null) {
            userService = new UserService(this);
        }
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case net_handler_find_friend_compelete: {
                        //TODO
                        ChatterActivity.this.setTitle(friend.getName());
                        break;
                    }
                    case UPDATE_UI:{
                        rcv_adapter.notifyItemInserted(msgList.size());
                        rcv.scrollToPosition(msgList.size());
                        break;
                    }
                }
            }
        };

        rcv = findViewById(R.id.rcv);
        btn_sender = findViewById(R.id.btn_sender);
        et_inputText = findViewById(R.id.et_inputText);


        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);

        rcv_adapter = new MineRecyclerViewAdapter(msgList == null ? new ArrayList<ChatterUIMessage>() : msgList);
        rcv.setAdapter(rcv_adapter);
    }

    private void init() {
        user = (User) getIntent().getSerializableExtra("user");
        friendId = getIntent().getStringExtra("friendId");

        getChatData();
        /*
        msgList.add(new ChatterUIMessage("你好", ChatterUIMessage.TYPE.RECEIVED));
        msgList.add(new ChatterUIMessage("你好，请问你是？", ChatterUIMessage.TYPE.SENT));
        msgList.add(new ChatterUIMessage("我是 deniro，很高兴认识你^_^", ChatterUIMessage.TYPE.RECEIVED));
        */
    }

    private int getChatData() {
        if(xmlTool == null){
            xmlTool = new XmlTool(ChatterActivity.this);
        }
        Log.i("请求聊天数据", "查找和QQ号为：" + friendId + "的好友的聊天记录");
        msgList = xmlTool.readChatInfo(user.getChat_number(), friendId);
        if (msgList == null) {
            msgList = new ArrayList<>();
        }
        Log.i("请求聊天数据完毕", "查找到" + msgList.size() + "条聊天记录");
        return msgList.size();
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
