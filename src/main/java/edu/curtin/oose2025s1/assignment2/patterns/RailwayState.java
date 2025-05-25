package edu.curtin.oose2025s1.assignment2.patterns;

import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.model.Town;

public interface RailwayState {
    int transportGoods(Town source, Town destination, int availableGoods);
    String getStatus();
    RailwayState nextState(Railway railway, long currentDay);
}