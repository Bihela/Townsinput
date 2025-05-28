package edu.curtin.oose2025s1.assignment2.patterns;

import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.model.Town;
import java.util.logging.Logger;

public class DualTrack implements RailwayState {
    private static final Logger LOGGER = Logger.getLogger(DualTrack.class.getName());

    @Override
    public int transportGoods(Town source, Town destination, int availableGoods, Railway railway) {
        LOGGER.info(() -> String.format("DualTrack: Attempting transport %s -> %s, available=%d, townA=%s, townB=%s",
                source.getName(), destination.getName(), availableGoods,
                railway.getTownA().getName(), railway.getTownB().getName()));

        if ((source.equals(railway.getTownA()) && destination.equals(railway.getTownB()) ||
             source.equals(railway.getTownB()) && destination.equals(railway.getTownA())) && availableGoods >= 100) {
            int transported = 100;
            source.receiveGoods(-transported);
            destination.receiveGoods(transported); 
            LOGGER.info(() -> String.format("Transported %d goods from %s to %s, source stockpile updated to %d",
                    transported, source.getName(), destination.getName(), source.getGoodsStockpile()));
            return transported;
        }

        LOGGER.info(() -> String.format("No transport: Invalid source/destination or insufficient goods for %s -> %s, available=%d",
                source.getName(), destination.getName(), availableGoods));
        return 0;
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