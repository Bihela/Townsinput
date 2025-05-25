package edu.curtin.oose2025s1.assignment2.util;

import edu.curtin.oose2025s1.assignment2.input.MessageProcessorImpl;
import edu.curtin.oose2025s1.assignment2.model.Simulation;
import edu.curtin.oose2025s1.assignment2.output.OutputManagerImpl;
import edu.curtin.oose2025s1.assignment2.TownsInput;

public class SimulationFactory {
    public Simulation createSimulation() {
        TownsInput townsInput = new TownsInput(12345L);
        MessageProcessorImpl messageProcessor = new MessageProcessorImpl(townsInput);
        OutputManagerImpl outputManager = new OutputManagerImpl();
        return new Simulation(messageProcessor, outputManager);
    }
}