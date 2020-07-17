package ge.edu.freeuni.Models;

import java.util.Random;

public class Randomizer {
    public int getRandomInteger(){
        Random rand = new Random();
        int randInteger = rand.nextInt(1000000) + 100000;
        return randInteger;
    }
}
