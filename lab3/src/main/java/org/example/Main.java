package org.example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.LoaderOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;
import org.yaml.snakeyaml.inspector.TagInspector;

import java.io.*;
import java.lang.reflect.Type;
import java.util.*;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        LinkedList<Student> students = new LinkedList<Student>();
        students.add(new Student("Anne", 23, 4000));
        students.add(new Student("John", 49, 2500));
        students.add(new Student("Bob", 102, 4000));
        students.add(new Student("Dan", 66, 3000));


        int choice = 0;
        Scanner scanner = new Scanner(System.in);
        while(true){
            System.out.println("What would you like the program to do?");
            System.out.println(" 1. I/O students to/from a file ");
            System.out.println(" 2. Serialize students (Native)");
            System.out.println(" 3. Serialize students (JSON - Gson)");
            System.out.println(" 4. Serialize students (YAML - SnakeYaml)");
            System.out.println(" 5. Exit");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    for(Student student : students){
                        writeStudentToFile(student, "studentsIO.txt");
                    }
                    System.out.println("Data about students is wrote into studentsIO.txt");
                    System.out.println("Retrieving data from the file back...");

                    LinkedList<Student> studentsFromIO = readStudentsFromFile("studentsIO.txt");
                    System.out.println("Retrieved data:");
                    for(Student s : studentsFromIO){
                        System.out.println(s.toString());
                    }
                    break;
                case 2:
                    System.out.println("Serializing students using Java's Native serialization...");
                    serializeStudentsNative(students, "studentsSerializedNative.txt");
                    System.out.println("Students are now serialized in studentsSerializedNative.txt");
                    System.out.println("Retrieving data from the file back...");

                    LinkedList<Student> studentsFromNative = deserializeStudentsNative("studentsSerializedNative.txt");
                    System.out.println("Retrieved data:");
                    for(Student s : studentsFromNative){
                        System.out.println(s.toString());
                    }
                    break;
                case 3:
                    System.out.println("Serializing students into a JSON file using Gson...");
                    serializeStudentsGson(students, "studentsSerializedGson.json");
                    System.out.println("Students are now serialized in studentsSerializedGson.json");
                    System.out.println("Retrieving data from the file back...");

                    LinkedList<Student> studentsFromGson = deserializeStudentsGson("studentsSerializedGson.json");
                    System.out.println("Retrieved data:");
                    for(Student s : studentsFromGson){
                        System.out.println(s.toString());
                    }
                    break;
                case 4:
                    System.out.println("Serializing students into a YAML file using SnakeYaml...");
                    serializeToYaml(students, "studentsSerializedYAML.yml");
                    System.out.println("Students are now serialized in studentsSerializedYAML.yml");
                    System.out.println("Retrieving data from the file back...");

                    LinkedList<Student> studentsFromYaml = deserializeFromYaml("studentsSerializedYAML.yml");
                    System.out.println("Retrieved data:");
                    for(Student s : studentsFromYaml){
                        System.out.println(s.toString());
                    }
                    break;
                case 5:
                    System.out.println("Alright, goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Please enter a number between 1 and 5");
            }
        }
    }

    // Запис інформації про студентів у файл
    public static void writeStudentToFile(Student student, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName, true);
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(fileName, true));

            fos.write(student.getFullName().getBytes());
            fos.write(",".getBytes());
            fos.write(String.valueOf(student.getRoomNumber()).getBytes()); // небуферизований - для кімнати
            fos.write(",".getBytes());
            fos.flush();
            fos.close();

            bos.write(String.valueOf(student.getAccomodationFee()).getBytes()); // буферизований - для оплати за проживання
            bos.write("\n".getBytes());
            bos.flush();
            bos.close();
        }
        catch(IOException e){
            System.out.println("exception caught while writing into the file: " + e.getMessage());
        }
    }

    // Читання інформації про студентів з файлу
    public static LinkedList<Student> readStudentsFromFile(String fileName) {
        try{
            FileInputStream fis = new FileInputStream(fileName);
            BufferedInputStream bis;
            LinkedList<Student> result = new LinkedList<>();
            int i = fis.read();
            while(i != -1){

                String studentName = "";
                String studentRoomNumber = "";
                String studentAccomodationFee = "";

                //читання імені студента, ascii коди: ',' = 44, '\n' = 10, EOF = -1
                while(i != 44 && i != 10 && i != -1){
                    studentName += (char)i;
                    i = fis.read();
                }
                i = fis.read();
                //читання номера кімнати студента
                while(i != 44 && i != 10 && i != -1){
                    studentRoomNumber += (char)i;
                    i = fis.read();
                }
                i = fis.read();
                //читання оплати за проживання студента
                bis = new BufferedInputStream(fis);
                while(i != 44 && i != 10 && i != -1){
                    studentAccomodationFee += (char)i;
                    i = bis.read();
                }
                //позиція fis зміщується на кінець файлу, бо BufferedInputStream може так читати до самого кінця ...?
                //з буфера повертаємо, скільки залишилося, і рахуємо позицію:
                fis.getChannel().position(fis.getChannel().position() - bis.available());
                result.add(new Student(studentName, Integer.parseInt(studentRoomNumber), Integer.parseInt(studentAccomodationFee)));

                i = fis.read();
            }
            return result;
        }
        catch(IOException e){
            System.out.println("exception caught while reading from the file: " + e.getMessage());
            return null;
        }
    }

    // Нативна серіалізація Java
    public static void serializeStudentsNative(LinkedList<Student> students, String fileName) {
        try {
            FileOutputStream fos = new FileOutputStream(fileName);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            for (Student student : students) {
                if (student.getRoomNumber() <= 100)
                    oos.writeObject(student);
            }

        } catch (IOException e) {
            System.out.println("exception caught while serializing students (native): " + e.getMessage());
        }
    }

    // Нативна десеріалізація Java
    public static LinkedList<Student> deserializeStudentsNative(String fileName) {
        LinkedList<Student> students = new LinkedList<>();

        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);

            while (true) {
                try {
                    Student student = (Student) ois.readObject();
                    students.add(student);
                } catch (IOException | ClassNotFoundException e) {
                    // Кінець файлу викличе EOFException, тоді виходимо з циклу
                    break;
                }
            }

        } catch (IOException e) {
            System.out.println("exception caught in native deserialization: " + e.getMessage());
        }

        return students;
    }

    //серіалізація через Gson
    public static void serializeStudentsGson(LinkedList<Student> students, String fileName) {
        Gson gson = new Gson();
        try (FileWriter writer = new FileWriter(fileName)) {
            // Серіалізуємо об'єкт студента у JSON формат
            gson.toJson(students, writer);
        } catch (IOException e) {
            System.out.println("exception caught in gson serializing: " + e.getMessage());
        }
    }

    // десеріалізація через Gson
    public static LinkedList<Student> deserializeStudentsGson(String fileName) {
        Gson gson = new Gson();
        LinkedList<Student> students = null;

        Type studentListType = new TypeToken<LinkedList<Student>>(){}.getType();
        try (FileReader reader = new FileReader(fileName)) {
            students = gson.fromJson(reader, studentListType);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return students;
    }


    //(де)серіалізація yaml
    public static void serializeToYaml(LinkedList<Student> students, String filePath) {
        DumperOptions options = new DumperOptions();
        options.setIndent(2);
        options.setPrettyFlow(true);
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

        Yaml yaml = new Yaml(options);

        List<Map<String, Object>> studentsMaps = students.stream()
                .map(Student::toYamlMap)
                .toList();

        try {
            Writer writer = new FileWriter(filePath);
            yaml.dump(studentsMaps, writer);

        } catch (IOException e) {
            System.out.println("Exception caught in serializeToYaml: " + e.getMessage());
        }
    }

    public static LinkedList<Student> deserializeFromYaml(String filePath) {
        var loaderOptions = new LoaderOptions();
        TagInspector tagInspector = tag -> tag.getClassName().equals(Student.class.getName());
        loaderOptions.setTagInspector(tagInspector);

        Yaml yaml = new Yaml(new Constructor(List.class, loaderOptions));

        try {
            Reader reader = new FileReader(filePath);
            LinkedList<HashMap<String, Object>> input = new LinkedList<HashMap<String, Object>>(yaml.load(reader));
            LinkedList<Student> result = new LinkedList<>();
            for(HashMap<String, Object> studentObj : input){
                String fullName = (String) studentObj.getOrDefault("fullName", "");
                int roomNumber = (int) studentObj.getOrDefault("roomNumber", 0);
                int accomodationFee = (int) studentObj.getOrDefault("accomodationFee", 0);
                result.add(new Student(fullName, roomNumber, accomodationFee));
            }

            return result;
        } catch (IOException e) {
            System.out.println("Exception caught in deserializeFromYaml: " + e.getMessage());
        }

        return null;
    }
}

