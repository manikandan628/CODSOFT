import java.util.*;
import java.io.*;
import java.util.regex.*;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.UUID;

// Student class with unique UUID
class Student {
    private UUID id;
    private String name;
    private int rollNumber;
    private String grade;
    private String email;

    public Student(String name, int rollNumber, String grade, String email) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.rollNumber = rollNumber;
        this.grade = grade;
        this.email = email;
    }

    // Getters and Setters
    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(int rollNumber) {
        this.rollNumber = rollNumber;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "Student [ID=" + id + ", Name=" + name + ", Roll Number=" + rollNumber +
               ", Grade=" + grade + ", Email=" + email + "]";
    }
}

// StudentManagementSystem class to handle student operations
class StudentManagementSystem {
    private List<Student> students;

    public StudentManagementSystem() {
        this.students = new ArrayList<>();
    }

    // Add a new student
    public void addStudent(Student student) {
        students.add(student);
        System.out.println("Student added successfully.");
    }

    // Remove a student by roll number
    public void removeStudent(int rollNumber) {
        Student student = searchStudentByRollNumber(rollNumber);
        if (student != null) {
            students.remove(student);
            System.out.println("Student removed successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Search for a student by roll number
    public Student searchStudentByRollNumber(int rollNumber) {
        for (Student student : students) {
            if (student.getRollNumber() == rollNumber) {
                return student;
            }
        }
        return null;
    }

    // Search for a student by name
    public Student searchStudentByName(String name) {
        for (Student student : students) {
            if (student.getName().equalsIgnoreCase(name)) {
                return student;
            }
        }
        return null;
    }

    // Edit a student's details
    public void editStudent(int rollNumber, String newName, String newGrade, String newEmail) {
        Student student = searchStudentByRollNumber(rollNumber);
        if (student != null) {
            student.setName(newName);
            student.setGrade(newGrade);
            student.setEmail(newEmail);
            System.out.println("Student details updated successfully.");
        } else {
            System.out.println("Student not found.");
        }
    }

    // Display all students
    public void displayAllStudents() {
        if (students.isEmpty()) {
            System.out.println("No students to display.");
        } else {
            for (Student student : students) {
                System.out.println(student);
            }
        }
    }

    // Save student data to JSON file
    public void saveToJSON(String filePath) {
        Gson gson = new Gson();
        try (Writer writer = new FileWriter(filePath)) {
            gson.toJson(students, writer);
            System.out.println("Student data saved to JSON file.");
        } catch (IOException e) {
            System.out.println("Error saving student data.");
        }
    }

    // Load student data from JSON file
    public void loadFromJSON(String filePath) {
        Gson gson = new Gson();
        try (Reader reader = new FileReader(filePath)) {
            students = gson.fromJson(reader, new TypeToken<List<Student>>() {}.getType());
            System.out.println("Student data loaded from JSON file.");
        } catch (IOException e) {
            System.out.println("Error loading student data.");
        }
    }

    // Export student data to CSV file
    public void exportToCSV(String filePath) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write("ID,Name,RollNumber,Grade,Email\n");
            for (Student student : students) {
                writer.write(student.getId() + "," + student.getName() + "," +
                             student.getRollNumber() + "," + student.getGrade() + "," +
                             student.getEmail() + "\n");
            }
            System.out.println("Student data exported to CSV file.");
        } catch (IOException e) {
            System.out.println("Error exporting student data.");
        }
    }
}

// Console-based UI for interaction
public class StudentManagementUI {
    private static StudentManagementSystem system = new StudentManagementSystem();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        boolean exit = false;

        // Load data from JSON file (if available)
        system.loadFromJSON("students.json");

        while (!exit) {
            System.out.println("\n==== Student Management System ====");
            System.out.println("1. Add Student");
            System.out.println("2. Remove Student");
            System.out.println("3. Search Student by Roll Number");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Edit Student Details");
            System.out.println("6. Display All Students");
            System.out.println("7. Export to CSV");
            System.out.println("8. Save & Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addStudent();
                    break;
                case 2:
                    removeStudent();
                    break;
                case 3:
                    searchStudentByRollNumber();
                    break;
                case 4:
                    searchStudentByName();
                    break;
                case 5:
                    editStudent();
                    break;
                case 6:
                    system.displayAllStudents();
                    break;
                case 7:
                    system.exportToCSV("students.csv");
                    break;
                case 8:
                    system.saveToJSON("students.json");
                    exit = true;
                    System.out.println("System exiting, data saved.");
                    break;
                default:
                    System.out.println("Invalid choice, please try again.");
            }
        }
    }

    // Add a new student
    private static void addStudent() {
        System.out.print("Enter name: ");
        String name = scanner.next();
        while (!isValidName(name)) {
            System.out.print("Invalid name. Enter again: ");
            name = scanner.next();
        }

        System.out.print("Enter roll number: ");
        int rollNumber = scanner.nextInt();

        System.out.print("Enter grade: ");
        String grade = scanner.next();

        System.out.print("Enter email: ");
        String email = scanner.next();
        while (!isValidEmail(email)) {
            System.out.print("Invalid email. Enter again: ");
            email = scanner.next();
        }

        system.addStudent(new Student(name, rollNumber, grade, email));
    }

    // Remove a student
    private static void removeStudent() {
        System.out.print("Enter roll number to remove: ");
        int rollNumber = scanner.nextInt();
        system.removeStudent(rollNumber);
    }

    // Search student by roll number
    private static void searchStudentByRollNumber() {
        System.out.print("Enter roll number to search: ");
        int rollNumber = scanner.nextInt();
        Student student = system.searchStudentByRollNumber(rollNumber);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    // Search student by name
    private static void searchStudentByName() {
        System.out.print("Enter name to search: ");
        String name = scanner.next();
        Student student = system.searchStudentByName(name);
        if (student != null) {
            System.out.println(student);
        } else {
            System.out.println("Student not found.");
        }
    }

    // Edit a student's details
    private static void editStudent() {
        System.out.print("Enter roll number to edit: ");
        int rollNumber = scanner.nextInt();

        System.out.print("Enter new name: ");
        String newName = scanner.next();
        while (!isValidName(newName)) {
            System.out.print("Invalid name. Enter again: ");
            newName = scanner.next();
        }

        System.out.print("Enter new grade: ");
        String newGrade = scanner.next();

        System.out.print("Enter new email: ");
        String newEmail = scanner.next();
        while (!isValidEmail(newEmail)) {
            System.out.print("Invalid email. Enter again: ");
            newEmail = scanner.next();
        }

        system.editStudent(rollNumber, newName, newGrade, newEmail);
    }

    // Validate name (only letters allowed)
    private static boolean isValidName(String name) {
        return Pattern.matches("[a-zA-Z]+", name);
    }

    // Validate email
    private static boolean isValidEmail(String email) {
        return Pattern.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$", email);
    }
}
