package gym;

import java.io.*;
import java.util.*;

public class CourseManager {
    private static final String COURSE_FILE = "courses.txt";
    private List<FitnessClass> courses;

    public CourseManager() {
        courses = new ArrayList<>();
        loadCourses();
    }

    private void loadCourses() {
        try (BufferedReader br = new BufferedReader(new FileReader(COURSE_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String schedule = parts[2];
                    String instructor = parts[3];
                    int availableSlots = Integer.parseInt(parts[4]);
                    courses.add(new FitnessClass(id, name, schedule, instructor, availableSlots));
                }
            }
        } catch (IOException e) {
            System.out.println("No courses file found. Starting fresh.");
        }
    }

    private void saveCourses() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(COURSE_FILE))) {
            for (FitnessClass course : courses) {
                bw.write(course.getId() + "," + course.getName() + "," + course.getSchedule() + "," + course.getInstructor() + "," + course.getAvailableSlots());
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<FitnessClass> getCourses() {
        return courses;
    }

    public FitnessClass getCourseById(int courseId) {
        for (FitnessClass course : courses) {
            if (course.getId() == courseId) {
                return course;
            }
        }
        return null;
    }

    public boolean registerUserToCourse(User user, int courseId) {
        FitnessClass course = getCourseById(courseId);
        if (course != null && course.getAvailableSlots() > 0) {
            course.registerUser(user);
            saveCourses();
            return true;
        }
        return false;
    }

    public boolean discardUserFromCourse(User user, int courseId) {
        FitnessClass course = getCourseById(courseId);
        if (course != null) {
            course.cancelRegistration(user);
            saveCourses();
            return true;
        }
        return false;
    }

    public void addCourse(FitnessClass course) {
        courses.add(course);
        saveCourses();
    }

    public void deleteCourse(int courseId) {
        FitnessClass course = getCourseById(courseId);
        if (course != null) {
            courses.remove(course);
            saveCourses();
        }
    }

    public void updateCourseDetails(int courseId, String name, String schedule, String instructor, int availableSlots) {
        FitnessClass course = getCourseById(courseId);
        if (course != null) {
            course.setName(name);
            course.setSchedule(schedule);
            course.setInstructor(instructor);
            course.setAvailableSlots(availableSlots);
            saveCourses();
        }
    }
}
