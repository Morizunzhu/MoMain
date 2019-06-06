package mo.hyit.entity;

import java.util.ArrayList;
import java.util.List;

public class Friends {
    private String chat_number;
    private List<User> friends;

    public Friends() {
        friends = new ArrayList<>();
    }

    public String getChat_number() {
        return chat_number;
    }

    public void setChat_number(String chat_number) {
        this.chat_number = chat_number;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
