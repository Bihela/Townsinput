package edu.curtin.oose2025s1.assignment2.output;

import edu.curtin.oose2025s1.assignment2.model.SimulationState;
import edu.curtin.oose2025s1.assignment2.model.Town;
import edu.curtin.oose2025s1.assignment2.patterns.Observer;

public class ConsoleOutputObserver implements Observer {
    @Override
    public void update(SimulationState state) {
        System.out.println("---");
        System.out.println("Day " + state.getDay() + ":");
        state.getMessages().forEach(System.out::println);
        System.out.println();
        for (Town town : state.getTowns()) {
            System.out.printf("%s p:%d rs:%d rd:%d gs:%d gt:%d%n",
                town.getName(),
                town.getPopulation(),
                town.getSingleTrackCount(),
                town.getDualTrackCount(),
                town.getGoodsStockpile(),
                town.getGoodsTransportedToday());
        }
        System.out.println("---");
    }
}