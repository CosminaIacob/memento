package design.patterns.history;


import design.patterns.commands.Command;

import java.util.ArrayList;
import java.util.List;

// Caretaker
public class History {

    private List<Pair> history = new ArrayList<>();

    private int virtualSize = 0;

    private class Pair {

        Command command;

        Memento memento;

        Pair(Command command, Memento memento) {
            this.command = command;
            this.memento = memento;
        }

        private Command getCommand() {
            return command;
        }

        private Memento getMemento() {
            return memento;
        }
    }

    public void push(Command command, Memento memento) {
        if (virtualSize != history.size() && virtualSize > 0) {
            history = history.subList(0, virtualSize - 1);
        }

        history.add(new Pair(command, memento));
        virtualSize = history.size();
    }

    public boolean undo() {
        Pair pair = getUndo();
        if (pair == null) {
            return false;
        }

        System.out.println("Undoing: " + pair.getCommand().getName());
        pair.getMemento().restore();
        return true;
    }

    public boolean redo() {
        Pair pair = getRedo();
        if (pair == null) {
            return false;
        }

        System.out.println("Redoing: " + pair.getCommand().getName());
        pair.getMemento().restore();
        pair.getCommand().execute();
        return true;
    }

    private Pair getUndo() {
        if (virtualSize == 0) {
            return null;
        }

        virtualSize = Math.max(0, virtualSize - 1);
        return history.get(virtualSize);
    }

    private Pair getRedo() {
        if (virtualSize == history.size()) {
            return null;
        }

        virtualSize = Math.min(history.size(), virtualSize + 1);
        return history.get(virtualSize - 1);
    }
}
