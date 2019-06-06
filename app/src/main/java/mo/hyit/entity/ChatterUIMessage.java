package mo.hyit.entity;

import java.sql.Timestamp;

public class ChatterUIMessage {
    private String content;
    private TYPE type;
    private Timestamp date;

    public enum TYPE{
        RECEIVED,
        SENT
    }

    public ChatterUIMessage(String content, TYPE type) {
        this.content = content;
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}
