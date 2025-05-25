package edu.curtin.oose2025s1.assignment2.model;

import edu.curtin.oose2025s1.assignment2.patterns.RailwayState;
import edu.curtin.oose2025s1.assignment2.patterns.UnderConstructionSingle;

public class Railway {
    private final Town townA;
    private final Town townB;
    private RailwayState state;

    public Railway(Town townA, Town townB, long currentDay) {
        this.townA = townA;
        this.townB = townB;
        this.state = new UnderConstructionSingle(currentDay);
    }

    public void updateState(long currentDay) {
        state = state.nextState(this, currentDay);
    }

    public int transportGoods(Town source, Town destination, int availableGoods) {
        return state.transportGoods(source, destination, availableGoods);
    }

    public String getStatus() {
        return state.getStatus();
    }

    public Town getTownA() {
        return townA;
    }

    public Town getTownB() {
        return townB;
    }

    public void setState(RailwayState state) {
        this.state = state;
    }
}