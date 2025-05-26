package edu.curtin.oose2025s1.assignment2.patterns;

import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.model.Town;
import java.util.logging.Logger;

public class UnderConstructionSingle implements RailwayState {
    private static final Logger LOGGER = Logger.getLogger(UnderConstructionSingle.class.getName());
    private final long completionDay;

    public UnderConstructionSingle(long currentDay) {
        this.completionDay = currentDay + 5;
        LOGGER.info(() -> String.format("Started construction, completionDay=%d", completionDay));
    }

    @Override
    public int transportGoods(Town source, Town destination, int availableGoods, Railway railway) {
        return 0;
    }

    @Override
    public String getStatus() {
        return "Single-track under construction";
    }

    @Override
    public RailwayState nextState(Railway railway, long currentDay) {
        if (currentDay >= completionDay) {
            LOGGER.info(() -> String.format("Transitioning to SingleTrack, currentDay=%d, completionDay=%d",
                    currentDay, completionDay));
            return new SingleTrack();
        }
        LOGGER.info(() -> String.format("Remaining in UnderConstructionSingle, currentDay=%d, completionDay=%d",
                currentDay, completionDay));
        return this;
    }
}