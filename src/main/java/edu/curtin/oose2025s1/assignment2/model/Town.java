package edu.curtin.oose2025s1.assignment2.model;

import java.util.ArrayList;
import java.util.List;

public class Town {
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
    }

    public void transportGoods() {
        goodsTransportedToday = 0;
        for (Railway railway : railways) {
            Town destination = railway.getTownA().equals(this) ? railway.getTownB() : railway.getTownA();
            int transported = railway.transportGoods(this, destination, goodsStockpile);
            goodsStockpile -= transported;
            goodsTransportedToday += transported;
        }
    }

    public void addRailway(Railway railway) {
        railways.add(railway);
    }

    public void setPopulation(int population) {
        this.population = population;
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
                            .filter(r -> r.getStatus().equals("Single-track completed") && this.equals(r.getTownA()))
                            .count();
    }

    public int getDualTrackCount() {
        return (int) railways.stream()
                            .filter(r -> r.getStatus().equals("Dual-track completed") && this.equals(r.getTownA()))
                            .count();
    }
}