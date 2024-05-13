package com.example.workoutapp;

import java.util.ArrayList;
import java.util.List;

public class Program {
    // fields
    private static List<Session> sessions;

    // constructors
    public Program() {
        if (sessions == null) {
            sessions = new ArrayList<>();
        }
    }

    public static List<Session> getSessions(){
        return sessions;
    }

}
