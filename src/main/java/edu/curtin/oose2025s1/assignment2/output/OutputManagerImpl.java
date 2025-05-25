package edu.curtin.oose2025s1.assignment2.output;

import edu.curtin.oose2025s1.assignment2.patterns.Observer;

public class OutputManagerImpl implements OutputManager {
    private final Observer consoleObserver;
    private final Observer fileObserver;

    public OutputManagerImpl() {
        this.consoleObserver = new ConsoleOutputObserver();
        this.fileObserver = new FileOutputObserver();
    }

    @Override
    public Observer getConsoleObserver() {
        return consoleObserver;
    }

    @Override
    public Observer getFileObserver() {
        return fileObserver;
    }
}