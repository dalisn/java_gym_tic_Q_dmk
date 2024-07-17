import gym.DatabaseInitializer;
import java.util.Scanner;
import java.util.List;
import gym.UserManager;
import gym.SessionManager;
import gym.User;
import gym.CourseManager;
import gym.MessageManager;
import gym.Message;
import gym.FitnessClass;


public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        DatabaseInitializer.initializeDatabase();
        UserManager userManager = new UserManager();
        SessionManager sessionManager = SessionManager.getInstance();
        CourseManager courseManager = new CourseManager();
        MessageManager messageManager = new MessageManager();
        int choice;

        while (true) {
            System.out.println("Welcome to Gym Management System");
            System.out.println("1. Admin");
            System.out.println("2. User");
            System.out.println("3. Exit");
            System.out.print("Please select your role (1-3): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    adminMenu(scanner, userManager, courseManager, messageManager);
                    break;
                case 2:
                    userMenu(scanner, userManager, sessionManager, courseManager, messageManager);
                    break;
                case 3:
                    System.out.println("Exiting...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static void adminMenu(Scanner scanner, UserManager userManager, CourseManager courseManager, MessageManager messageManager) {
        System.out.print("Enter admin username: ");
        String username = scanner.next();
        System.out.print("Enter admin password: ");
        String password = scanner.next();

        if ("admin".equals(username) && "admin".equals(password)) {
            while (true) {
                System.out.println("Admin Menu");
                System.out.println("1. View and Control Users");
                System.out.println("2. See Planning of Courses");
                System.out.println("3. Add New Course");
                System.out.println("4. Delete a Course");
                System.out.println("5. Update Course Details");
                System.out.println("6. Delete a User");
                System.out.println("7. View Messages");
                System.out.println("8. Logout");
                System.out.print("Please select an option (1-8): ");
                int choice = scanner.nextInt();

                switch (choice) {
                    case 1:
                        viewAndControlUsers(scanner, userManager);
                        break;
                    case 2:
                        viewAvailableCourses(courseManager);
                        break;
                    case 3:
                        addNewCourse(scanner, courseManager);
                        break;
                    case 4:
                        deleteCourse(scanner, courseManager);
                        break;
                    case 5:
                        updateCourseDetails(scanner, courseManager);
                        break;
                    case 6:
                        deleteUser(scanner, userManager, courseManager);
                        break;
                    case 7:
                        viewMessages(scanner, messageManager);
                        break;
                    case 8:
                        System.out.println("Admin logged out successfully.");
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } else {
            System.out.println("Invalid admin credentials.");
        }
    }

    public static void userMenu(Scanner scanner, UserManager userManager, SessionManager sessionManager, CourseManager courseManager, MessageManager messageManager) {
        int choice;

        while (true) {
            System.out.println("User Menu");
            System.out.println("1. Login");
            System.out.println("2. Sign Up");
            System.out.println("3. Back to Main Menu");
            System.out.print("Please select an option (1-3): ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    String sessionId = loginUser(scanner, userManager, sessionManager);
                    if (sessionId != null) {
                        userSessionMenu(scanner, sessionManager, courseManager, userManager, messageManager, sessionId);
                    }
                    break;
                case 2:
                    signUpUser(scanner, userManager);
                    break;
                case 3:
                    return; // Go back to main menu
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    public static String loginUser(Scanner scanner, UserManager userManager, SessionManager sessionManager) {
        System.out.println("Login");
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        if (userManager.loginUser(email, password)) {
            User user = userManager.getUserByEmail(email);
            String sessionId = sessionManager.createSession(user);
            System.out.println("User logged in successfully. Session ID: " + sessionId);
            return sessionId;
        } else {
            System.out.println("Invalid email or password.");
            return null;
        }
    }

    public static void signUpUser(Scanner scanner, UserManager userManager) {
        System.out.println("Sign Up");
        System.out.print("Enter name: ");
        String name = scanner.next();
        System.out.print("Enter email: ");
        String email = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();

        userManager.signUpUser(name, email, password);
    }

    public static void userSessionMenu(Scanner scanner, SessionManager sessionManager, CourseManager courseManager, UserManager userManager, MessageManager messageManager, String sessionId) {
        int choice;
        User user = sessionManager.getUserBySessionId(sessionId);
    
        while (true) {
            System.out.println("User Session Menu");
            System.out.println("1. View Available Courses");
            System.out.println("2. Register for a Course");
            System.out.println("3. Discard from a Course");
            System.out.println("4. View Registered Courses");
            System.out.println("5. Update Account Information");
            System.out.println("6. Send Message to Admin");
            System.out.println("7. View Messages and Responses");
            System.out.println("8. Logout");
            System.out.print("Please select an option (1-8): ");
            choice = scanner.nextInt();
    
            switch (choice) {
                case 1:
                    viewAvailableCourses(courseManager);
                    break;
                case 2:
                    registerForCourse(scanner, courseManager, user);
                    break;
                case 3:
                    discardFromCourse(scanner, courseManager, user);
                    break;
                case 4:
                    viewRegisteredCourses(courseManager, user);
                    break;
                case 5:
                    updateAccountInformation(scanner, userManager, user);
                    break;
                case 6:
                    sendMessage(scanner, messageManager, user);
                    break;
                case 7:
                    viewUserMessages(scanner, messageManager, user);
                    break;
                case 8:
                    sessionManager.removeSession(sessionId);
                    System.out.println("User logged out successfully.");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    
    public static void viewAndControlUsers(Scanner scanner, UserManager userManager) {
        System.out.println("All Users:");
        for (User user : userManager.getAllUsers()) {
            System.out.println("ID: " + user.getId() + ", Name: " + user.getName() + ", Email: " + user.getEmail());
        }
    }

    public static void viewAvailableCourses(CourseManager courseManager) {
        System.out.println("Available Courses:");
        for (FitnessClass course : courseManager.getCourses()) {
            System.out.println(course.getId() + ". " + course.getName() + " - " + course.getSchedule() + " - Instructor: " + course.getInstructor() + " - Slots Available: " + course.getAvailableSlots());
        }
    }

    public static void addNewCourse(Scanner scanner, CourseManager courseManager) {
        System.out.println("Add New Course");
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter course schedule: ");
        String schedule = scanner.nextLine();
        System.out.print("Enter course instructor: ");
        String instructor = scanner.nextLine();
        System.out.print("Enter available slots: ");
        int availableSlots = scanner.nextInt();

        FitnessClass newCourse = new FitnessClass(courseManager.getCourses().size() + 1, name, schedule, instructor, availableSlots);
        courseManager.addCourse(newCourse);
        System.out.println("New course added successfully.");
    }

    public static void deleteCourse(Scanner scanner, CourseManager courseManager) {
        System.out.print("Enter the ID of the course you want to delete: ");
        int courseId = scanner.nextInt();
        courseManager.deleteCourse(courseId);
        System.out.println("Course deleted successfully.");
    }

    public static void updateCourseDetails(Scanner scanner, CourseManager courseManager) {
        System.out.print("Enter the ID of the course you want to update: ");
        int courseId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter new course name: ");
        String name = scanner.nextLine();
        System.out.print("Enter new course schedule: ");
        String schedule = scanner.nextLine();
        System.out.print("Enter new course instructor: ");
        String instructor = scanner.nextLine();
        System.out.print("Enter new available slots: ");
        int availableSlots = scanner.nextInt();

        courseManager.updateCourseDetails(courseId, name, schedule, instructor, availableSlots);
        System.out.println("Course details updated successfully.");
    }

    public static void registerForCourse(Scanner scanner, CourseManager courseManager, User user) {
        System.out.print("Enter the ID of the course you want to register for: ");
        int courseId = scanner.nextInt();
        if (courseManager.registerUserToCourse(user, courseId)) {
            System.out.println("Successfully registered for the course.");
        } else {
            System.out.println("Failed to register for the course. It may be full or unavailable.");
        }
    }

    public static void discardFromCourse(Scanner scanner, CourseManager courseManager, User user) {
        System.out.print("Enter the ID of the course you want to discard from: ");
        int courseId = scanner.nextInt();
        if (courseManager.discardUserFromCourse(user, courseId)) {
            System.out.println("Successfully discarded from the course.");
        } else {
            System.out.println("Failed to discard from the course. You may not be registered in it.");
        }
    }

    public static void viewRegisteredCourses(CourseManager courseManager, User user) {
        System.out.println("Registered Courses:");
        List<FitnessClass> userCourses = courseManager.getUserCourses(user);
        if (userCourses.isEmpty()) {
            System.out.println("You are not registered for any courses.");
        } else {
            for (FitnessClass course : userCourses) {
                System.out.println(course.getId() + ". " + course.getName() + " - " + course.getSchedule() + " - Instructor: " + course.getInstructor());
            }
        }
    }

    public static void updateAccountInformation(Scanner scanner, UserManager userManager, User user) {
        System.out.println("Update Account Information");
        System.out.print("Enter new name: ");
        String newName = scanner.next();
        System.out.print("Enter new email: ");
        String newEmail = scanner.next();
        System.out.print("Enter new password: ");
        String newPassword = scanner.next();

        userManager.updateUser(user.getEmail(), newName, newEmail, newPassword);
    }

    public static void deleteUser(Scanner scanner, UserManager userManager, CourseManager courseManager) {
        System.out.print("Enter the email of the user you want to delete: ");
        String email = scanner.next();
        User user = userManager.getUserByEmail(email);
        if (user != null) {
            courseManager.removeUserFromAllCourses(user);
            userManager.deleteUser(email);
            System.out.println("User and their course registrations deleted successfully.");
        } else {
            System.out.println("User not found.");
        }
    }

    public static void sendMessage(Scanner scanner, MessageManager messageManager, User user) {
        System.out.println("Send Message to Admin");
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter your message: ");
        String content = scanner.nextLine();
        messageManager.sendMessage(user.getEmail(), content);
        System.out.println("Message sent successfully.");
    }

    public static void viewMessages(Scanner scanner, MessageManager messageManager) {
        System.out.println("All Messages:");
        List<Message> messages = messageManager.getAllMessages();
        for (Message message : messages) {
            System.out.println("ID: " + message.getId() + ", Sender: " + message.getSender() + ", Content: " + message.getContent() + ", Response: " + message.getResponse());
        }
        System.out.print("Enter the ID of the message you want to respond to: ");
        int messageId = scanner.nextInt();
        scanner.nextLine(); // Consume newline left-over
        System.out.print("Enter your response: ");
        String response = scanner.nextLine();
        messageManager.respondToMessage(messageId, response);
        System.out.println("Response sent successfully.");
    }

    public static void viewUserMessages(Scanner scanner, MessageManager messageManager, User user) {
        System.out.println("Your Messages and Admin Responses:");
        List<Message> messages = messageManager.getAllMessages();
        for (Message message : messages) {
            if (message.getSender().equals(user.getEmail())) {
                System.out.println("ID: " + message.getId() + ", Content: " + message.getContent() + ", Response: " + (message.getResponse().isEmpty() ? "No response yet" : message.getResponse()));
            }
        }
    }
    
}
