package com.example.workoutapp;

import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.util.converter.DoubleStringConverter;
import javafx.scene.control.*;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.util.converter.IntegerStringConverter;

import java.time.LocalDateTime;
import java.util.List;

//table reference::https://edencoding.com/tableview-customization-cellfactory/

public class Controller {

    @FXML private Label dateLabel;
    @FXML private TextField sessionNameTextField;
    @FXML private TableView<StrengthExercise> strengthTable;
    @FXML private TableColumn<StrengthExercise, String> strNameCol;
    @FXML private TableColumn<StrengthExercise, Double> weightCol;
    @FXML private TableColumn<StrengthExercise, Integer> setsCol;
    @FXML private TableColumn<StrengthExercise, Integer> repsCol;
    @FXML private TableColumn<StrengthExercise, Double> volumeCol;
    @FXML private TableColumn<StrengthExercise, Double> oneRepMaxCol;
    @FXML private TableColumn<StrengthExercise, String> strNotesCol;
    @FXML private TableView<CardioExercise> cardioTable;
    @FXML private TableColumn<CardioExercise, String> cardNameCol;
    @FXML private TableColumn<CardioExercise, Double> inclineCol;
    @FXML private TableColumn<CardioExercise, Integer> speedCol;
    @FXML private TableColumn<CardioExercise, Integer> timeCol;
    @FXML private TableColumn<CardioExercise, Double> distanceCol;
    @FXML private TableColumn<CardioExercise, Double> paceCol;
    @FXML private TableColumn<CardioExercise, String> cardNotesCol;
    @FXML private MenuButton pastSessionsMenuButton;
    @FXML private MenuItem pastSessionsMenuItem;
    @FXML private Button createNewSessionButton;
    @FXML private Button deleteSessionButton;

    private Program program;
    private Session selectedSession; //to hold current session


    public void initializeApp(){
        program = new Program();
        initializeSession();
        //updatePastSessionsMenuButton();
    }

    public void initializeSession() {
        //current date-----------------------------------
        LocalDateTime currentDate = LocalDateTime.now();
        //create new session with date data
        selectedSession = new Session(currentDate, "");

        //initialize table
        ObservableList<StrengthExercise> defaultStrengthExercises = FXCollections.observableArrayList();
        ObservableList<CardioExercise> defaultCardioExercises = FXCollections.observableArrayList();
        //creating exercises to fill rows
        for (int i = 0; i < 6; i++) {
            StrengthExercise strengthExercise = new StrengthExercise("", 0.0, 0, 0,  "" );
            defaultStrengthExercises.add(strengthExercise);
        }
        for (int i = 0; i < 2; i++) {
            CardioExercise cardioExercise = new CardioExercise("", 0.0, 0, 0,  "" );
            defaultCardioExercises.add(cardioExercise);
        }
        //set items
        strengthTable.setItems(defaultStrengthExercises);
        cardioTable.setItems(defaultCardioExercises);
        //make table cells editable
        makeTableCellsEditable();

        // set the formatted date to the label and menu-------
        dateLabel.setText(selectedSession.getFormattedCreationDate());
    }

    public void makeTableCellsEditable(){
        // for the strength table
        //1
        strNameCol.setCellValueFactory(cellData -> cellData.getValue().exerciseNameProperty());
        strNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        strNameCol.setOnEditCommit(event -> {
            event.getRowValue().setExerciseName(event.getNewValue());
        });
        //2
        weightCol.setCellValueFactory(cellData -> cellData.getValue().secondColumnProperty().asObject());
        weightCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        weightCol.setOnEditCommit(event -> event.getRowValue().setSecondColumn(event.getNewValue()));
        //3
        setsCol.setCellValueFactory(cellData -> cellData.getValue().thirdColumnProperty().asObject());
        setsCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        setsCol.setOnEditCommit(event -> event.getRowValue().setThirdColumn(event.getNewValue()));
        //4
        repsCol.setCellValueFactory(cellData -> cellData.getValue().fourthColumnProperty().asObject());
        repsCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        repsCol.setOnEditCommit(event -> event.getRowValue().setFourthColumn(event.getNewValue()));
        //5
        // Calculate and bind Volume column
        volumeCol.setCellValueFactory(cellData -> {
            StrengthExercise exercise = cellData.getValue();
            DoubleProperty volume = new SimpleDoubleProperty(exercise.calculateFifthColumn());
            // Listen for changes in weight, sets, reps and update volume
            volume.bind(Bindings.createDoubleBinding(() ->
                            exercise.getSecondColumn() * exercise.getThirdColumn() * exercise.getFourthColumn(),
                    exercise.secondColumnProperty(), exercise.thirdColumnProperty(), exercise.fourthColumnProperty()));
            return volume.asObject();
        });
        volumeCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        volumeCol.setEditable(false);
        //6
        // Calculate and bind One Rep Max column
        oneRepMaxCol.setCellValueFactory(cellData -> {
            StrengthExercise exercise = cellData.getValue();
            DoubleProperty oneRepMax = new SimpleDoubleProperty(exercise.calculateSixthColumn());
            // Listen for changes in weight and reps and update oneRepMax
            oneRepMax.bind(Bindings.createDoubleBinding(() ->
                            exercise.getSecondColumn() * (1 + (exercise.getFourthColumn() / 30.0)),
                    exercise.secondColumnProperty(), exercise.fourthColumnProperty()));
            return oneRepMax.asObject();
        });
        oneRepMaxCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        oneRepMaxCol.setEditable(false);
        //7
        strNotesCol.setCellValueFactory(cellData -> cellData.getValue().seventhColumnProperty());
        strNotesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        strNotesCol.setOnEditCommit(event -> event.getRowValue().setSeventhColumn(event.getNewValue()));

        // for the cardio table
        //1
        cardNameCol.setCellValueFactory(cellData -> cellData.getValue().exerciseNameProperty());
        cardNameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cardNameCol.setOnEditCommit(event -> {
            event.getRowValue().setExerciseName(event.getNewValue());
        });
        //2
        inclineCol.setCellValueFactory(cellData -> cellData.getValue().secondColumnProperty().asObject());
        inclineCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        inclineCol.setOnEditCommit(event -> event.getRowValue().setSecondColumn(event.getNewValue()));
        //3
        speedCol.setCellValueFactory(cellData -> cellData.getValue().thirdColumnProperty().asObject());
        speedCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        speedCol.setOnEditCommit(event -> event.getRowValue().setThirdColumn(event.getNewValue()));
        //4
        timeCol.setCellValueFactory(cellData -> cellData.getValue().fourthColumnProperty().asObject());
        timeCol.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        timeCol.setOnEditCommit(event -> event.getRowValue().setFourthColumn(event.getNewValue()));
        //5
        // Calculate and bind Volume column
        distanceCol.setCellValueFactory(cellData -> {
            CardioExercise exercise = cellData.getValue();
            DoubleProperty distance = new SimpleDoubleProperty(exercise.calculateFifthColumn());
            // Listen for changes in incline, speed, time(min) and update distance
            distance.bind(Bindings.createDoubleBinding(() ->
                            exercise.getSecondColumn() * exercise.getThirdColumn() * exercise.getFourthColumn(),
                    exercise.secondColumnProperty(), exercise.thirdColumnProperty(), exercise.fourthColumnProperty()));
            return distance.asObject();
        });
        distanceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        distanceCol.setEditable(false);
        //6
        // Calculate and bind pace column
        paceCol.setCellValueFactory(cellData -> {
            CardioExercise exercise = cellData.getValue();
            DoubleProperty pace = new SimpleDoubleProperty(exercise.calculateSixthColumn());
            // Listen for changes in time(min) and distance and update pace
            pace.bind(Bindings.createDoubleBinding(() ->
                            exercise.getSecondColumn() * (1 + (exercise.getFourthColumn() / 30.0)),
                    exercise.secondColumnProperty(), exercise.fourthColumnProperty()));
            return pace.asObject();
        });
        paceCol.setCellFactory(TextFieldTableCell.forTableColumn(new DoubleStringConverter()));
        paceCol.setEditable(false);
        //7
        cardNotesCol.setCellValueFactory(cellData -> cellData.getValue().seventhColumnProperty());
        cardNotesCol.setCellFactory(TextFieldTableCell.forTableColumn());
        cardNotesCol.setOnEditCommit(event -> event.getRowValue().setSeventhColumn(event.getNewValue()));

    }

    //session name field changed from user
    @FXML public void handleSessionNameChange(){
        String newSessionName = sessionNameTextField.getText();
        selectedSession.setSessionName(newSessionName);
        //updatePastSessionMenuButton();

        //get formatted data
        String formattedDate = selectedSession.getFormattedCreationDate();
        //update pastSessionsMenuButton
        pastSessionsMenuButton.setText(formattedDate + " - " + newSessionName);
        pastSessionsMenuItem.setText(formattedDate + " - " + newSessionName);
    }

    //update pastSessionsMenuButton with updated session name
    public void updatePastSessionsMenuButton(){
        //clear existing
        pastSessionsMenuButton.getItems().clear();
        //get list of sessions in program
        List<Session> sessions = Program.getSessions();
        //sort sessions by creation date in descending order
        sessions.sort((s1, s2) -> s2.getCreationDate().compareTo(s1.getCreationDate()));
        for (Session session : sessions){
            String formattedSession = session.getFormattedCreationDate() + " - " + session.getSessionName();
            MenuItem sessionMenuItem = new MenuItem(formattedSession);
            //to associate menuItem to that session for later retrieval
            sessionMenuItem.setUserData(session);
            sessionMenuItem.setOnAction(event -> displaySelectedSession(session)); // view name of current session viewed
            pastSessionsMenuButton.getItems().add(sessionMenuItem);
        }
    }

    private void displaySelectedSession(Session session){
//        System.out.println("Selected Session: " + session.getSessionName());
//        System.out.println("Strength Exercises: " + session.getStrengthExercises());
//        System.out.println("Cardio Exercises: " + session.getCardioExercises());

        //set the session name to text field
        selectedSession = session;
        sessionNameTextField.setText(session.getSessionName());
        //populate tables
        ObservableList<StrengthExercise> strengthExercises = FXCollections.observableArrayList(session.getStrengthExercises());
        strengthTable.setItems(strengthExercises);
        ObservableList<CardioExercise> cardioExercises = FXCollections.observableArrayList(session.getCardioExercises());
        cardioTable.setItems(cardioExercises);

        //update pastSessionMenuButton with selected session info
        String formattedSession = session.getFormattedCreationDate() + " - " + session.getSessionName();
        pastSessionsMenuButton.setText(formattedSession);
        pastSessionsMenuItem.setText(formattedSession);
    }

    @FXML private void handleCreateNewSession(){
        saveCurrentSession();
        initializeSession();
        updatePastSessionsMenuButton(); //???
        sessionNameTextField.setText("");
        pastSessionsMenuButton.setText("");
    }

    private void saveCurrentSession(){
        //retrieve data from tables and save into new Session
        ObservableList<StrengthExercise> strengthExercises = strengthTable.getItems();
        ObservableList<CardioExercise> cardioExercises = cardioTable.getItems();

        // now clear existing exercises
        selectedSession.getStrengthExercises().clear();
        selectedSession.getCardioExercises().clear();

        // Add exercises to the selected session
        selectedSession.getStrengthExercises().addAll(strengthExercises);
        selectedSession.getCardioExercises().addAll(cardioExercises);

        // Update the session in the Program sessions list
        List<Session> sessions = Program.getSessions();
        sessions.add(selectedSession);

        //add menu item for saved session
        updatePastSessionsMenuButton();
    }

    @FXML private void handleDeleteSession() {
        //to fetch the select session from menuitem and delete it
        MenuItem selectedMenuItem = pastSessionsMenuButton.getItems().stream().filter(MenuItem::isVisible).filter(item -> item.getText().equals(pastSessionsMenuButton.getText())).findFirst().orElse(null);

        if (selectedSession != null) { //as long as there's a session
            Session sessionToDelete = (Session) selectedMenuItem.getUserData();
            deleteSession(sessionToDelete);
            updatePastSessionsMenuButton(); //refresh menuitems
        } else {
            System.out.println("Selected menu item is null or not found.");
        }
    }

    private void deleteSession(Session sessionToDelete) {
        if (sessionToDelete != null) {
            //remove selected session from program sessions list
            Program.getSessions().remove(sessionToDelete);

            //check if there are remaining sessions
            if (!Program.getSessions().isEmpty()) {
                LocalDateTime deleteDate = sessionToDelete.getCreationDate();
                Session previousSession = null;
                for (Session session : Program.getSessions()) {
                    if (session.getCreationDate().isBefore(deleteDate)) {
                        if (previousSession == null || session.getCreationDate().isAfter(previousSession.getCreationDate())) {
                            previousSession = session;
                        }
                    }
                }
                if (previousSession != null) {
                    displaySelectedSession(previousSession);
                } else {
                    //if no previous, display latest
                    Session latestSession = Program.getSessions().stream().max(java.util.Comparator.comparing(Session::getCreationDate)).orElse(null);
                    if (latestSession != null) {
                        displaySelectedSession(latestSession);
                    } else {
                        //otherwise, clear display
                        initializeSession();
                    }
                }
            } else {
                pastSessionsMenuButton.getItems().clear();
                initializeApp();
                sessionNameTextField.setText("");
                pastSessionsMenuButton.setText("");
            }
        }
    }
}