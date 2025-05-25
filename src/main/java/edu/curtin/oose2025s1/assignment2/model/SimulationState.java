package edu.curtin.oose2025s1.assignment2.model;

import java.util.ArrayList;
import java.util.List;

public class SimulationState {
    private final long day;
    private final List<String> messages;
    private final List<Town> towns;

    public SimulationState(long day, List<String> messages, List<Town> towns) {
        this.day = day;
        this.messages = new ArrayList<>(messages);
        this.towns = new ArrayList<>(towns);
    }

    public long getDay() {
        return day;
    }

    public List<String> getMessages() {
        return messages;
    }

    public List<Town> getTowns() {
        return towns;
    }
}