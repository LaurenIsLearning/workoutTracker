package com.example.workoutapp;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.lang.Math;

public class CardioExercise extends Exercise { // child of exercise
    public CardioExercise(String name, double incline, int speed, int time, String notes) {
        super(name, incline, speed, time, notes);
    }

    //abstract methods to calculate columns
    @Override//calculate effective distance (speed * time)/(Math.cos(Math.toRadians(inclineAngle))
    public double calculateFifthColumn(){
        //time (min) / 60 for hours multiplied by speed
        double horizontalDistance = ((double) getFourthColumn() / 60) * getThirdColumn();
        return horizontalDistance / Math.cos(Math.toRadians(getSecondColumn()));
    }
    @Override//calculate pace (time / distance)
    public double calculateSixthColumn(){
        double fifthColumnValue = getFifthColumn();
        if (Double.isFinite(fifthColumnValue) && fifthColumnValue != 0) {
            return getFourthColumn() / getFifthColumn();
        } else {
            return 0;
        }
    }
}