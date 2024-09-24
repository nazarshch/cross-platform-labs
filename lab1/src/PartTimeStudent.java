import java.util.HashMap;

public class PartTimeStudent extends Student{
    private String workplace;

    public PartTimeStudent(String firstname, String lastname, String group, HashMap<String, Double> grades, String workplace) {
        super(firstname, lastname, group, grades);
        this.workplace = workplace;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    @Override
    public String toCSV(){
        StringBuilder sb = new StringBuilder();
        sb.append("parttime,");
        sb.append(this.workplace).append(",");
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
        sb.append("PartTimeStudent:");
        sb.append(this.getFirstName()).append(",");
        sb.append(this.getLastName()).append(",");
        sb.append(this.getGroup()).append(",");
        HashMap<String, Double> grades = this.getGrades();
        sb.append("grades:");
        for (String subject : grades.keySet()) {
            sb.append(",").append(subject).append(":").append(grades.get(subject));
        }
        sb.append(", workplace:").append(workplace);
        return sb.toString();
    }
}
