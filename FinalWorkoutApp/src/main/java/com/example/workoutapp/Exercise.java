package com.example.workoutapp;
import javafx.beans.property.*;

public abstract class Exercise {
    // fields
    private final StringProperty nameColumn = new SimpleStringProperty();  //strength/cardio
    private final DoubleProperty secondColumn = new SimpleDoubleProperty(); //weight/incline
    private final IntegerProperty thirdColumn = new SimpleIntegerProperty(); //sets/speed
    private final IntegerProperty fourthColumn = new SimpleIntegerProperty(); //reps/time
    private final DoubleProperty fifthColumn = new SimpleDoubleProperty(); //volume/distance
    private final DoubleProperty sixthColumn = new SimpleDoubleProperty(); //one rep max/pace
    private final StringProperty seventhColumn = new SimpleStringProperty(); //notes/notes


    // constructors
    public Exercise(String exerciseName, double second, int third, int fourth, String notes) {
        this.nameColumn.set(exerciseName);
        this.secondColumn.set(second);
        this.thirdColumn.set(third);
        this.fourthColumn.set(fourth);
        this.fifthColumn.set(calculateFifthColumn());
        this.sixthColumn.set(calculateSixthColumn());
        this.seventhColumn.set(notes);
    }

    //abstract methods to calculate columns
    public abstract double calculateFifthColumn();
    public abstract double calculateSixthColumn();

    //setters
    public void setExerciseName(String newValue) {
        this.nameColumn.set(newValue);
    }
    public void setSecondColumn(Double newValue) {
        this.secondColumn.set(newValue);
    }
    public void setThirdColumn(Integer newValue) {
        this.thirdColumn.set(newValue);
    }
    public void setFourthColumn(Integer newValue) {
        this.fourthColumn.set(newValue);
    }
    public void setSeventhColumn(String newValue) {
        this.seventhColumn.set(newValue);
    }

    //getters
    public Double getSecondColumn(){
        return secondColumn.get();
    }
    public Integer getThirdColumn(){
        return thirdColumn.get();
    }
    public Integer getFourthColumn(){
        return fourthColumn.get();
    }
    public Double getFifthColumn(){
        return fifthColumn.get();
    }

    //binding properties
    public StringProperty exerciseNameProperty() {
        return nameColumn;
    }

    public DoubleProperty secondColumnProperty() {
        return secondColumn;
    }

    public IntegerProperty thirdColumnProperty() {
        return thirdColumn;
    }

    public IntegerProperty fourthColumnProperty() {
        return fourthColumn;
    }

    public StringProperty seventhColumnProperty() {
        return seventhColumn;
    }

}