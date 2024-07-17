package gym;

import java.io.*;
import java.util.*;

public class MessageManager {
    private static final String MESSAGE_FILE = "messages.txt";
    private List<Message> messages;

    public MessageManager() {
        messages = new ArrayList<>();
        loadMessages();
    }

    public void sendMessage(String sender, String content) {
        int id = messages.size() + 1;
        Message message = new Message(id, sender, content, "");
        messages.add(message);
        saveMessages();
    }

    public List<Message> getAllMessages() {
        return messages;
    }

    public Message getMessageById(int id) {
        for (Message message : messages) {
            if (message.getId() == id) {
                return message;
            }
        }
        return null;
    }

    public void respondToMessage(int id, String response) {
        Message message = getMessageById(id);
        if (message != null) {
            message.setResponse(response);
            saveMessages();
        }
    }

    private void loadMessages() {
        try (BufferedReader br = new BufferedReader(new FileReader(MESSAGE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    int id = Integer.parseInt(parts[0]);
                    String sender = parts[1];
                    String content = parts[2];
                    String response = parts[3];
                    messages.add(new Message(id, sender, content, response));
                }
            }
        } catch (IOException e) {
            System.out.println("No messages file found. Starting fresh.");
        }
    }

    private void saveMessages() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(MESSAGE_FILE))) {
            for (Message message : messages) {
                bw.write(message.getId() + "," + message.getSender() + "," + message.getContent() + "," + message.getResponse());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
