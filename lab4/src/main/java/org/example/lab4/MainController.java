package org.example.lab4;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.UnaryOperator;

public class MainController {

    @FXML
    private Text totalResultText;

    @FXML
    private TextField threadsNumberTextField;

    // форматтер для обмеження введення тільки чисел
    UnaryOperator<TextFormatter.Change> filter = change -> {
        String text = change.getText();
        if (text.matches("[0-9]*")) {
            return change;
        }
        return null;
    };

    @FXML
    private TableView<ThreadData> threadsTableView;
    @FXML
    private TableColumn<ThreadData, String> nameColumn;
    @FXML
    private TableColumn<ThreadData, String> statusColumn;
    @FXML
    private TableColumn<ThreadData, Double> resultColumn;

    public void initialize(){
        //задаємо форматтер на текстове поле
        TextFormatter<String> textFormatter = new TextFormatter<>(filter);
        threadsNumberTextField.setTextFormatter(textFormatter);

        nameColumn.setCellValueFactory(cellData -> cellData.getValue().threadNameProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().statusProperty());
        resultColumn.setCellValueFactory(cellData -> cellData.getValue().resultProperty().asObject());
    }

    @FXML
    private void startThreads(ActionEvent event) {
        String threadsNumberString = threadsNumberTextField.getText();
        if (threadsNumberString == null || threadsNumberString.trim().isEmpty()) {
            return;
        }

        int threadsNumber = Integer.parseInt(threadsNumberString);
        ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);

        ObservableList<ThreadData> threadDataList = FXCollections.observableArrayList();
        threadsTableView.setItems(threadDataList);

        GlobalModel model = new GlobalModel();
        ArrayList<Future<Double>> futures = new ArrayList<>();

        // Колекція для накопичення результатів
        List<Double> results = Collections.synchronizedList(new ArrayList<>());

        for (int i = 0; i < threadsNumber; i++) {

            ThreadData threadData = new ThreadData("...", "Creating...");
            threadDataList.add(threadData);

            MachineLearningSimulator simulator = new MachineLearningSimulator(BatchGenerator.GenerateBatch());

            Future<Double> future = executor.submit(() -> {
                String threadName = Thread.currentThread().getName();
                // Оновлення статусу та імені потоку при створенні
                Platform.runLater(() -> {
                    threadData.setThreadName(threadName);
                    threadData.setStatus(Thread.currentThread().getState().toString());
                });

                Double result = simulator.call(); // Симуляція "навчання"

                // Оновлення статусу та результату потоку після завершення
                Platform.runLater(() -> {
                    threadData.setResult(result);
                    threadData.setStatus(Thread.currentThread().getState().toString());
                });

                synchronized (results) {
                    results.add(result);
                }

                return result;
            });
            futures.add(future);
        }

        // окремий потік для підрахунку результатів, щоб не блокувати основний потік
        new Thread(() -> {
            try {
                for (Future<Double> future : futures) {
                    future.get();
                }
                double totalSum;
                synchronized (results) {
                    totalSum = results.stream().mapToDouble(Double::doubleValue).sum();
                }
                model.updateAggregatedResult(totalSum);

                Platform.runLater(() -> {
                    System.out.println("Total result: " + model.getAggregatedResult());
                    totalResultText.setText("Total result: " + model.getAggregatedResult());
                });
            } catch (Exception e) {
                System.out.println(e.getMessage());
            } finally {
                executor.shutdown();
            }
        }).start();
    }


}