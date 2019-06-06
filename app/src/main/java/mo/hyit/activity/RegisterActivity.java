package mo.hyit.activity;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.Inet4Address;

import cn.pedant.SweetAlert.SweetAlertDialog;
import mo.hyit.dao.UserService;
import mo.hyit.entity.User;
import mo.hyit.momain.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText edittext_register_uname, edittext_register_upwd, edittext_register_reupwd;
    private Button button_register_register;
    private UserService userService;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle data = msg.getData();
                String val = data.getString("value");

                Log.i("访问Web服务器",val);

                try {
                    JSONObject user = new JSONObject(val);
                    if(user.getInt("code") == -1){
                        new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.ERROR_TYPE).setTitleText("错误").setContentText("注册失败").show();
                    }else{
                        new SweetAlertDialog(RegisterActivity.this,SweetAlertDialog.SUCCESS_TYPE).setTitleText("成功").setContentText("注册成功").setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                finish();
                                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
                            }
                        }).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        initView();
        setListener();
    }

    private void setListener() {
        button_register_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!checkData()) {
                    new SweetAlertDialog(RegisterActivity.this, SweetAlertDialog.ERROR_TYPE).setTitleText("错误").setContentText("密码不能为空或两次密码不一致").show();
                }
            }
        });
    }

    private void initView() {
        edittext_register_uname = findViewById(R.id.edittext_register_uname);
        edittext_register_upwd = findViewById(R.id.edittext_register_upwd);
        edittext_register_reupwd = findViewById(R.id.edittext_register_reupwd);
        button_register_register = findViewById(R.id.button_register_register);

        if(userService == null){
            userService = new UserService(this);
        }
    }

    private boolean checkData() {
        String uname = edittext_register_uname.getText().toString().trim(),
                reupwd = edittext_register_reupwd.getText().toString().trim(),
                upwd = edittext_register_upwd.getText().toString().trim();
        if("".equals(uname) ||"".equals(reupwd)  ||"".equals(upwd) || !reupwd.equals(upwd)){
            return false;
        }else {
            new Thread(new Runnable() {
                @TargetApi(Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {
                    User user = new User(edittext_register_uname.getText().toString().trim(),edittext_register_upwd.getText().toString().trim(),"",Inet4Address.getLoopbackAddress().toString(),0);
                    String res = userService.doRegister(user);
                    Bundle data = new Bundle();
                    data.putString("value",res);
                    Message msg =  handler.obtainMessage();
                    msg.setData(data);
                    handler.sendMessage(msg);
                }
            }).start();
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }
}
