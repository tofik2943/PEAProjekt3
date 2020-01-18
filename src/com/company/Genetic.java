package com.company;

import java.util.ArrayList;
import java.util.Random;

public class Genetic {

    private ArrayList<Unit> population;
    private ArrayList<Integer> bestPath;
    private int bestCost = 10000;
    private FileRead fileRead;
    private int populationCount;
    private int matrixSize;
    private int bestKnownCost;

    public Genetic(String path, int populationCount, int bestKnownCost) {
        fileRead = new FileRead(path);
        population = new ArrayList<>();
        this.bestKnownCost = bestKnownCost;
        this.populationCount = populationCount;
        this.fileRead.fullfill();
        fileRead.setSize();
        this.matrixSize = fileRead.getSize();
        System.out.println("Pobrana macierz jest rozmiaru:\t" + this.matrixSize);
    }

    public FileRead getFileRead() {
        return fileRead;
    }

    public int randomNum(int size) {
        int randomInt = -1;
        if (size == 1)
            return randomInt;
        while (randomInt <= 0) {
            Random random = new Random();
            randomInt = random.nextInt(size);
        }
        return randomInt;
    }

    public void mutation1(Unit unit) {
        Integer temp;
        int index1 = randomNum(matrixSize), index2 = randomNum(matrixSize);
        while ((index1 == index2) || (index1 == 0) || (index2 == 0)) {
            index1 = randomNum(matrixSize);
            index2 = randomNum(matrixSize);
        }
        if (index1 > index2) {          //index2 musi być większy od index1
            temp = index1;
            index1 = index2;
            index2 = temp;
        }
        Integer tmp = unit.getPath().get(index1);
        unit.getPath().set(index1, unit.getPath().get(index2));
        unit.getPath().set(index2, tmp);
        unit.getPath().add(index1);
        unit.getPath().add(index2);
        unit.setCost(countDistance(unit.getPath(), true));
    }

    public ArrayList<Integer> generateRandomPath() {
        ArrayList<Integer> randomPath = new ArrayList<>();
        randomPath.add(0);
        int index;
        int w = 0;
        while (randomPath.size() < this.getFileRead().getSize()) {
            index = randomNum(matrixSize);
            for (Integer k : randomPath) {
                if (k == index) {
                    w++;
                }
            }
            if (w == 0) {
                randomPath.add(index);
            } else {
                w = 0;
            }
        }
        return randomPath;
    }

    public int countDistance(ArrayList<Integer> path, boolean b) {

        int count = 0;
        for (int i = 0; i < path.size() - 1; i++) {

            count += this.getFileRead().weight(path.get(i), path.get(i + 1));
        }

        if (b)
            count = count + this.getFileRead().weight(path.get(path.size() - 1), path.get(0));
        // System.out.println("KOSZT WYNOSI: "+count);
        return count;

    }

    public ArrayList<Unit> cross1(Unit a, Unit b, int lengthProb) {     //trzeb policzyć koszt
        Unit childA = new Unit(), childB = new Unit();
        ArrayList<Integer> pathChildA = new ArrayList<>(), pathChildB = new ArrayList<>();
        int tempA = -1, tempB = -1, countOfReSign;
        int duplicatedValueA, duplicatedValueB;
        Integer toBeInsertedA, toBeInsertedB;

        boolean error = false;

        for (int i = 0; i < matrixSize; i++) {
            pathChildA.add(0);
            pathChildB.add(0);
        }

        boolean section = true;
        int sectionSize = 0;
        int locus = -1;

        while (section && (sectionSize < (matrixSize - 2))) {       //DLUGOSC SEKCJI
            if (randomNum(101) < lengthProb) {
                sectionSize++;
            } else {
                section = false;
            }
        }
        if (sectionSize != 0) {                                     //NAPRAWA LOCUS
            locus = randomNum(matrixSize - sectionSize);
            if (locus <= 0) {
                System.out.println("cross1 locus error ");
                locus = 1;
            }

            for (int i = 0; i < sectionSize; i++) {
                duplicatedValueA = b.getPath().get(locus + i); //wartosc jaka bedzie przeniesiona do A
                duplicatedValueB = a.getPath().get(locus + i); //wartosc jaka bedzie przeniesiona do B
                for (int k = 0; k < matrixSize; k++) {
                    if (duplicatedValueA == a.getPath().get(k)) {
                        tempA = k;                             //indeks wartości ktora bedzie zduplikowana po przeniesieniu
                    }
                }
                for (int k = 0; k < matrixSize; k++) {
                    if (duplicatedValueB == b.getPath().get(k)) {
                        tempB = k;                              //indeks wartości ktora bedzie zduplikowana po przeniesieniu
                    }
                }
                if (((tempA < locus) || (tempA > (locus + sectionSize - 1))) && (tempA != -1))
                    pathChildA.set(tempA, a.getPath().get(locus + i));
                if (((tempB < locus) || (tempB > (locus + sectionSize - 1))) && (tempB != -1))
                    pathChildB.set(tempB, b.getPath().get(locus + i));

                //sprawdz w całej tablicy czy wartośc toBeInsertedA występuje już poza sekcją
                pathChildA.set(locus + i, b.getPath().get(locus + i));
                pathChildB.set(locus + i, a.getPath().get(locus + i));
            }
            for (int k = 1; k < matrixSize; k++) {
                if (pathChildA.get(k) == 0) {
                    pathChildA.set(k, a.getPath().get(k));
                    pathChildB.set(k, b.getPath().get(k));
                }
            }
            ////////////////////////////////////
            boolean error1=true,onceMore=false;
int w=0;
            for (int i = 1; i < matrixSize; i++) {
                for (int k = 0; k < sectionSize; k++) {
                    if((i==1)&&(k==0)){
                        onceMore=false;
                    }
                    if ((i < locus) || (i > locus + sectionSize - 1)) {
                        if(pathChildA.get(i).equals(pathChildA.get(locus + k))){
                            pathChildA.set(i,a.getPath().get(searchValue(b.getPath(),pathChildA.get(i))));//na pozycji 'i' ma byc wartość z 'a' na pozycji na ktorej 'b' ma wartośc aktualnej pozycji a(i).
                            onceMore=true;
                        }
                        if(pathChildB.get(i).equals(pathChildB.get(locus + k))){
                            pathChildB.set(i,b.getPath().get(searchValue(a.getPath(),pathChildB.get(i))));//na pozycji 'i' ma byc wartość z 'a' na pozycji na ktorej 'b' ma wartośc aktualnej pozycji a(i).
                            onceMore=true;
                        }
                        if(onceMore){
                            i=1;
                            k=-1;
                            w++;
                            System.out.println("once more"+w);
                        }

                    }
                }
            }
            //////////////////////////////////
        } else {
            pathChildA = a.getPath();
            pathChildB = b.getPath();
            childA.setPath(pathChildA);
            childB.setPath(pathChildB);
            childA.setCost(countDistance(pathChildA, true));
            childB.setCost(countDistance(pathChildB, true));
        }

            childA.setPath(pathChildA);
            childB.setPath(pathChildB);
            childA.setCost(countDistance(pathChildA, true));
            childB.setCost(countDistance(pathChildB, true));

        ArrayList<Unit> toBeReturned = new ArrayList<>();
        toBeReturned.add(childA);
        toBeReturned.add(childB);

        return toBeReturned;
    }

    public Unit mutation2(Unit unit) {

        return unit;
    }
    public  int searchValue(ArrayList<Integer>path,int value){
        for(int i=0;i<path.size();i++){
            if(path.get(i)==value){
                return i;
            }
        }
        return -1;
    }

    public void toBeDeleted() {
        ArrayList<Integer> indexes = new ArrayList<>();
        int min = 100000;
        int minIndex = -1;
        float avg = 0;
        for (Unit unit : population) {
            avg += unit.getCost();
        }
        avg = avg / population.size();
        System.out.println("avg=" + avg*0.95);
        for (int i = population.size() - 1; i >= 0; i--) {
            if (population.get(i).getCost() > (avg)) {
                indexes.add(i);
            } else {
                if (population.get(i).getCost() < min) {
                    min = population.get(i).getCost();
                    minIndex = i;
                }
            }
        }
        if (min < bestCost) {
            bestCost = min;
            bestPath = population.get(minIndex).getPath();
        }

        for (Integer integer : indexes) {

            population.remove(integer.intValue());
            // System.out.println("usuwanko");
        }


    }


    public int createWorld(int downCrossProb, int mutationProb) {
        int counter;
        int matingProb;
        Unit copyUnitReadyToMate = null;
        ArrayList<Unit> tempRet;
        for (int i = 0; i < populationCount; i++) {
            Unit unit = new Unit();
            unit.setPath(generateRandomPath());
            unit.setCost(countDistance(unit.getPath(), true));
            population.add(unit);
        }
        System.out.println("Starting population:\t" + population.size());

        while ((population.size() > 1) && (bestCost != bestKnownCost)) { //program trwa
            counter = Math.floorDiv(populationCount, 2) + 1;

            while (counter > 0) {                                          //dopóki nie będzie określonej liczby rozmnożeń
                boolean changeCrossType = false;
                for (int k = 0; k < population.size(); k++) {

                    matingProb = bestCost / population.get(k).getCost() * 100;
                    if (matingProb < downCrossProb) {
                        matingProb = downCrossProb;
                    }
                    if (randomNum(101) < matingProb) {
                        if (copyUnitReadyToMate != null) {
                            tempRet = cross1(population.get(k), copyUnitReadyToMate, 80);
                            population.add(tempRet.get(0));
                            population.add(tempRet.get(1));
                            copyUnitReadyToMate = null;
                            counter--;
                            if (counter <= 0) {
                                k = population.size();
                            }
                        }
                        if (k < population.size())
                            copyUnitReadyToMate = population.get(k);
                    }
                }
                System.out.println("rozmiar populacji po rozmnażaniu to:{" + this.population.size()+"}");
            }
            //mutacje
            if (mutationProb > randomNum(100)) {
                int toBeMutated = randomNum(population.size());
                mutation1(population.get(toBeMutated));
                System.out.println("mutated");
                //  population.set(toBeMutated,mutation1(population.get(toBeMutated)));
            }
            //selekcja
            toBeDeleted();
            System.out.println("rozmiar populacji po selekcji{" + this.population.size()+"}");
            //1. stworzyc osobniki z drogą
            //2. osobniki się mnożą z prawd. ma dojść do floor(n/2) mnożeń
            //3. jest wywoływana funkcja odrzucająca rozwiązania słabsze od średniej
            //4. szansa na mutacje
            System.out.println("Best Cost:" + bestCost+"\t bestPath:"+bestPath.toString());
        }
        System.out.println(population.size());
        return bestCost;
    }


    public ArrayList<Integer> getBestPath() {
        return bestPath;
    }

    public int getBestCost() {
        return bestCost;
    }
}