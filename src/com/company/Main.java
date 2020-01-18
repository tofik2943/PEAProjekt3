package com.company;

public class Main {

    public static void main(String[] args) {
	Genetic genetic=new Genetic("C:\\STUDIA\\5 semestr\\pea\\Projekt3\\src\\com\\company\\tsp6.txt",100,6);
        System.out.println(genetic.createWorld(60,5));
        System.out.println("BestPath:\t"+genetic.getBestPath().toString());
    }
}
