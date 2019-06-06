package mo.hyit.core;

import android.util.Log;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

public class Client {
    private Socket client;
    private PrintWriter pw;

    public Client() {

    }

    public void sendMessage(String msg, String send_to_ip, int port) {
        new MyThread(msg, send_to_ip,port).start();
    }

    class MyThread  extends Thread{

        private String msg,send_to_ip;
        private int port;

        public MyThread(String msg, String send_to_ip, int port){
            this.msg = msg;
            this.send_to_ip = send_to_ip;
            this.port = port;
        }

        @Override
        public void run() {
            try {
                client = new Socket(send_to_ip, port);
                pw = new PrintWriter(client.getOutputStream());
                pw.print(msg);
                pw.flush();
                Thread.sleep(500);
                pw.close();
                client.close();
            } catch (SocketTimeoutException e) {
                Log.e("Client Socket超时","发送消息失败，Socket超时");
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
