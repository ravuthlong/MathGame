package com.ravtrix.ravinder.mathgame;

import java.util.Random;

/**
 * Created by Ravinder on 12/22/15.
 */
class MathGenerator {

    private boolean isRight = false;
    private boolean isWrong = false;
    private static int startingNumber = 0;
    private int secondNumber;
    private int resultNum;

    void generateNumbers() {
        int numToAdd;
        Random rand = new Random();

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
    void updateStartingNum() {
        startingNumber += secondNumber;
    }

    /**
     * Find the number based on the random number.
     * @return      the number
     */
    private int weightedRandom() {
        Random rand = new Random();
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

    void setStartingNumber(int starting) {
        startingNumber = starting;
    }
    boolean getIsRight() {
        return isRight;
    }
    boolean getIsWrong() {
        return isWrong;
    }
    int getStartingNumber() {
        return startingNumber;
    }
    int getSecondNumber() {
        return secondNumber;
    }
    int getResultNum() {
        return resultNum;
    }
}
