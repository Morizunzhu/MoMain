package mo.hyit.lesson.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Enumeration;

import mo.hyit.lesson.R;

public class TestSocketActivity extends AppCompatActivity {

    private TextView textView_socket_show;
    private Button button_socket_start, button_socket_stop, button_socket_client;
    private ServerSocket sst;
    private Socket clientSt;
    private Thread sstThread;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_socket);

        initView();
        setListener();

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Log.e("666",""+getLocalIp());
                    textView_socket_show.setText(getLocalIp()+"\n");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private void initView() {
        sst = null;
        textView_socket_show = findViewById(R.id.textView_socket_show);
        button_socket_start = findViewById(R.id.button_socket_start);
        button_socket_stop = findViewById(R.id.button_socket_stop);
        button_socket_client = findViewById(R.id.button_socket_client);
    }

    private void setListener() {
        button_socket_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sst == null || !sst.isClosed()) {
                    sstThread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sst = new ServerSocket(6788);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        textView_socket_show.append("服务器已启动!\n");
                                    }
                                });
                                boolean test = true;
                                while (test){
                                    clientSt = sst.accept();
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            textView_socket_show.append("get client connection!\n");
                                        }
                                    });
                                }
                                sst.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    sstThread.start();
                }
            }
        });
        button_socket_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Log.i("666","socket开始访问");
                            Socket st = new Socket("10.0.2.2", 6788);
                            Log.i("666","socket访问结束");
                            st.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
    }

    private static String getLocalIp() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface
                    .getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf
                        .getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()
                            && inetAddress instanceof Inet4Address) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return "0.0.0.0";

    }
}
