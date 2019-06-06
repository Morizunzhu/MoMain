package mo.hyit.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mo.hyit.entity.User;
import mo.hyit.dao.UserService;
import mo.hyit.momain.R;
import mo.hyit.utils.JsonToObject;
import mo.hyit.utils.NetWorkTool;
import mo.hyit.view.DropEditText;

public class LoginActivity extends AppCompatActivity {

    private TextView tv_login_new_register;
    private DropEditText dropEditText;
    private EditText editText_passwd;
    private ArrayAdapter<String> adapter;
    private Button button_login;
    private static UserService userService;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("value");

                Log.i("访问Web服务器",val);

                try {
                    JSONObject res = new JSONObject(val);
                    if(res.getInt("code") != 1){
                        //登录失败
                        new SweetAlertDialog(LoginActivity.this,SweetAlertDialog.ERROR_TYPE).setTitleText("错误").setContentText("登录失败").show();
                    }else{
                        //登陆成功
                        //记录登录状态、更新最新的ip
                        SharedPreferences sp = getSharedPreferences("login_cookie",Context.MODE_PRIVATE);
                        User user = JsonToObject.getUserByJsonResult(res.getJSONObject("user").toString());
                        sp.edit().putInt("login_status",1).putString("login_user",user.getChat_number()).apply();
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                String localip = NetWorkTool.getLocalIp();

                            }
                        }).start();

                        //跳转的主页面
                        Intent intent = new Intent(LoginActivity.this,FriendListActivity.class);
                        intent.putExtra("user",user);
                        startActivity(intent);
                        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        initView();
        initData();
        setListener();
    }

    private void initView() {
        dropEditText = findViewById(R.id.drop_edit_text);
        button_login = findViewById(R.id.button_login);
        tv_login_new_register = findViewById(R.id.tv_login_new_register);
        editText_passwd = findViewById(R.id.editText_passwd);

        if(userService == null){
            userService = new UserService(this);
        }
    }

    private void setListener() {
        tv_login_new_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String tmp = userService.checkLogin(dropEditText.getText().toString().trim(),editText_passwd.getText().toString().trim());
                        Message msg = handler.obtainMessage();
                        Bundle data = new Bundle();
                        data.putString("value",tmp);
                        msg.setData(data);
                        handler.sendMessage(msg);
                    }
                }).start();
            }
        });
    }

    private void initData(){
        readLoginData();
    }

    // TODO 登录过的用户有缓存
    private void readLoginData(){
        String[] strings = new String[10];
        for (int i = 0; i < 1; i++) {
            strings[i] = "940406032";
        }
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, strings);
        dropEditText.setAdapter(adapter);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
