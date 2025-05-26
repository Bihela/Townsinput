package edu.curtin.oose2025s1.assignment2.patterns;

import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.model.Town;

public class DualTrack implements RailwayState {
    @Override
    public int transportGoods(Town source, Town destination, int availableGoods, Railway railway) {
        return Math.min(availableGoods, 100);
    }

    @Override
    public String getStatus() {
        return "Dual-track completed";
    }

    @Override
    public RailwayState nextState(Railway railway, long currentDay) {
        return this;
    }
}