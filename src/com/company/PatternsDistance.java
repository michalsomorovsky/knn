package com.company;

/**
 * Created by msomorovsky on 06/10/14.
 */
public class PatternsDistance {
    TrainPattern dataSetPattern;
    double distance;
    double weight;

    public PatternsDistance(TrainPattern dataSetPattern){
        this.dataSetPattern = dataSetPattern;
    }

    public void setDistance(double distance){
        this.distance = distance;
    }

    public TrainPattern getPattern(){
        return dataSetPattern;
    }

    public double getDistance(){
        return distance;
    }

    public double getWeight(){
        return weight;
    }

    public void setWeight(double weight){
        this.weight = weight;
    }
}
