package edu.curtin.oose2025s1.assignment2.patterns;

import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.model.Town;
import java.util.Random;

public class SingleTrack implements RailwayState {
    private boolean directionAToB;
    private static final Random RANDOM = new Random();

    public SingleTrack() {
        this.directionAToB = RANDOM.nextBoolean();
    }

    @Override
    public int transportGoods(Town source, Town destination, int availableGoods) {
        Railway railway = source.getRailways().stream()
                               .filter(r -> r.getTownA().equals(destination) || r.getTownB().equals(destination))
                               .findFirst()
                               .orElse(null);
        if (railway != null) {
            if ((source.equals(railway.getTownA()) && directionAToB) ||
                (source.equals(railway.getTownB()) && !directionAToB)) {
                int transported = Math.min(availableGoods, 100);
                directionAToB = !directionAToB;
                return transported;
            }
        }
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