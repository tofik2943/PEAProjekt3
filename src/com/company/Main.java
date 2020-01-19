package com.company;

public class Main {

    public static void main(String[] args) {
	Genetic genetic=new Genetic("C:\\STUDIA\\5 semestr\\pea\\Projekt3\\src\\com\\company\\data21.txt",100,2707);
        System.out.println(genetic.createWorld(60,10));
        if(genetic.getBestPath()!=null)
        System.out.println("BestPath:\t"+genetic.getBestPath().toString());
    }
}
