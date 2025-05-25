package edu.curtin.oose2025s1.assignment2.input;

import edu.curtin.oose2025s1.assignment2.model.Simulation;
import edu.curtin.oose2025s1.assignment2.model.Town;
import edu.curtin.oose2025s1.assignment2.model.Railway;
import edu.curtin.oose2025s1.assignment2.patterns.UnderConstructionDual;
import edu.curtin.oose2025s1.assignment2.TownsInput;
import java.util.logging.Logger;

public class MessageProcessorImpl implements MessageProcessor {
    private static final Logger LOGGER = Logger.getLogger(MessageProcessorImpl.class.getName());
    private final TownsInput townsInput;

    public MessageProcessorImpl(TownsInput townsInput) {
        this.townsInput = townsInput;
    }

    @Override
    public void processMessages(Simulation simulation) {
        while (true) {
            final String message = townsInput.nextMessage();
            if (message == null) {
                break;
            }
            try {
                LOGGER.info(() -> "Processing message: " + message);
                processMessage(message, simulation);
                simulation.addMessage(message);
            } catch (InvalidMessageException e) {
                LOGGER.warning(() -> "Invalid message: " + message + ", error: " + e.getMessage());
            }
        }
    }

    private void processMessage(String message, Simulation simulation) throws InvalidMessageException {
        String[] parts = message.split(" ");
        if (parts.length != 3) {
            throw new InvalidMessageException("Message must have 3 parts");
        }
        String type = parts[0];
        String arg1 = parts[1];
        String arg2 = parts[2];

        switch (type) {
            case "town-founding":
                int population;
                try {
                    population = Integer.parseInt(arg2);
                    if (population <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    InvalidMessageException ex = new InvalidMessageException("Invalid population: " + arg2);
                    ex.initCause(e);
                    throw ex;
                }
                if (!arg1.matches("[a-zA-Z_]+")) {
                    throw new InvalidMessageException("Invalid town name: " + arg1);
                }
                simulation.addTown(arg1, population);
                break;
            case "town-population":
                try {
                    population = Integer.parseInt(arg2);
                    if (population <= 0) {
                        throw new NumberFormatException();
                    }
                } catch (NumberFormatException e) {
                    InvalidMessageException ex = new InvalidMessageException("Invalid population: " + arg2);
                    ex.initCause(e);
                    throw ex;
                }
                if (!arg1.matches("[a-zA-Z_]+")) {
                    throw new InvalidMessageException("Invalid town name: " + arg1);
                }
                simulation.updatePopulation(arg1, population);
                break;
            case "railway-construction":
                if (!arg1.matches("[a-zA-Z_]+") || !arg2.matches("[a-zA-Z_]+")) {
                    throw new InvalidMessageException("Invalid town names: " + arg1 + ", " + arg2);
                }
                simulation.addRailway(arg1, arg2);
                break;
            case "railway-duplication":
                if (!arg1.matches("[a-zA-Z_]+") || !arg2.matches("[a-zA-Z_]+")) {
                    throw new InvalidMessageException("Invalid town names: " + arg1 + ", " + arg2);
                }
                Town townA = simulation.getTowns().stream()
                                      .filter(t -> t.getName().equals(arg1))
                                      .findFirst()
                                      .orElse(null);
                Town townB = simulation.getTowns().stream()
                                      .filter(t -> t.getName().equals(arg2))
                                      .findFirst()
                                      .orElse(null);
                if (townA != null && townB != null) {
                    Railway railway = townA.getRailways().stream()
                                          .filter(r -> r.getTownA().equals(townB) || r.getTownB().equals(townB))
                                          .findFirst()
                                          .orElse(null);
                    if (railway != null && railway.getStatus().equals("Single-track completed")) {
                        railway.setState(new UnderConstructionDual(simulation.getDay()));
                    } else {
                        throw new InvalidMessageException("No single-track railway exists between " + arg1 + " and " + arg2);
                    }
                } else {
                    throw new InvalidMessageException("Invalid towns: " + arg1 + ", " + arg2);
                }
                break;
            default:
                throw new InvalidMessageException("Unknown message type: " + type);
        }
    }
}