package edu.curtin.oose2025s1.assignment2.model;

import edu.curtin.oose2025s1.assignment2.input.MessageProcessor;
import edu.curtin.oose2025s1.assignment2.output.OutputManager;
import edu.curtin.oose2025s1.assignment2.patterns.Observer;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import java.io.IOException;

public class Simulation {
    private static final Logger LOGGER = Logger.getLogger(Simulation.class.getName());
    private final MessageProcessor messageProcessor;
    @SuppressWarnings("PMD.UnusedPrivateField") // Field used for dependency injection and observer setup
    private final OutputManager outputManager;
    private final List<Observer> observers = new ArrayList<>();
    private final List<Town> towns = new ArrayList<>();
    private final List<String> messages = new ArrayList<>();
    private long day = 0;

    public Simulation(MessageProcessor messageProcessor, OutputManager outputManager) {
        this.messageProcessor = messageProcessor;
        this.outputManager = outputManager;
        initializeObservers();
    }

    private void initializeObservers() {
        addObserver(outputManager.getConsoleObserver());
        addObserver(outputManager.getFileObserver());
    }

    public void run() {
        LOGGER.info(() -> "Starting simulation");
        try {
            while (System.in.available() == 0) {
                day++;
                messages.clear();
                messageProcessor.processMessages(this);
                updateRailways();
                produceGoods();
                transportGoods();
                notifyObservers();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    LOGGER.severe(() -> "Simulation interrupted: " + e.getMessage());
                    throw new SimulationException("Simulation interrupted", e);
                }
            }
        } catch (IOException e) {
            LOGGER.severe(() -> "Error checking System.in: " + e.getMessage());
            throw new SimulationException("Simulation terminated due to I/O error", e);
        }
        LOGGER.info(() -> "Simulation ended");
    }

    public final void addObserver(Observer observer) {
        observers.add(observer);
    }

    private void notifyObservers() {
        SimulationState state = new SimulationState(day, messages, towns);
        observers.forEach(observer -> observer.update(state));
    }

    public void addTown(String name, int population) {
        towns.add(new Town(name, population));
    }

    public void updatePopulation(String name, int population) {
        towns.stream()
             .filter(t -> t.getName().equals(name))
             .findFirst()
             .ifPresent(t -> t.setPopulation(population));
    }

    public void addRailway(String townAName, String townBName) {
        Town townA = findTown(townAName);
        Town townB = findTown(townBName);
        if (townA != null && townB != null) {
            Railway railway = new Railway(townA, townB, day);
            townA.addRailway(railway);
            townB.addRailway(railway);
        } else {
            LOGGER.warning(() -> "Cannot add railway: Invalid towns " + townAName + ", " + townBName);
        }
    }

    public void addMessage(String message) {
        messages.add(message);
    }

    private Town findTown(String name) {
        return towns.stream()
                    .filter(t -> t.getName().equals(name))
                    .findFirst()
                    .orElse(null);
    }

    private void updateRailways() {
        towns.forEach(t -> t.getRailways().forEach(r -> r.updateState(day)));
    }

    private void produceGoods() {
        towns.forEach(Town::produceGoods);
    }

    private void transportGoods() {
        towns.forEach(Town::transportGoods);
    }

    public long getDay() {
        return day;
    }

    public List<Town> getTowns() {
        return new ArrayList<>(towns);
    }

    public List<String> getMessages() {
        return new ArrayList<>(messages);
    }
}