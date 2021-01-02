package com.company;

import java.util.ArrayList;

public class Main {

    public static void main(String[] args) {
        Genetic genetic = new Genetic("C:\\STUDIA\\5 semestr\\pea\\Projekt3\\src\\com\\company\\tsp_10c.txt", 100, 10);
        ArrayList<Integer> path = new ArrayList<>();
        int min = 10000000;
        int max = 0;
        int avg = 0;
        int times = 5;
        int differentPopulationSize = 1;
        long start, stop;
        int lastResult;
        long argumentTime;
        int populationSize = 32;
        start = System.currentTimeMillis();
        for (int k = 0; k < differentPopulationSize; k++) {
            avg=0;
            max=0;
            min=100000000;
            for (int i = 0; i < times; i++) {

                genetic.setPopulationCount(populationSize);
                argumentTime = System.currentTimeMillis();
                lastResult = genetic.createWorld(60, 30, argumentTime, 15);
                //System.out.println("error:"+genetic.countOfErrors+" notError:"+genetic.countOfGood);
                if (lastResult > max)
                    max = lastResult;
                if (lastResult < min) {
                    min = lastResult;
                    path = genetic.getBestPath();
                }

                avg += lastResult;
                genetic.reset();
                if (i == (times - 1)) {
                    System.out.println("population:\t" + genetic.getPopulationCount());
                    populationSize = populationSize * 2;
                    genetic.setPopulationCount(populationSize);


                    stop = System.currentTimeMillis();
                    avg = avg / times;
                    System.out.println("time:\t" + (stop - start) / 1000D);
                    System.out.println("min:\t" + min + "\nmax:\t" + max + "\navg:\t" + avg);
                    if (path != null)
                        System.out.println("Best Path:\t" + path.toString());
                    start = System.currentTimeMillis();
                }
            }

        }
    }
}
