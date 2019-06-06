package mo.hyit.entity;

import java.io.Serializable;

public class User implements Serializable {
    private String name;
    private String passwd;
    private String chat_number;
    private String latest_ip;
    private int latest_port;


    public User() {
    }

    public User(String name, String passwd, String chat_number, String latest_ip,int latest_port) {
        this.name = name;
        this.passwd = passwd;
        this.chat_number = chat_number;
        this.latest_ip = latest_ip;
        this.latest_port = latest_port;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getChat_number() {
        return chat_number;
    }

    public void setChat_number(String chat_number) {
        this.chat_number = chat_number;
    }

    public String getLatest_ip() {
        return latest_ip;
    }

    public void setLatest_ip(String latest_ip) {
        this.latest_ip = latest_ip;
    }

    public int getLatest_port() {
        return latest_port;
    }

    public void setLatest_port(int latest_port) {
        this.latest_port = latest_port;
    }
}
