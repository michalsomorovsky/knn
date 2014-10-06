package com.company;

import com.company.Pattern;
import com.company.PatternsDistance;
import com.company.TrainPattern;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

/**
 * Created by msomorovsky on 06/10/14.
 */
public class KNN {

    private ArrayList<PatternsDistance> data;

    public ArrayList<PatternsDistance> getData(){
        return data;
    }

    public void loadData(String filePath){

        data = new ArrayList<PatternsDistance>();

        Path file = Paths.get(filePath);
        InputStream in = null;
        try {
            in = Files.newInputStream(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        try {
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                data.add(new PatternsDistance(new TrainPattern(Double.parseDouble(parts[0]), Double.parseDouble(parts[1]), Integer.parseInt(parts[2]))));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Pattern randomPattern(){
        Random rand = new Random();
        Double min=0.0, max=8.0;
        return new Pattern(min + rand.nextDouble()*(max-min), min + rand.nextDouble()*(max-min));
    }

    public double euklidDistance(Pattern pattern1, Pattern pattern2){
        return Math.sqrt(Math.pow(pattern1.featureOne - pattern2.featureOne, 2.0) + Math.pow(pattern1.featureTwo - pattern2.featureTwo, 2.0));
    }

    public void knn(Pattern testPattern){
        for(PatternsDistance trainPattern : data){
            trainPattern.setDistance(euklidDistance(testPattern, trainPattern.getPattern()));
        }

        Collections.sort(data, new PatternDistanceComparator());

        //return classDecision(k);
    }

    public void mknn(Pattern tesPattern, int k){
        knn(tesPattern);

        calculateWeights(k);
    }

    public void calculateWeights(int k){
        double nearestDistance = data.get(0).getDistance();
        double farestDistance = data.get(k-1).getDistance();

        for(int i=0; i<k; i++){
            data.get(i).setWeight((farestDistance - data.get(i).getDistance())/(farestDistance - nearestDistance));
        }

    }

    public int knnClassDecision(int k){
        HashMap<Integer, Integer> classes = new HashMap<Integer, Integer>();
        while(k>0){
            if(classes.containsKey(data.get(--k).getPattern().getClassOfPattern())){
                classes.put(Integer.valueOf(data.get(k).getPattern().getClassOfPattern()), classes.get(data.get(k).getPattern().getClassOfPattern())+1);
            }
            else {
                classes.put(Integer.valueOf(data.get(k).getPattern().getClassOfPattern()), 1);
            }
        }

        Map.Entry<Integer, Integer> maxEntry = null;

        for(Map.Entry<Integer, Integer> entry : classes.entrySet()){
            if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0){
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();
    }

    public int mknnClassDecision(int k){
        HashMap<Integer, Double> classes = new HashMap<Integer, Double>();
        while(k>0){
            if(classes.containsKey(data.get(--k).getPattern().getClassOfPattern())){
                classes.put(Integer.valueOf(data.get(k).getPattern().getClassOfPattern()), classes.get(data.get(k).getPattern().getClassOfPattern())+data.get(k).getWeight());
            }
            else {
                classes.put(Integer.valueOf(data.get(k).getPattern().getClassOfPattern()), data.get(k).getWeight());
            }
        }

        Map.Entry<Integer, Double> maxEntry = null;

        for(Map.Entry<Integer, Double> entry : classes.entrySet()){
            if(maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0){
                maxEntry = entry;
            }
        }

        return maxEntry.getKey();
    }



    public class PatternDistanceComparator implements Comparator<PatternsDistance>{
        @Override
        public int compare(PatternsDistance o1, PatternsDistance o2) {
            double diff = o1.getDistance() - o2.getDistance();
            if(diff==0.0) return 0;
            else if(diff < 0.0) return - 1;
            else return 1;
        }
    }

}
