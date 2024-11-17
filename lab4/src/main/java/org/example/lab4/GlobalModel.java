package org.example.lab4;

public class GlobalModel {

    private double aggregatedResult;

    public GlobalModel(){
        aggregatedResult = 0.0;
    }
    public double getAggregatedResult() {
        return aggregatedResult;
    }

    public void updateAggregatedResult(double resultUpdate) {
        aggregatedResult += resultUpdate;
    }

}
