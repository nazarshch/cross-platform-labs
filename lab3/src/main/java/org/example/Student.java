package org.example;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Student implements Serializable {

    private String fullName;
    private transient int roomNumber;
    private int accomodationFee;
    
    public Student(String fullName, int roomNumber, int accomodationFee) {
        this.fullName = fullName;
        this.roomNumber = roomNumber;
        this.accomodationFee = accomodationFee;
    }

    public Student(){
        new Student("", 0, 0);
    }

    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public int getAccomodationFee() {
        return accomodationFee;
    }
    public void setAccomodationFee(int accomodationFee) {
        this.accomodationFee = accomodationFee;
    }

    private final int STANDARD_ACCOMODATION_FEE = 4000;
    // конвертація в мапу з умовою для плати проживання
    public Map<String, Object> toYamlMap() {
        Map<String, Object> map = new HashMap<>();
        map.put("fullName", fullName);
        // номер кімнати не серіалізується за умовою завдання
        if (!(accomodationFee < STANDARD_ACCOMODATION_FEE)) {
            map.put("accomodationFee", accomodationFee);
        }
        return map;
    }

    @Override
    public String toString() {
        return "Student{" +
                "fullName='" + fullName + '\'' +
                ", roomNumber=" + roomNumber +
                ", accomodationFee=" + accomodationFee +
                '}';
    }
}
