package edu.curtin.oose2025s1.assignment2.output;

import edu.curtin.oose2025s1.assignment2.model.SimulationState;
import edu.curtin.oose2025s1.assignment2.model.Town;
import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.patterns.Observer;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Logger;

public class FileOutputObserver implements Observer {
    private static final Logger LOGGER = Logger.getLogger(FileOutputObserver.class.getName());

    @Override
    public void update(SimulationState state) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph Towns {\n");
        for (Town town : state.getTowns()) {
            sb.append("    ").append(town.getName()).append("\n");
        }
        for (Town town : state.getTowns()) {
            for (Railway railway : town.getRailways()) {
                Town townA = railway.getTownA();
                Town townB = railway.getTownB();
                if (townA.getName().compareTo(townB.getName()) < 0) {
                    String status = railway.getStatus();
                    String edge = switch (status) {
                        case "Single-track under construction" -> "[style=\"dashed\"]";
                        case "Single-track completed" -> "";
                        case "Dual-track under construction" -> "[style=\"dashed\",color=\"black:black\"]";
                        case "Dual-track completed" -> "[color=\"black:black\"]";
                        default -> "";
                    };
                    sb.append("    ").append(townA.getName())
                      .append(" -- ").append(townB.getName())
                      .append(" ").append(edge).append("\n");
                }
            }
        }
        sb.append("}\n");

        try {
            Files.writeString(Paths.get("simoutput.dot"), sb.toString());
        } catch (IOException e) {
            LOGGER.severe(() -> "Failed to write simoutput.dot: " + e.getMessage());
        }
    }
}