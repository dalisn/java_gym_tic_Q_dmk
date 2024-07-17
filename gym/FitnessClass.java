package gym;

import java.util.HashSet;
import java.util.Set;

public class FitnessClass {
    private int id;
    private String name;
    private String schedule;
    private String instructor;
    private int availableSlots;
    private Set<User> registeredUsers;

    public FitnessClass(int id, String name, String schedule, String instructor, int availableSlots) {
        this.id = id;
        this.name = name;
        this.schedule = schedule;
        this.instructor = instructor;
        this.availableSlots = availableSlots;
        this.registeredUsers = new HashSet<>();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getSchedule() {
        return schedule;
    }

    public String getInstructor() {
        return instructor;
    }

    public int getAvailableSlots() {
        return availableSlots;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSchedule(String schedule) {
        this.schedule = schedule;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public void setAvailableSlots(int availableSlots) {
        this.availableSlots = availableSlots;
    }

    public void registerUser(User user) {
        if (availableSlots > 0 && registeredUsers.add(user)) {
            availableSlots--;
            System.out.println(user.getName() + " registered for " + name + ".");
        } else {
            System.out.println("No available slots or user already registered.");
        }
    }

    public void cancelRegistration(User user) {
        if (registeredUsers.remove(user)) {
            availableSlots++;
            System.out.println(user.getName() + " removed from " + name + ".");
        } else {
            System.out.println("User not registered in this class.");
        }
    }
}
