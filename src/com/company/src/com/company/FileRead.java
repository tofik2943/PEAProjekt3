package com.company;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import static java.lang.Math.sqrt;

public class FileRead {
    private ArrayList<Integer> vertex;
    Scanner scanner;
    private int size=-1;

    public FileRead(String path) {
        java.io.File file = new java.io.File(path);//com/company/dane.txt
        vertex =new ArrayList<>();
        try {
            scanner = new Scanner(file);

        } catch (FileNotFoundException e) {
            System.out.println("File wasn't found");
        }
    }

    public int getSize() {
        return size;
    }

    public int assignSize(){
        double sizeD=(double)vertex.size();
        return (int)sqrt(sizeD);
    }

    public void setSize(){
        this.size= assignSize();
    }

    public void fullfill() {
        String temp;
        while (scanner.hasNext()) {
            temp= scanner.next();

            if(temp.equals("EDGE_WEIGHT_SECTION")   ){
                while(scanner.hasNext()){

                    vertex.add(scanner.nextInt());

                }
            }
        }
        // System.out.println("Pobrano "+vertex.size()+" długości");
    }
    public int weight(int from, int to){
        if((from<0)||(to<0)||(from>size)||(to>size)) {
            System.out.println("Wrong weight() arguments");
            return -1;
        }
        return vertex.get(from*size+to);
    }

}
