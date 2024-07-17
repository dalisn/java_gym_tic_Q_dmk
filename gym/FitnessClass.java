package gym;

public class FitnessClass {
    private int id;
    private String name;
    private String schedule;
    private String instructor;
    private int availableSlots;

    public FitnessClass(int id, String name, String schedule, String instructor, int availableSlots) {
        this.id = id;
        this.name = name;
        this.schedule = schedule;
        this.instructor = instructor;
        this.availableSlots = availableSlots;
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
}
