package edu.curtin.oose2025s1.assignment2.patterns;

import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.model.Town;
import java.util.logging.Logger;

public class SingleTrack implements RailwayState {
    private static final Logger LOGGER = Logger.getLogger(SingleTrack.class.getName());

    @Override
    public int transportGoods(Town source, Town destination, int availableGoods, Railway railway) {
        LOGGER.info(() -> String.format("SingleTrack: Attempting transport %s -> %s, directionAToB=%b, available=%d, townA=%s, townB=%s",
                source.getName(), destination.getName(), railway.isDirectionAToB(), availableGoods,
                railway.getTownA().getName(), railway.getTownB().getName()));

        boolean isAToB = railway.isDirectionAToB() && source.equals(railway.getTownA()) && destination.equals(railway.getTownB());
        boolean isBToA = !railway.isDirectionAToB() && source.equals(railway.getTownB()) && destination.equals(railway.getTownA());

        if (isAToB || isBToA) {
            int transported = Math.min(availableGoods, 100);
            source.receiveGoods(-transported); // Deduct from source stockpile
            railway.toggleDirection();
            LOGGER.info(() -> String.format("Transported %d goods from %s to %s, source stockpile updated, new directionAToB=%b",
                    transported, source.getName(), destination.getName(), railway.isDirectionAToB()));
            return transported;
        }

        LOGGER.info(() -> String.format("No transport: Condition failed for %s -> %s, isAToB=%b, isBToA=%b, directionAToB=%b",
                source.getName(), destination.getName(), isAToB, isBToA, railway.isDirectionAToB()));
        return 0;
    }

    @Override
    public String getStatus() {
        return "Single-track completed";
    }

    @Override
    public RailwayState nextState(Railway railway, long currentDay) {
        return this;
    }
}
// REMINDER: Updated transportGoods() to deduct goods from source stockpile and remove direct destination stockpile update, ensuring single-direction alternating transport. Fixed dual-direction transport (both towns gt:100) from Day 9 onward (2025-05-26).