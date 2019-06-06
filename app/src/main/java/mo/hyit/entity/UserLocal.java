package mo.hyit.entity;

public class UserLocal extends User {
    private String head_cache_location;

    public UserLocal() {
    }

    public String getHead_cache_location() {
        return head_cache_location;
    }

    public void setHead_cache_location(String head_cache_location) {
        this.head_cache_location = head_cache_location;
    }
}
