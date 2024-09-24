//1+ (regular, stream api)	Розділити утворений список на 2 різні списки, в першому з яких будуть тільки заочники, в другому – всі інші.
//2+ (regular, stream api)	Здійсніть групування студентів за групами.
//3+ (regular, stream api)	Створіть нову колекцію, в якій визначте для кожної оцінки по заданому предмету
//          список прізвищ студентів, які мають цей бал.
//4+ (regular, stream api)	Здійсніть сортування за середнім балом успішності.
//5+ (regular, stream api)	Вивести список всіх унікальних предметів, які студенти вивчають.
//6+ (regular, stream api)	Знайти студента з найвищим середнім балом з предмета, якщо він є, або повернути повідомлення про те,
//          що студентів немає (з використанням Optional).

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<Student> students = new LinkedList<>();
        students = ReadStudentsFromFile("C:\\Users\\nazar\\Documents\\studentsdata.txt");
        System.out.println("Data about students read from a file successfully.");

        launchApp(students);
    }

    public static void launchApp(LinkedList<Student> students) {
        Scanner scanner = new Scanner(System.in);
        Scanner scanner3 = new Scanner(System.in);
        Scanner scanner6 = new Scanner(System.in);

        while(true){
            System.out.println("What would you like to do?");
            System.out.println("1. Divide students by working/non-working");
            System.out.println("2. Divide students by groups");
            System.out.println("3. Group students by each grade for a subject");
            System.out.println("4. Sort students by the average grade");
            System.out.println("5. Get all unique subjects");
            System.out.println("6. Find the top student for a subject");
            System.out.println("7. Exit");
            System.out.println("Enter your choice: ");
            int input = 0;
            input = scanner.nextInt();
            if(input < 1 || input > 7)
                System.out.println("Please, enter a number between 1 and 7");
            else{
                if(input == 1){
                    while(true){
                        System.out.println("Choose the method implementation:");
                        System.out.println("1. Regular implementation");
                        System.out.println("2. Stream API implementation");
                        int input1 = scanner.nextInt();
                        if(input1 < 1 || input1 > 2)
                            System.out.println("Please, enter a number between 1 and 2");
                        else{
                            if(input1 == 1){
                                HashMap.SimpleEntry<LinkedList<FullTimeStudent>, LinkedList<PartTimeStudent>> result = DivideByWork(students);
                                System.out.println("Full-time students:");
                                for(FullTimeStudent s : result.getKey()){
                                    System.out.println(s.toString());
                                }
                                System.out.println("Part-time students:");
                                for(PartTimeStudent s : result.getValue()){
                                    System.out.println(s.toString());
                                }
                                break;
                            }
                            else if(input1 == 2){
                                HashMap.SimpleEntry<LinkedList<FullTimeStudent>, LinkedList<PartTimeStudent>> result = DivideByWork_Stream(students);
                                System.out.println("Full-time students:");
                                for(FullTimeStudent s : result.getKey()){
                                    System.out.println(s.toString());
                                }
                                System.out.println("Part-time students:");
                                for(PartTimeStudent s : result.getValue()){
                                    System.out.println(s.toString());
                                }
                                break;
                            }
                        }
                    }
                }
                else if(input == 2){
                    while(true){
                        System.out.println("Choose the method implementation:");
                        System.out.println("1. Regular implementation");
                        System.out.println("2. Stream API implementation");
                        int input2 = scanner.nextInt();
                        if(input2 < 1 || input2 > 2)
                            System.out.println("Please, enter a number between 1 and 2");
                        else{
                            if(input2 == 1){
                                HashMap<String, LinkedList<Student>> result = DivideByGroups(students);
                                for(String str : result.keySet()){
                                    System.out.println("Group " + str);
                                    for(Student s : result.get(str)){
                                        System.out.println("\t" + s.toString());
                                    }
                                }
                                break;
                            }
                            else if(input2 == 2){
                                HashMap<String, LinkedList<Student>> result = DivideByGroups_Stream(students);
                                for(String str : result.keySet()){
                                    System.out.println("Group " + str);
                                    for(Student s : result.get(str)){
                                        System.out.println("\t" + s.toString());
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                else if(input == 3){
                    while(true){
                        System.out.println("Choose the method implementation:");
                        System.out.println("1. Regular implementation");
                        System.out.println("2. Stream API implementation");
                        int input3 = scanner.nextInt();
                        if(input3 < 1 || input3 > 2)
                            System.out.println("Please, enter a number between 1 and 2");
                        else{
                            if(input3 == 1){
                                System.out.println("Enter a subject to group by:");
                                String subject = scanner3.nextLine();
                                HashMap<String, LinkedList<Student>> result = DivideByGrades5_Stream(students, subject);
                                System.out.println("For the subject " + subject + ":");
                                for(String grade: result.keySet()){
                                    System.out.println(grade + " got:");
                                    for(Student s: result.get(grade)){
                                        System.out.println("\t" + s.toString());
                                    }
                                }
                                break;
                            }
                            else if(input3 == 2){
                                System.out.println("Enter a subject to group by:");
                                String subject = scanner3.nextLine();
                                HashMap<Double, LinkedList<String>> result = GroupByGradeForASubject_Stream(students, subject);
                                System.out.println("For the subject " + subject + ":");
                                for(Double grade: result.keySet()){
                                    System.out.println(grade + " got:");
                                    for(String str: result.get(grade)){
                                        System.out.println("\t" + str);
                                    }
                                }
                                break;
                            }
                        }
                    }
                }
                else if(input == 4){
                    while(true){
                        System.out.println("Choose the method implementation:");
                        System.out.println("1. Regular implementation");
                        System.out.println("2. Stream API implementation");
                        int input4 = scanner.nextInt();
                        if(input4 < 1 || input4 > 2)
                            System.out.println("Please, enter a number between 1 and 2");
                        else{
                            if(input4 == 1){
                                LinkedList<Student> result = SortByAverageGrade(students);
                                for(Student s : result){
                                    System.out.println("Average grade: " + CalculateAverageGrade(s) + ": " + s.toString());
                                }
                                break;
                            }
                            else if(input4 == 2){
                                LinkedList<Student> result = SortByAverageGrade_Stream(students);
                                for(Student s : result){
                                    System.out.println("Average grade: " + CalculateAverageGrade(s) + ": " + s.toString());
                                }
                                break;
                            }
                        }
                    }
                }
                else if(input == 5){
                    while(true){
                        System.out.println("Choose the method implementation:");
                        System.out.println("1. Regular implementation");
                        System.out.println("2. Stream API implementation");
                        int input5 = scanner.nextInt();
                        if(input5 < 1 || input5 > 2)
                            System.out.println("Please, enter a number between 1 and 2");
                        else{
                            if(input5 == 1){
                                LinkedList<String> result = GetAllUniqueSubjects(students);
                                System.out.println("All unique subjects:");
                                for(String s : result){
                                    System.out.println("\t" + s);
                                }
                                break;
                            }
                            else if(input5 == 2){
                                LinkedList<String> result = GetAllUniqueSubjects_Stream(students);
                                System.out.println("All unique subjects:");
                                for(String s : result){
                                    System.out.println("\t" + s);
                                }
                                break;
                            }
                        }
                    }
                }
                else if(input == 6){
                    while(true){
                        System.out.println("Choose the method implementation:");
                        System.out.println("1. Regular implementation");
                        System.out.println("2. Stream API implementation");
                        int input6 = scanner.nextInt();
                        if(input6 < 1 || input6 > 2)
                            System.out.println("Please, enter a number between 1 and 2");
                        else{
                            if(input6 == 1){
                                System.out.println("Enter a subject to look for a top student in:");
                                String subject = scanner6.nextLine();
                                Optional<Student> result = FindTopStudentForASubject(students, subject);
                                if(result.isPresent()){
                                    System.out.println("Top student in " + subject + " is: ");
                                    System.out.println(result.get().toString());
                                    System.out.println("With the grade of " + result.get().getGrade(subject));
                                }
                                else
                                    System.out.println("Couldn't find any students for this subject.");
                                break;
                            }
                            else if(input6 == 2){
                                System.out.println("Enter a subject to look for a top student in:");
                                String subject = scanner6.nextLine();
                                Optional<Student> result = FindTopStudentForASubject_Stream(students, subject);
                                if(result.isPresent()){
                                    System.out.println("Top student in " + subject + " is: ");
                                    System.out.println(result.get().toString());
                                    System.out.println("With the grade of " + result.get().getGrade(subject));
                                }
                                else
                                    System.out.println("Couldn't find any students for this subject.");
                                break;
                            }
                        }
                    }
                }
                else if(input == 7){
                    System.out.println("Alright, goodbye!");
                    scanner.close();
                    scanner3.close();
                    scanner6.close();
                    System.exit(0);
                }
            }
        }
    }

    public static Student fromCSV(String csv){
        String[] tokens = csv.split(",");
        Student result = null;
        if(Objects.equals(tokens[0], "fulltime")){
            String firstName = tokens[1];
            String lastName = tokens[2];
            String group = tokens[3];

            HashMap<String, Double> grades = new HashMap<>();
            for (int i = 4; i < tokens.length; i++) {
                String[] gradeParts = tokens[i].split(":");
                grades.put(gradeParts[0], Double.parseDouble(gradeParts[1]));
            }
            result = new FullTimeStudent(firstName, lastName, group, grades);
        }
        else{
            String workplace = tokens[1];
            String firstName = tokens[2];
            String lastName = tokens[3];
            String group = tokens[4];

            HashMap<String, Double> grades = new HashMap<>();
            for (int i = 5; i < tokens.length; i++) {
                String[] gradeParts = tokens[i].split(":");
                grades.put(gradeParts[0], Double.parseDouble(gradeParts[1]));
            }
            result = new PartTimeStudent(firstName, lastName,group, grades, workplace);
        }

        return result;
    }

    // Метод для зчитування студентів із файлу
    public static LinkedList<Student> ReadStudentsFromFile(String filepath) throws FileNotFoundException {
        File file = new File(filepath);
        // Додати перевірку, чи існує файл
        if (!file.exists()) {
            System.out.println("File not found at: " + file.getAbsolutePath());
            throw new FileNotFoundException("File not found at: " + file.getAbsolutePath());
        }


        Scanner scanner = new Scanner(file);
        LinkedList<Student> result = new LinkedList<>();
        String lineContent = "";
        while(scanner.hasNextLine()){
            lineContent = scanner.nextLine();
            result.add(fromCSV(lineContent));
            lineContent = "";
        }

        return result;
    }

    public static HashMap.SimpleEntry<LinkedList<FullTimeStudent>, LinkedList<PartTimeStudent>>  DivideByWork(
            LinkedList<Student> list){
        LinkedList<FullTimeStudent> listFullTime = new LinkedList<FullTimeStudent>();
        LinkedList<PartTimeStudent> listPartTime = new LinkedList<PartTimeStudent>();
        for(Student student : list){
            if(student instanceof FullTimeStudent){
                listFullTime.add((FullTimeStudent)student);
            }
            else if (student instanceof PartTimeStudent){
                listPartTime.add((PartTimeStudent)student);
            }
        }

        return new HashMap.SimpleEntry<>(listFullTime, listPartTime);
    }

    public static HashMap.SimpleEntry<LinkedList<FullTimeStudent>, LinkedList<PartTimeStudent>> DivideByWork_Stream(
            LinkedList<Student> list){
        LinkedList<FullTimeStudent> listFullTime = list.stream()
                .filter(student -> student instanceof FullTimeStudent)
                .map(student -> (FullTimeStudent)student)
                .collect(Collectors.toCollection(LinkedList::new));

        LinkedList<PartTimeStudent> listPartTime = list.stream()
                .filter(student -> student instanceof PartTimeStudent)
                .map(student -> (PartTimeStudent)student)
                .collect(Collectors.toCollection(LinkedList::new));

        return new HashMap.SimpleEntry<>(listFullTime, listPartTime);
    }

    public static HashMap<String, LinkedList<Student>> DivideByGroups (LinkedList<Student> list){
        HashMap<String, LinkedList<Student>> result = new HashMap<>();
        //Кожний елемент списку студентів проходиться по карті погрупованих студентів.
        //      -якщо група ключа карти == групі студента, додаємо, якщо ні - йдемо далі.
        //      - якщо вже кінець карти, а група не знайшлася, створюємо новий елемент карти з групою студента.
        for(Student student : list){
            if(result.containsKey(student.getGroup()))
                result.get(student.getGroup()).add(student);
            else{
                LinkedList<Student> newgroup = new LinkedList<>();
                newgroup.add(student);
                result.put(student.getGroup(), newgroup);
            }
        }
        return result;
    }

    public static HashMap<String, LinkedList<Student>> DivideByGroups_Stream(LinkedList<Student> list) {
        // Використовуємо Stream API для групування студентів по групах
        Map<String, List<Student>> tempMap = list.stream()
                .collect(Collectors.groupingBy(Student::getGroup)); // Групуємо студентів

        // Конвертуємо Map в HashMap
        HashMap<String, LinkedList<Student>> result = new HashMap<>();
        tempMap.forEach((key, value) -> result.put(key, new LinkedList<Student>(value))); // Додаємо в LinkedList

        return result;
    }

    public static HashMap<Double, LinkedList<String>> GroupByGradeForASubject(
            LinkedList<Student> students, String subject) {
        HashMap<Double, LinkedList<String>> gradeToStudents = new HashMap<>();

        for (Student student : students) {
            HashMap<String, Double> grades = student.getGrades();

            if (grades.containsKey(subject)) {
                Double grade = grades.get(subject);
                //якщо для оцінки нема списку прізвищ, то створюємо
                gradeToStudents.putIfAbsent(grade, new LinkedList<>());
                gradeToStudents.get(grade).add(student.getLastName());
            }
        }

        return gradeToStudents;
    }

    public static HashMap<Double, LinkedList<String>> GroupByGradeForASubject_Stream(
            LinkedList<Student> students, String subject) {

        return students.stream()
                .filter(student -> student.getGrades().containsKey(subject))
                .collect(Collectors.groupingBy(
                        student -> student.getGrades().get(subject),
                        HashMap::new,
                        Collectors.mapping(Student::getLastName, Collectors.toCollection(LinkedList::new))
                ));
    }

    public static LinkedList<Student> SortByAverageGrade(LinkedList<Student> students) {
        LinkedList<Student> sorted = new LinkedList<>();
        for(Student student : students) {
            ListIterator<Student> iterator = sorted.listIterator();
            boolean added = false;
            while(iterator.hasNext()) {
                Student current = iterator.next();
                if (CalculateAverageGrade(student) < CalculateAverageGrade(current)){
                    iterator.previous();
                    iterator.add(student);
                    added = true;
                    break;
                }
                else continue;

            }
            if (!added) sorted.add(student);

        }
        return sorted;
    }

    public static LinkedList<Student> SortByAverageGrade_Stream(LinkedList<Student> students){
        return students.stream()
                .sorted(Comparator.comparing(Main::CalculateAverageGrade))
                .collect(Collectors.toCollection(LinkedList::new));
    }

    public static Double CalculateAverageGrade(Student student){
        Double result = 0.0;
        for(Double grade : student.getGrades().values()) {
            result += grade;
        }
        result /= student.getGrades().size();

        return result;
    }

    public static LinkedList<String> GetAllUniqueSubjects(LinkedList<Student> students) {
        LinkedList<String> subjects = new LinkedList<>();
        for(Student s : students) {
            for(String subject : s.getGrades().keySet())
                if (!subjects.contains(subject)) subjects.add(subject);
        }

        return subjects;
    }

    public static LinkedList<String> GetAllUniqueSubjects_Stream(LinkedList<Student> students){
        return students.stream()
                .map(Student::getGrades)
                .map(HashMap::keySet)
                .flatMap(Collection::stream)
                .distinct()
                .collect(Collectors.toCollection(LinkedList::new));
    } 

    public static Optional<Student> FindTopStudentForASubject(LinkedList<Student> students, String subject){
        Student topStudent = null;
        double highestGrade = -1.0;

        for(Student s : students) {
            HashMap<String, Double> grades = s.getGrades();
            if(grades.containsKey(subject)) {
                if(grades.get(subject) > highestGrade){
                    highestGrade = grades.get(subject);
                    topStudent = s;
                }
            }
        }
        return Optional.ofNullable(topStudent);
    }

    public static Optional<Student> FindTopStudentForASubject_Stream(LinkedList<Student> students, String subject) {
        return students.stream()
                .filter(student -> student.getGrades().containsKey(subject))
                .max(Comparator.comparingDouble(s -> s.getGrades().get(subject)));
    }

    public static HashMap<String, LinkedList<Student>> DivideByGrades5(LinkedList<Student> students, String subject) {
        HashMap<String, LinkedList<Student>> result = new HashMap<>();
        result.putIfAbsent("5", new LinkedList<>());
        result.putIfAbsent("4", new LinkedList<>());
        result.putIfAbsent("3", new LinkedList<>());

        for(Student s : students){
            HashMap<String, Double> grades = s.getGrades();

            if (grades.containsKey(subject)) {
                Double grade = grades.get(subject);

                if(grade >= 50.0 && grade < 71.0){
                    result.get("3").add(s);
                }
                else if(grade >= 71.0 && grade < 88.0){
                    result.get("4").add(s);
                }
                else if(grade >= 88.0){
                    result.get("5").add(s);
                }
            }
        }

        return result;
    }

    public static HashMap<String, LinkedList<Student>> DivideByGrades5_Stream(LinkedList<Student> students, String subject) {
        HashMap<String, LinkedList<Student>> result = new HashMap<>();
        result.putIfAbsent("5", new LinkedList<>());
        result.putIfAbsent("4", new LinkedList<>());
        result.putIfAbsent("3", new LinkedList<>());

        students.stream()
                .filter(s-> s.getGrades().containsKey(subject))
                .forEach(s -> {
                    double grade = s.getGrades().get(subject);
                    String key = (grade >= 88.0) ? "5" :
                            (grade >= 71.0) ? "4" :
                            (grade >= 50.0) ? "3" : null;
                    if(key != null) result.get(key).add(s);
                });

        return result;
    }

}