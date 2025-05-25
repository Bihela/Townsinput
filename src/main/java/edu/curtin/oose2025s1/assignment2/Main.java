package edu.curtin.oose2025s1.assignment2;

import edu.curtin.oose2025s1.assignment2.util.SimulationFactory;

public class Main {
    public static void main(String[] args) {
        SimulationFactory factory = new SimulationFactory();
        factory.createSimulation().run();
    }
}