import java.util.HashMap;

public class FullTimeStudent extends Student{
    public FullTimeStudent(String firstname, String lastname, String group, HashMap<String, Double> grades) {
        super(firstname, lastname, group, grades);
    }

    @Override
    public String toCSV(){
        StringBuilder sb = new StringBuilder();
        sb.append("fulltime,");
        sb.append(this.getFirstName()).append(",");
        sb.append(this.getLastName()).append(",");
        sb.append(this.getGroup()).append(",");

        HashMap<String, Double> grades = this.getGrades();
        for (String subject : grades.keySet()) {
            sb.append(",").append(subject).append(":").append(grades.get(subject));
        }

        return sb.toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("FullTimeStudent:");
        sb.append(this.getFirstName()).append(",");
        sb.append(this.getLastName()).append(",");
        sb.append(this.getGroup()).append(",");
        HashMap<String, Double> grades = this.getGrades();
        sb.append("grades:");
        for (String subject : grades.keySet()) {
            sb.append(",").append(subject).append(":").append(grades.get(subject));
        }
        return sb.toString();
    }
}

