package com.company;

import java.util.ArrayList;

public class Unit {
  private   ArrayList<Integer> path;
  private   int cost;
    public Unit(){
        path=new ArrayList<>();
    }

    public ArrayList<Integer> getPath() {
        return path;
    }

    public void setPath(ArrayList<Integer> path) {
        this.path = path;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }
}
