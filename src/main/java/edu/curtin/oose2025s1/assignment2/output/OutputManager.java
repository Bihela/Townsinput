package edu.curtin.oose2025s1.assignment2.output;

import edu.curtin.oose2025s1.assignment2.patterns.Observer;

public interface OutputManager {
    Observer getConsoleObserver();
    Observer getFileObserver();
}