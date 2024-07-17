package gym;

public class Message {
    private int id;
    private String sender;
    private String content;
    private String response;

    public Message(int id, String sender, String content, String response) {
        this.id = id;
        this.sender = sender;
        this.content = content;
        this.response = response;
    }

    public int getId() {
        return id;
    }

    public String getSender() {
        return sender;
    }

    public String getContent() {
        return content;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }
}
