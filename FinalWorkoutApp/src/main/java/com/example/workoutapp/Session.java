package com.example.workoutapp;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Session {
    // fields
    private LocalDateTime creationDate;
    private String sessionName;
    private final ObservableList<StrengthExercise> strengthExercises;
    private final ObservableList<CardioExercise> cardioExercises;

    // constructors
    public Session(LocalDateTime creationDate, String sessionName) {
        this.creationDate = creationDate;
        this.sessionName = sessionName;
        this.strengthExercises = FXCollections.observableArrayList();
        this.cardioExercises = FXCollections.observableArrayList();
    }

    public void setSessionName(String sessionName){
        this.sessionName = sessionName;
    }

    public LocalDateTime getCreationDate(){
        return creationDate;
    }

    public String getFormattedCreationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MMM dd, yyyy");
        return creationDate.format(formatter);
    }

    public String getSessionName(){
        return sessionName;
    }

    //getters for exercise lists
    public ObservableList<StrengthExercise> getStrengthExercises(){
        return strengthExercises;
    }
    public ObservableList<CardioExercise> getCardioExercises(){
        return cardioExercises;
    }


}