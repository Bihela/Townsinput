package edu.curtin.oose2025s1.assignment2.patterns;

import edu.curtin.oose2025s1.assignment2.model.SimulationState;

public interface Observer {
    void update(SimulationState state);
}