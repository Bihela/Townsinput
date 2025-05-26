package edu.curtin.oose2025s1.assignment2.model;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class Town {
    private static final Logger LOGGER = Logger.getLogger(Town.class.getName());
    private final String name;
    private int population;
    private int goodsStockpile;
    private int goodsTransportedToday;
    private final List<Railway> railways;

    public Town(String name, int population) {
        this.name = name;
        this.population = population;
        this.goodsStockpile = 0;
        this.goodsTransportedToday = 0;
        this.railways = new ArrayList<>();
    }

    public void produceGoods() {
        goodsStockpile += population;
        LOGGER.info(() -> String.format("Produced %d goods for %s, new stockpile=%d", population, name, goodsStockpile));
    }

    public void receiveGoods(int goods) {
        goodsStockpile += goods;
        LOGGER.info(() -> String.format("Received %d goods for %s, new stockpile=%d", goods, name, goodsStockpile));
    }

    public void addRailway(Railway railway) {
        railways.add(railway);
        LOGGER.info(() -> String.format("Added railway to %s: %s <-> %s", name, railway.getTownA().getName(), railway.getTownB().getName()));
    }

    public void setPopulation(int population) {
        this.population = population;
        LOGGER.info(() -> String.format("Set population=%d for %s", population, name));
    }

    public void setGoodsTransportedToday(int transported) {
        this.goodsTransportedToday = transported;
        LOGGER.info(() -> String.format("Set goodsTransportedToday=%d for %s", transported, name));
    }

    public String getName() {
        return name;
    }

    public int getPopulation() {
        return population;
    }

    public int getGoodsStockpile() {
        return goodsStockpile;
    }

    public int getGoodsTransportedToday() {
        return goodsTransportedToday;
    }

    public List<Railway> getRailways() {
        return railways;
    }

    public int getSingleTrackCount() {
        return (int) railways.stream()
                .filter(r -> r.getStatus().equals("Single-track completed"))
                .count();
    }

    public int getDualTrackCount() {
        return (int) railways.stream()
                .filter(r -> r.getStatus().equals("Dual-track completed"))
                .count();
    }
}
// REMINDER: Removed redundant transportGoods() method to eliminate duplication with Simulation.transportGoods(). Enhanced logging for state changes to support debugging (2025-05-26).