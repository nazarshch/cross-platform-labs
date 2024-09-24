//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.HashMap;

public abstract class Student {
    private String firstname;
    private String lastname;
    private String group;
    private HashMap<String, Double> grades;

    public Student(String firstname, String lastname, String group, HashMap<String, Double> grades) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.group = group;
        this.grades = grades;
    }

    public String getFirstName() {
        return firstname;
    }

    public void setFirstName(String firstname) {
        this.firstname = firstname;
    }

    public String getLastName() {
        return lastname;
    }

    public void setLastName(String lastname) {
        this.lastname = lastname;
    }

    public String getGroup() {
        return group;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public HashMap<String, Double> getGrades() {
        return grades;
    }

    public void setGrades(HashMap<String, Double> grades) {
        this.grades = grades;
    }

    public String toCSV() {
        return "base";
    }

    public Double getGrade(String subject){
        return grades.get(subject);
    }
}

