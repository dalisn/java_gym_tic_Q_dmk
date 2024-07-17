package gym;

import java.sql.*;
import java.util.*;

public class MessageManager {
    private static final String DB_URL = "jdbc:sqlite:gym.db";

    public MessageManager() {
        DatabaseInitializer.initializeDatabase();
    }

    public void sendMessage(String sender, String content) {
        String sql = "INSERT INTO messages(sender, content, response) VALUES(?, ?, '')";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, sender);
            pstmt.setString(2, content);
            pstmt.executeUpdate();
            System.out.println("Message sent successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<Message> getAllMessages() {
        String sql = "SELECT * FROM messages";
        List<Message> messages = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                messages.add(new Message(rs.getInt("id"), rs.getString("sender"), rs.getString("content"), rs.getString("response")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return messages;
    }

    public void respondToMessage(int id, String response) {
        String sql = "UPDATE messages SET response = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, response);
            pstmt.setInt(2, id);
            pstmt.executeUpdate();
            System.out.println("Response sent successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
