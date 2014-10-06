package com.company;
/*
X1 =(0.8,0.8,1), X4 =(0.8,1.2,1), X7 =(3.8,2.8,2), X10 = (4.2, 3.2, 2), X13 = (3.2, 0.4, 3), X16 = (3.5, 1.0, 3),
X2 =(1.0,1.0,1), X5 =(1.2,1.2,1), X8 =(4.2,2.8,2), X11 = (4.4, 2.8, 2), X14 = (3.2, 0.7, 3), X17 = (4.0, 1.0, 3),
X3 = (1.2, 0.8, 1) X6 = (4.0, 3.0, 2) X9 = (3.8, 3.2, 2) X12 = (4.4, 3.2, 2) X15 = (3.8, 0.5, 3) X18 = (4.0, 0.7, 3)
*/

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static final String PATH_TO_DATA_FILE = "assets/trainData";

    public  static final int K = 4;

    public static void main(String[] args) {
	// write your code here
        KNN knn = new KNN();
        knn.loadData(PATH_TO_DATA_FILE);

        printData(knn);

        Pattern rand = new Pattern(3.0, 2.0);//knn.randomPattern();
        System.out.println("-------------");
        System.out.println(rand.getFeatureOne() + ", " + rand.getFeatureTwo());
        knn.mknn(rand, K);
        int classOfTestPattern = knn.knnClassDecision(K);
        System.out.println(classOfTestPattern);
        System.out.println("-------------");
        printData(knn);
        System.out.println("-------------");
        int classOfTestPatternMKNN = knn.knnClassDecision(K);
        System.out.println(classOfTestPatternMKNN);

    }

    public static void printData(KNN knn){
        for(PatternsDistance pd : knn.getData()){
            System.out.println(pd.getPattern().getFeatureOne() + ", " + pd.getPattern().getFeatureTwo() + ", " + pd.getPattern().getClassOfPattern() + ": " + pd.getDistance() + " :::: " + pd.getWeight());
        }
    }
}
