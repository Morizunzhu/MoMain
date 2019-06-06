package mo.hyit.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import mo.hyit.entity.ChatMessage;
import mo.hyit.utils.XmlTool;

import static mo.hyit.utils.StaticValue.ChatMessage_message;

public class ChatInfoBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        final ChatMessage data = (ChatMessage) intent.getSerializableExtra("chat_message");
        Log.e("聊天界面接收到广播", "接收到Socket消息："+data.getMessage());
        SharedPreferences sp =context.getSharedPreferences("login_cookie",Context.MODE_PRIVATE);
        String userId = sp.getString("login_user","");
        XmlTool xmlTool = new XmlTool(context);
        switch (data.getType()) {
            case ChatMessage_message: {
                if(data.getMessage() != null){
                    String[] res = data.getMessage().split("@");
                    //写入xml
                    Log.i("静态广播","接收到新消息，写入聊天记录文件");
                    xmlTool.writeChatInfoToFile(userId,res[0],res[1],"you");
                }else{
                    Log.w("静态广播","接收到新消息，但消息是NULL");
                }
                break;
            }
        }
    }
}
