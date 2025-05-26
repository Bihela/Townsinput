package edu.curtin.oose2025s1.assignment2.model;

import edu.curtin.oose2025s1.assignment2.patterns.RailwayState;
import edu.curtin.oose2025s1.assignment2.patterns.UnderConstructionSingle;
import java.util.logging.Logger;

public class Railway {
    private static final Logger LOGGER = Logger.getLogger(Railway.class.getName());
    private final Town townA;
    private final Town townB;
    private RailwayState state;
    private boolean directionAToB;

    public Railway(Town townA, Town townB, long currentDay) {
        this.townA = townA;
        this.townB = townB;
        this.state = new UnderConstructionSingle(currentDay);
        this.directionAToB = true; // Deterministic initial direction
        LOGGER.info(() -> String.format("Railway created: %s <-> %s, initial directionAToB=%b",
                townA.getName(), townB.getName(), directionAToB));
    }

    public void updateState(long currentDay) {
        LOGGER.info(() -> String.format("Updating state: %s, day=%d", state.getStatus(), currentDay));
        state = state.nextState(this, currentDay);
    }

    public int transportGoods(Town source, Town destination, int availableGoods) {
        LOGGER.info(() -> String.format("Transporting goods: %s -> %s, state=%s, directionAToB=%b",
                source.getName(), destination.getName(), state.getStatus(), directionAToB));
        return state.transportGoods(source, destination, availableGoods, this);
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

    public boolean isDirectionAToB() {
        return directionAToB;
    }

    public void toggleDirection() {
        this.directionAToB = !this.directionAToB;
        LOGGER.info(() -> String.format("Toggled direction: %s <-> %s, new directionAToB=%b",
                townA.getName(), townB.getName(), directionAToB));
    }
}