package edu.curtin.oose2025s1.assignment2.patterns;

import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.model.Town;

public class UnderConstructionDual implements RailwayState {
    private final long completionDay;

    public UnderConstructionDual(long currentDay) {
        this.completionDay = currentDay + 5;
    }

    @Override
    public int transportGoods(Town source, Town destination, int availableGoods) {
        return 0;
    }

    @Override
    public String getStatus() {
        return "Dual-track under construction";
    }

    @Override
    public RailwayState nextState(Railway railway, long currentDay) {
        if (currentDay >= completionDay) {
            return new DualTrack();
        }
        return this;
    }
}