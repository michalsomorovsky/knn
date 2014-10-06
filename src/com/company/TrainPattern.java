package com.company;

/**
 * Created by msomorovsky on 06/10/14.
 */
public class TrainPattern extends Pattern {
    private int classOfPattern;

    public TrainPattern(double featureOne, double featureTwo, int classOfPattern){
        this.featureOne = featureOne;
        this.featureTwo = featureTwo;
        this.classOfPattern = classOfPattern;
    }

    public int getClassOfPattern(){
        return classOfPattern;
    }
}
