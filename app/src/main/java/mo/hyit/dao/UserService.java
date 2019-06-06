package mo.hyit.dao;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import mo.hyit.entity.Friends;
import mo.hyit.entity.User;
import mo.hyit.momain.R;
import mo.hyit.utils.HttpJsonTool;
import mo.hyit.utils.JsonToObject;
import mo.hyit.utils.Md5Tool;

public class UserService {
    private HttpJsonTool http;
    private Context context;
    private String serve_ip = "192.168.1.111";

    public UserService(Context context) {
        http = new HttpJsonTool();
        this.context = context;
    }

    public String checkLogin(String chat_number, String userPwd) {
        JSONObject user = new JSONObject();
        try {
            user.put("chat_number", chat_number);
            user.put("passwd", Md5Tool.getStringMd5(userPwd));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            return http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_login), user.toString(), "", "");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public String doRegister(User user) {
        JSONObject res = new JSONObject();
        try {
            res.put("name", user.getName());
            res.put("passwd", Md5Tool.getStringMd5(user.getPasswd()));
            res.put("chat_number", "");
            res.put("latest_ip", user.getLatest_ip());
            res.put("latest_port", user.getLatest_port());
            return http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_register), res.toString(), "", "");
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    public List<User> findUserByUserName(String userName) {
        JSONObject res = new JSONObject();
        try {
            res.put("name", userName);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        try {
            String result = http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_finduser), res.toString(), "", "");
            Log.i("QAQ", result);
            return JsonToObject.getUsersByJsonResult(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Friends findFriendsByUserNum(String chat_number) {
        JSONObject res = new JSONObject();
        try {
            res.put("chat_number", chat_number);
            String result = http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_findfriend), res.toString(), "", "");
            Log.i("QAQ", result);
            return JsonToObject.getFriendsByJsonResult(result, chat_number);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public User findFriendByFriendId(String chat_number) {
        JSONObject res = new JSONObject();
        try {
            res.put("chat_number", chat_number);
            String result = http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_find_one_user), res.toString(), "", "");
            Log.i("QAQ", result);
            return JsonToObject.findFriendByJsonResult(result);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean updateIPandPort(String chat_number, String latest_ip, int latest_port) {
        JSONObject res = new JSONObject();
        try {
            res.put("chat_number", chat_number);
            res.put("latest_ip", latest_ip);
            res.put("latest_port", latest_port);
            String result = http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_update_ip_and_port), res.toString(), "", "");
            Log.i("访问数据库API", result);
            return true;
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public void askForFriend(String sender_id, String receiver_id) {
        JSONObject res = new JSONObject();
        try {
            res.put("sender_id", sender_id);
            res.put("receiver_id", receiver_id);
            String result = http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_askforfriend), res.toString(), "", "");
            Log.i("访问数据库API", result);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Integer> ListAskforfriend(String receiver_id) {
        JSONObject res = new JSONObject();
        try {
            res.put("receiver_id", receiver_id);
            String result = http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_listaskforfriend), res.toString(), "", "");
            Log.i("更新添加好友表", result);
            return JsonToObject.getFriendsingByJsonResult(result);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void updateAskForFriend(String sender_id, String receiver_id) {
        JSONObject res = new JSONObject();
        try {
            res.put("sender_id", sender_id);
            res.put("receiver_id", receiver_id);
            String result = http.post("http://" + serve_ip + ":8080" + context.getString(R.string.serverAPI_updateAskForFriend), res.toString(), "", "");
            Log.i("访问数据库API", result);
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
