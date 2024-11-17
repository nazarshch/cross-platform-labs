package org.example.lab4;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class ThreadData {

    private final SimpleStringProperty threadName;
    private final SimpleStringProperty status;
    private final SimpleDoubleProperty result;

    public ThreadData(String threadName, String status) {
        this.threadName = new SimpleStringProperty(threadName);
        this.status = new SimpleStringProperty(status);
        this.result = new SimpleDoubleProperty(0.0);
    }

    public String getThreadName() {
        return threadName.get();
    }

    public void setThreadName(String threadName) {
        this.threadName.set(threadName);
    }

    public SimpleStringProperty threadNameProperty() {
        return threadName;
    }

    public String getStatus() {
        return status.get();
    }

    public SimpleStringProperty statusProperty() {
        return status;
    }

    public void setStatus(String status) {
        this.status.set(status);
    }

    public double getResult() {
        return result.get();
    }

    public SimpleDoubleProperty resultProperty() {
        return result;
    }

    public void setResult(double result) {
        this.result.set(result);
    }
}
