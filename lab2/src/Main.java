import com.sun.security.jgss.GSSUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static void main(String[] args) {

        LinkedList<City> cities = new LinkedList<>();
        cities.add(new City("Kyiv", 50.4501, 30.5234, ZoneId.of("GMT+3")));
        cities.add(new City("London", 51.5074, -0.1278, ZoneId.of("GMT+1")));
        cities.add(new City("New York", 40.7128, -74.0060, ZoneId.of("GMT-4")));
        cities.add(new City("Tokyo", 35.6762, 139.6503, ZoneId.of("GMT+9")));
        cities.add(new City("Sydney", -33.8688, 151.2093, ZoneId.of("GMT+11")));
        cities.add(new City("Paris", 48.8566, 2.3522, ZoneId.of("GMT+2")));
        cities.add(new City("Berlin", 52.5200, 13.4050, ZoneId.of("GMT+2")));
        cities.add(new City("Dubai", 25.2048, 55.2708, ZoneId.of("GMT+4")));
        cities.add(new City("Hong Kong", 22.3193, 114.1694, ZoneId.of("GMT+8")));
        cities.add(new City("Rio de Janeiro", -22.9068, -43.1729, ZoneId.of("GMT-3")));
        cities.add(new City("Mexico City", 19.4326, -99.1332, ZoneId.of("GMT-5")));
        cities.add(new City("Toronto", 43.651070, -79.347015, ZoneId.of("GMT-4")));
        cities.add(new City("Los Angeles", 34.0522, -118.2437, ZoneId.of("GMT-7")));
        cities.add(new City("Warsaw", 52.2297, 21.0122, ZoneId.of("GMT+2")));
        cities.add(new City("Seoul", 37.5665, 126.9780, ZoneId.of("GMT+9")));

        LinkedList<TravelMeans> means = new LinkedList<>();
        means.add(new TravelMeans("Car", 20, 150));
        means.add(new TravelMeans("Train", 10, 500));
        means.add(new TravelMeans("Plane", 100, 2000));

        while(true){
            System.out.println("What would you like to do?");
            System.out.println(" 1. Open the travel planner app");
            System.out.println(" 2. Detect a sentence with (())+");
            System.out.println(" 3. Exit");
            int input = 0;
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextInt();
            if(input < 1 || input > 3)
                System.out.println("Please enter a number between 1 and 3");
            else{
                if(input == 1){
                    System.out.println("Here's the list of cities we travel to:");
                    LinkedList<City> chosencities = new LinkedList<>();
                    Scanner cityScanner = new Scanner(System.in);
                    for(City city : cities){
                        System.out.println(" -" + city.toString());
                    }
                    System.out.println("Choose the city of departure:");
                    while(true){
                        String cityName = cityScanner.nextLine();
                        boolean cityFound = false;
                        for(City c: cities){
                            if (c.getName().equalsIgnoreCase(cityName)) { // порівняння без врахування регістру
                                chosencities.add(c);
                                cityFound = true;
                                break;
                            }
                        }
                        if(cityFound) break;
                        else System.out.println("Please enter a valid city name");
                    }
                    while(true){
                        System.out.println("Enter the next arrival city or the - sign to continue: ");
                        boolean isContinuePressed = false;
                        while(true){
                            String cityName = cityScanner.nextLine();
                            if(Objects.equals(cityName, "-")){
                                isContinuePressed = true;
                                break;
                            }
                            boolean cityFound = false;
                            for(City c: cities){
                                if (c.getName().equalsIgnoreCase(cityName)) { // порівняння без врахування регістру
                                    chosencities.add(c);
                                    cityFound = true;
                                    break;
                                }
                            }
                            if(cityFound) break;
                            else System.out.println("Please enter a valid city name");
                        }
                        if(isContinuePressed) break;
                    }

                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                    LocalDateTime dateTime = null;
                    boolean validInput = false;
                    System.out.println("Enter the date and time you'd like to depart (in the timezone of the first city, in the yyyy-MM-dd HH:mm format):");
                    while (!validInput) {
                        String timeInput = scanner.nextLine();
                        timeInput = scanner.nextLine();
                        try {
                            dateTime = LocalDateTime.parse(timeInput, formatter);
                            validInput = true;
                        } catch (DateTimeParseException e) {
                            System.out.println("Wrong format, try again please");
                        }
                    }
                    System.out.println("Are you limited by time by any chance? (y/n)");
                    boolean tooLowOnTime = false;
                    while(true){
                        String response = scanner.nextLine();
                        if(Objects.equals(response, "n")){
                            System.out.println("Alright, let's continue then!");
                            break;
                        }
                        else if(Objects.equals(response, "y")){
                            System.out.println("Alright, how much time do you have (in hours)?");
                            int timeInput = scanner.nextInt();
                            double fullDistance = 0.0;
                            for(int i = 1; i < chosencities.size(); i++)
                                fullDistance += calculateDistance(chosencities.get(i - 1), chosencities.get(i));
                            if(fullDistance / timeInput > 2000.0){
                                System.out.println("Sorry, the fastest we can do is 2000km/h and it's going to be" + (int) fullDistance/2000 + " hours");
                                tooLowOnTime = true;
                                break;
                            }
                            else{
                                System.out.println("Alright, you're going to be travelling by plane at the speed of"
                                        + (int)fullDistance/timeInput + " km/h. Have a nice trip!");
                                OutputTravel(chosencities, dateTime, formatter, (int)fullDistance/timeInput);
                                tooLowOnTime = true;
                                break;
                            }
                        }
                        else System.out.println("Enter 'y' or 'n' for yes or no.");
                    }
                    if(tooLowOnTime){
                        continue;
                    }
                    System.out.println("Choose how you want to travel: ");
                    for(TravelMeans t: means){
                        System.out.println(t.toString());
                    }
                    TravelMeans chosenmeans = null;
                    while(true){
                        String meansName = cityScanner.nextLine();
                        boolean meansFound = false;
                        for(TravelMeans t: means){
                            if (t.getName().equalsIgnoreCase(meansName)) { // порівняння без врахування регістру
                                chosenmeans = t;
                                meansFound = true;
                                break;
                            }
                        }
                        if(meansFound) break;
                        else System.out.println("Please enter a valid travel means name");
                    }

                    System.out.println("Enter speed you'd like to go at: " + chosenmeans.getLowestSpeed()+ "km/h" + " - " + chosenmeans.getHighestSpeed() + "km/h");
                    int speed = 0;
                    while(true){
                        speed = scanner.nextInt();
                        if(speed < chosenmeans.getLowestSpeed() || speed > chosenmeans.getHighestSpeed()){
                            System.out.println("Enter the speed between " + chosenmeans.getLowestSpeed() + "km/h" + " - " + chosenmeans.getHighestSpeed() + "km/h");
                            continue;
                        }
                        break;
                    }

                    OutputTravel(chosencities, dateTime, formatter, speed);
                }
                else if(input == 2){
                    File file = null;
                    Scanner fileScanner = null;
                    try{
                        file = new File("C:\\Users\\nazar\\Documents\\sentences.txt");
                        fileScanner = new Scanner(file);
                        if (!file.exists()) {
                            System.out.println("File not found at: " + file.getAbsolutePath());
                            throw new FileNotFoundException("File not found at: " + file.getAbsolutePath());
                        }
                    }
                    catch(FileNotFoundException e){
                        System.out.println("File not found at: " + file.getAbsolutePath());
                        break;
                    }
                    StringBuilder builder = new StringBuilder();
                    while(fileScanner.hasNextLine()){
                        builder.append(fileScanner.nextLine());
                    }
                    String fileContents = builder.toString();
                    LinkedList<String> sentences = GetSentencesWithBrackets(fileContents);
                    //список, в який запишуться речення, в яких є дужки, вкладені на більше ніж 1 рівень:
                    System.out.println("Data read from the file successfully.");
                    for(String str : sentences){
                        System.out.println(str);
                    }

                }
                else {
                    System.out.println("Alright, goodbye!");
                    scanner.close();
                    System.exit(0);
                }
            }
        }


    }

    public static void OutputTravel(LinkedList<City> chosencities, LocalDateTime dateTime, DateTimeFormatter formatter, int speed){
        double totalTravelTime = 0.0;
        double totalDistance = 0.0;
        ZonedDateTime zonedTime = dateTime.atZone(chosencities.get(0).getTimeZone());
        for(int i = 1; i < chosencities.size(); i++){
            System.out.println("Trip from " + chosencities.get(i-1).getName() + " to " + chosencities.get(i).getName() + ": ");
            System.out.println("- departure: (" + chosencities.get(i-1).getName() + "'s time): " + zonedTime.format(formatter));
            double timeTaken = calculateTime(chosencities.get(i-1), chosencities.get(i), speed);
            double distanceTravelled = calculateDistance(chosencities.get(i-1), chosencities.get(i));
            int hours = (int) timeTaken;
            int minutes = (int) ((timeTaken - hours) * 60);
            zonedTime = zonedTime.plusHours(hours).plusMinutes(minutes);
            zonedTime = zonedTime.withZoneSameInstant(chosencities.get(i).getTimeZone());
            System.out.println("- arrival: (" + chosencities.get(i).getName() + "'s time): " + zonedTime.format(formatter));
            System.out.println("- time taken: " + hours + " hours and " + minutes + " minutes");
            System.out.println("- distance travelled: " + String.format("%.2f", distanceTravelled) + " km.");
            totalTravelTime += timeTaken;
            totalDistance += distanceTravelled;
        }

        int totalHours = (int) totalTravelTime;
        int totalMinutes = (int) ((totalTravelTime - (double)totalHours) * 60);
        System.out.println("Total time taken for the trip: " + totalHours + " hours and " + totalMinutes + " minutes");
        System.out.println("Total distance travelled: " + String.format("%.2f", totalDistance) + " km.");
    }

    //формула гаверсинуса
    public static double calculateDistance(City city1, City city2) {
        final int EARTH_RADIUS = 6378;

        double latDistance = Math.toRadians(city2.getLatitude() - city1.getLatitude());
        double lonDistance = Math.toRadians(city2.getLongitude() - city1.getLongitude());

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2) +
                Math.cos(Math.toRadians(city1.getLatitude())) * Math.cos(Math.toRadians(city2.getLatitude())) *
                        Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    // швидкість - км/год, результат - в годинах
    public static double calculateTime(City city1, City city2, int speed) {
        double distance = calculateDistance(city1, city2);
        return distance / (double)speed;
    }

    public static LinkedList<String> GetSentencesWithBrackets(String text){
        LinkedList<String> result = new LinkedList<String>();

        Pattern sentencePattern = Pattern.compile("[^.!?]*([(]+[^.!?]*){2,}([)]+[^.!?]*){2,}[.!?]");
        //[^.!?]*([(]{2,}+[\d]{2}[-][\d]{2}[-][\d]{4})+[)]{2,}[^.!?]*[.!?] - пошук дати типу ММ-ДД-РРРР у вкладених дужках
        Matcher matcher = sentencePattern.matcher(text);

        while(matcher.find()){
            result.add(matcher.group().trim());
        }

        return result;
    }
}

