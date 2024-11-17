package org.example.lab4;

import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

public class MachineLearningSimulator implements Callable<Double> {

    private final ArrayList<Double> _batchData;
    private double result;

    public MachineLearningSimulator(ArrayList<Double> batchData) {
        this._batchData = batchData;
        result = 0.0;
    }

    @Override
    public Double call(){
        try{
            String listContent = "";
            for(Double d: _batchData){ listContent += d + ", ";}

            this.result = _batchData.stream().mapToDouble(Double::doubleValue).sum();
            TimeUnit.SECONDS.sleep(2);  // - для симуляції навчання моделі

            System.out.println("List: " + listContent + ", sum result: " + this.result);
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
        return this.result;
    }
}
