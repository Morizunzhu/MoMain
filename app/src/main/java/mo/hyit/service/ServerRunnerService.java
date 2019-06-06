package mo.hyit.service;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import mo.hyit.core.Server;
import mo.hyit.dao.UserService;
import mo.hyit.entity.ChatMessage;

import static mo.hyit.utils.StaticValue.ChatMessage_message;
import static mo.hyit.utils.StaticValue.ChatMessage_updateIP;
import static mo.hyit.utils.StaticValue.socket_get_new_msg;
import static mo.hyit.utils.StaticValue.socket_has_new_port;

public class ServerRunnerService extends Service {
    private Server server;
    private Handler handler;
    private int server_port;

    public ServerRunnerService() {
        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what){
                    case socket_has_new_port:{
                        server_port = (int) msg.obj;
                        Log.i("Service获得server_port",""+server_port);
                        ChatMessage data = new ChatMessage();
                        data.setType(ChatMessage_updateIP);
                        data.setMessage(""+server_port);
                        Intent intent = new Intent();
                        intent.setAction("mo.hyit.listener");//用隐式意图来启动广播
                        intent.putExtra("chat_message",data);
                        sendBroadcast(intent);
                        break;
                    }
                    case socket_get_new_msg:{
                        ChatMessage data = new ChatMessage();
                        data.setMessage((String) msg.obj);
                        data.setType(ChatMessage_message);
                        Intent intent = new Intent();
                        intent.setAction("mo.hyit.listenerOfwriter");//用隐式意图来启动广播
                        intent.putExtra("chat_message",data);
                        Log.i("发送广播","发送给静态广播接收者");
                        sendBroadcast(intent);
                        intent.setAction("mo.hyit.listener");//用隐式意图来启动广播
                        intent.putExtra("chat_message",data);
                        Log.i("发送广播","发送给动态广播接收者");
                        sendBroadcast(intent);
                        break;
                    }
                }
            }
        };

        server = new Server(handler);
        //开始监听
        server.startListener();
    }

    @Override
    public IBinder onBind(Intent intent) {
        return new MyBinder();
    }

    public class MyBinder extends Binder{
        public ServerRunnerService getListenerService(){
            return ServerRunnerService.this;
        }
    }

    @Override
    public void onCreate() {
        server = new Server();
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        server.stopListener();
        Log.i("日志","Service已关闭");
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

}
