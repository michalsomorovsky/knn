package com.company;

/**
 * Created by msomorovsky on 06/10/14.
 */
public class Pattern {
    protected double featureOne;
    protected double featureTwo;

    public Pattern(){
        this.featureOne = 0.0;
        this.featureTwo = 0.0;
    }

    public Pattern(double featureOne, double featureTwo){
        this.featureOne = featureOne;
        this.featureTwo = featureTwo;
    }

    public double getFeatureOne(){
        return featureOne;
    }

    public double getFeatureTwo(){
        return featureTwo;
    }

}
