package mo.hyit.utils;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import mo.hyit.entity.Friends;
import mo.hyit.entity.User;

public class JsonToObject {
    public static List<User> getUsersByJsonResult(String json){
        List<User> userList = new ArrayList<>();
        try {
            JSONArray users = new JSONObject(json).getJSONArray("users");
            for(int i =0;i<users.length();i++){
                User user = new User();
                JSONObject tmp = new JSONObject(users.get(i).toString());
                user.setName(tmp.getString("name"));
                user.setChat_number(tmp.getString("chat_number"));
                user.setLatest_ip(tmp.getString("latest_ip"));
                userList.add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static List<Integer> getFriendsingByJsonResult(String json){
        List<Integer> userList = new ArrayList<>();
        try {
            JSONArray users = new JSONObject(json).getJSONArray("friendsing");
            Log.e("格式化添加好友Json数据","得到的数据为："+json);
            for(int i =0;i<users.length();i++){
                JSONObject tmp = new JSONObject(users.get(i).toString());
                Integer isn = tmp.getInt("value");
                userList.add(isn);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public static User getUserByJsonResult(String json){
        try {
            JSONObject tmp = new JSONObject(json);
            User user = new User();
            user.setName(tmp.getString("name"));
            user.setChat_number(tmp.getString("chat_number"));
            user.setLatest_ip(tmp.getString("latest_ip"));
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Friends getFriendsByJsonResult(String json,String chat_number){
        Friends friends = new Friends();
        friends.setChat_number(chat_number);
        try {
            JSONArray fs = new JSONObject(json).getJSONArray("friends");
            for (int i =0;i<fs.length();i++){
                User user = new User();
                JSONObject tmp = new JSONObject(fs.get(i).toString());
                user.setName(tmp.getString("name"));
                user.setChat_number(tmp.getString("chat_number"));
                user.setLatest_ip(tmp.getString("latest_ip"));
                friends.getFriends().add(user);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return friends;
    }

    public static User findFriendByJsonResult(String json){
        User user= new User();
        try {
            JSONObject tmp = new JSONObject(json).getJSONObject("friend");
            user.setName(tmp.getString("name"));
            user.setChat_number(tmp.getString("chat_number"));
            user.setLatest_ip(tmp.getString("latest_ip"));
            user.setLatest_port(tmp.getInt("latest_port"));
            return user;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
