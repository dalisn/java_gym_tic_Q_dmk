package gym;

import java.sql.*;
import java.util.*;

public class CourseManager {
    private static final String DB_URL = "jdbc:sqlite:gym.db";

    public CourseManager() {
        DatabaseInitializer.initializeDatabase();
    }

    public void addCourse(FitnessClass course) {
        String sql = "INSERT INTO courses(name, schedule, instructor, availableSlots) VALUES(?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, course.getName());
            pstmt.setString(2, course.getSchedule());
            pstmt.setString(3, course.getInstructor());
            pstmt.setInt(4, course.getAvailableSlots());
            pstmt.executeUpdate();
            System.out.println("Course added successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<FitnessClass> getCourses() {
        String sql = "SELECT * FROM courses";
        List<FitnessClass> courses = new ArrayList<>();
        try (Connection conn = DriverManager.getConnection(DB_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                courses.add(new FitnessClass(rs.getInt("id"), rs.getString("name"), rs.getString("schedule"), rs.getString("instructor"), rs.getInt("availableSlots")));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return courses;
    }

    public void deleteCourse(int courseId) {
        String sql = "DELETE FROM courses WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, courseId);
            pstmt.executeUpdate();
            System.out.println("Course deleted successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void updateCourseDetails(int courseId, String name, String schedule, String instructor, int availableSlots) {
        String sql = "UPDATE courses SET name = ?, schedule = ?, instructor = ?, availableSlots = ? WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(DB_URL);
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, name);
            pstmt.setString(2, schedule);
            pstmt.setString(3, instructor);
            pstmt.setInt(4, availableSlots);
            pstmt.setInt(5, courseId);
            pstmt.executeUpdate();
            System.out.println("Course details updated successfully.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public boolean registerUserToCourse(User user, int courseId) {
        // Implementation of registering user to a course
        return true;
    }

    public boolean discardUserFromCourse(User user, int courseId) {
        // Implementation of discarding user from a course
        return true;
    }

    public List<FitnessClass> getUserCourses(User user) {
        // Implementation of getting user courses
        return new ArrayList<>();
    }

    public void removeUserFromAllCourses(User user) {
        // Implementation based on your application logic
    }
}
