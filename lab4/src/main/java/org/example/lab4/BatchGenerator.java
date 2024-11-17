package org.example.lab4;

import java.util.ArrayList;

public class BatchGenerator {
    public static ArrayList<Double> GenerateBatch(){
        ArrayList<Double> batch = new ArrayList<>();

        for(int i = 0; i < 20; i++){
            batch.add((long)(((Math.random() * 100) + 1) * 1000) / 1000.0);
            //(long)(number * 1000) / 1000.0
        }

        return batch;
    }
}
