package mo.hyit.core;

import android.os.Handler;
import android.os.Message;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;

import mo.hyit.utils.StaticValue;

import static mo.hyit.utils.StaticValue.socket_has_new_port;


public class Server {

    private static int max = 999, min = 1;
    private static int listen_port;

    private Handler handler;
    private Thread msg_reader_thread;
    private ServerSocket server;
    public Boolean isListenFlag = false;

    static {
        listen_port = new Random().nextInt(max) % (max - min + 1) + min;
        listen_port += 9000;
        Log.i("服务端监听端口建立", "当前监听端口：" + listen_port);
    }

    public Server() {
    }

    public Server(Handler handler) {
        this.handler = handler;

        Message msg = handler.obtainMessage();
        msg.what = socket_has_new_port;
        msg.obj = listen_port;
        Log.i("服务端监听端口", "当前监听端口：" + listen_port+",即将存入数据库端口为"+(listen_port));
        handler.sendMessage(msg);
    }

    public void startListener() {
        msg_reader_thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    server = new ServerSocket(listen_port);
                    isListenFlag = true;
                    while (isListenFlag) {
                        if (msg_reader_thread.isInterrupted()) {
                            throw new InterruptedException();
                        }
                        Log.i("服务开始监听", "server.accepting,"+server.getInetAddress()+","+server.getLocalSocketAddress());
                        final Socket client = server.accept();
                        Log.i("服务器", "获得客户端连接");
                        new MyThread(client).start();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Log.e("mo.hyit.core.server", "线程已经停止");
                    e.printStackTrace();
                }
            }
        });
        msg_reader_thread.start();
    }

    public void stopListener() {
        if (server!=null && !server.isClosed()) {
            try {
                server.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (msg_reader_thread != null && Thread.State.RUNNABLE == msg_reader_thread.getState()) {
            try {
                Thread.sleep(50);
                msg_reader_thread.interrupt();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                msg_reader_thread = null;
            }
        }
    }

    public Thread getMsg_reader_thread() {
        return msg_reader_thread;
    }

    public void setMsg_reader_thread(Thread msg_reader_thread) {
        this.msg_reader_thread = msg_reader_thread;
    }
    class MyThread extends  Thread{
        Socket client;

        public MyThread(Socket client){
            this.client = client;
        }

        @Override
        public void run() {
            super.run();
            try {
                BufferedReader br = new BufferedReader(new InputStreamReader(client.getInputStream()));
                String sbf = br.readLine();
                Message msg = handler.obtainMessage();
                msg.what = StaticValue.socket_get_new_msg;
                Log.i("服务器获得数据", "客服端发来数据" + sbf);
                msg.obj = sbf;
                handler.sendMessage(msg);
                br.close();
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
