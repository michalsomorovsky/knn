package com.company;
/*
X1 =(0.8,0.8,1), X4 =(0.8,1.2,1), X7 =(3.8,2.8,2), X10 = (4.2, 3.2, 2), X13 = (3.2, 0.4, 3), X16 = (3.5, 1.0, 3),
X2 =(1.0,1.0,1), X5 =(1.2,1.2,1), X8 =(4.2,2.8,2), X11 = (4.4, 2.8, 2), X14 = (3.2, 0.7, 3), X17 = (4.0, 1.0, 3),
X3 = (1.2, 0.8, 1) X6 = (4.0, 3.0, 2) X9 = (3.8, 3.2, 2) X12 = (4.4, 3.2, 2) X15 = (3.8, 0.5, 3) X18 = (4.0, 0.7, 3)
*/

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.FastScatterPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class Main {

    public static final String PATH_TO_DATA_FILE = "assets/trainData";
    public static final String PATH_TO_IMAGE = "images/image";

    public  static final int K = 5;
    public static final boolean mknn = true;

    public static void main(String[] args) {
	// write your code here
        KNN knn = new KNN();
        knn.loadData(PATH_TO_DATA_FILE);
        computeOnePattern(knn, new Pattern(4.2, 1.8), mknn);
        //computeWholeGraph(knn, mknn);


    }

    public static void printData(KNN knn){
        for(PatternsDistance pd : knn.getData()){
            System.out.println(pd.getPattern().getFeatureOne() + ", " + pd.getPattern().getFeatureTwo() + ", " + pd.getPattern().getClassOfPattern() + ": " + pd.getDistance() + " :::: " + pd.getWeight());
        }
    }

    public static int compute(KNN knn, Pattern rand, boolean mknn){
        //printData(knn);
        //Pattern rand = new Pattern(3.0, 2.0);//knn.randomPattern();
        //System.out.println("-------------");
        //System.out.println("RANDOM PATTERN:");
        //System.out.println(rand.getFeatureOne() + ", " + rand.getFeatureTwo());
        knn.mknn(rand, K);
        //int classOfTestPattern = knn.knnClassDecision(K);
        //System.out.println("KNN CLASS:");
        //System.out.println(classOfTestPattern);
        //System.out.println("-------------");
        //printData(knn);
        //System.out.println("-------------");
        //int classOfTestPatternMKNN = knn.knnClassDecision(K);
        //System.out.println("MKNN CLASS:");
        //System.out.println(classOfTestPatternMKNN);
        return mknn ? knn.mknnClassDecision(K) : knn.knnClassDecision(K);
    }

    public static void computeWholeGraph(KNN knn, Boolean mknn){
        XYSeries class1 = new XYSeries("Class 1");
        XYSeries class2 = new XYSeries("Class 2");
        XYSeries class3 = new XYSeries("Class 3");


        for(double i=0.0; i<6.0; i+=0.01){
            for(double j=0.0; j<6.0; j+=0.01){
                switch(compute(knn, new Pattern(j, i), mknn)){
                    case 1:
                        class1.add(j, i);
                        break;
                    case 2:
                        class2.add(j, i);
                        break;
                    case 3:
                        class3.add(j, i);
                        break;
                }
            }
        }
        XYSeriesCollection xydata = new XYSeriesCollection();
        xydata.addSeries(class1);
        xydata.addSeries(class2);
        xydata.addSeries(class3);

        JFreeChart chart = ChartFactory.createScatterPlot("KNN", "X feature", "Y feature", xydata);
        //ChartPanel chartPanel = new ChartPanel(chart);

        OutputStream out = null;
        try {
            out = new FileOutputStream(PATH_TO_IMAGE + "_" + (mknn ? "MkNN_" : "kNN_") + K + ".png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedImage chartImage = chart.createBufferedImage(500, 500);
        try {
            ImageIO.write(chartImage, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        /*final ChartDemo chd = new ChartDemo("DEMO", chartPanel);
        chd.pack();
        chd.setVisible(true);*/
    }

    public static void computeOnePattern(KNN knn, Pattern pattern, boolean mknn){
        XYSeries class1 = new XYSeries("Class 1");
        XYSeries class2 = new XYSeries("Class 2");
        XYSeries class3 = new XYSeries("Class 3");

        ArrayList<PatternsDistance> data = knn.getData();

        for(PatternsDistance p : data){
            switch(p.getPattern().getClassOfPattern()){
                case 1:
                    class1.add(p.getPattern().getFeatureOne(), p.getPattern().getFeatureTwo());
                    break;
                case 2:
                    class2.add(p.getPattern().getFeatureOne(), p.getPattern().getFeatureTwo());
                    break;
                case 3:
                    class3.add(p.getPattern().getFeatureOne(), p.getPattern().getFeatureTwo());
                    break;
            }
        }

        int classOfPattern = compute(knn, pattern, mknn);

        switch(classOfPattern){
            case 1:
                class1.add(pattern.getFeatureOne(), pattern.getFeatureTwo());
                break;
            case 2:
                class2.add(pattern.getFeatureOne(), pattern.getFeatureTwo());
                break;
            case 3:
                class3.add(pattern.getFeatureOne(), pattern.getFeatureTwo());
                break;
        }

        XYSeriesCollection xydata = new XYSeriesCollection();
        xydata.addSeries(class1);
        xydata.addSeries(class2);
        xydata.addSeries(class3);

        JFreeChart chart = ChartFactory.createScatterPlot("KNN", "X feature", "Y feature", xydata);
        //ChartPanel chartPanel = new ChartPanel(chart);

        OutputStream out = null;
        try {
            out = new FileOutputStream(PATH_TO_IMAGE + "_" + (mknn ? "MkNN_" : "kNN_") + K + "one_pattern.png");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        BufferedImage chartImage = chart.createBufferedImage(500, 500);
        try {
            ImageIO.write(chartImage, "png", out);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
