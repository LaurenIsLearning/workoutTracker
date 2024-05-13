package com.example.workoutapp;

public class StrengthExercise extends Exercise { // child of exercise
    // fields
    public StrengthExercise(String name, double weight, int sets, int reps, String notes) {
        super(name, weight, sets, reps, notes);
    }

    //abstract methods to calculate columns
    @Override //calculate volume (sets * reps) to find total column for weekly count
    public double calculateFifthColumn(){
        return getThirdColumn() * getFourthColumn() * getSecondColumn();
    }
    @Override//calculate one rep max (OConner formula: 1RM = weight * (1+(.025 * reps)))
    public double calculateSixthColumn(){
        return getSecondColumn() * (1 + (getFourthColumn() / 30.0));
    }


}