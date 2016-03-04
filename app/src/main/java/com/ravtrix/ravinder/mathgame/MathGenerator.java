package com.ravtrix.ravinder.mathgame;

import java.util.Random;

/**
 * Created by Ravinder on 12/22/15.
 */
public class MathGenerator {

    Random rand = new Random();
    private boolean isRight = false;
    private boolean isWrong = false;
    private static int startingNumber = 0;
    private int secondNumber;
    private int resultNum;

    public void generateNumbers() {
        int numToAdd;
        secondNumber = rand.nextInt(4) + 2;
        numToAdd = weightedRandom();
        resultNum = startingNumber + secondNumber + numToAdd;

        int correctAnswer = startingNumber + secondNumber;
        if (resultNum == correctAnswer) {
            isRight = true;
        } else {
            isWrong = true;
        }
    }
    public void updateStartingNum() {
        startingNumber += secondNumber;
    }

    private int weightedRandom() {
        int num = rand.nextInt() * 100;

        if(num < 55) {
            return 0;
        } else if (num < 70) {
            return 1;
        } else if (num < 85) {
            return 2;
        } else {
            return 3;
        }
    }

    public void setStartingNumber(int starting) {
        startingNumber = starting;
    }

    public boolean getIsRight() {
        return isRight;
    }
    public boolean getIsWrong() {
        return isWrong;
    }
    public int getStartingNumber() {
        return startingNumber;
    }
    public int getSecondNumber() {
        return secondNumber;
    }
    public int getResultNum() {
        return resultNum;
    }
}
