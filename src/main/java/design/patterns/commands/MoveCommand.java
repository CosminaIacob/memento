package design.patterns.commands;

import design.patterns.editor.Editor;
import design.patterns.shapes.Shape;

public class MoveCommand implements Command {

    private Editor editor;

    private int startX;

    private int startY;

    private int endX;

    private int endY;

    public MoveCommand(Editor editor) {
        this.editor = editor;
    }

    @Override
    public String getName() {
        return "Move by X:" + (endX - startX) + " Y:" + (endY - startY);
    }

    public void start(int x, int y) {
        startX = x;
        startY = y;
        for (design.patterns.shapes.Shape child : editor.getShapes().getSelected()) {
            child.drag();
        }
    }

    public void move(int x, int y) {
        for (design.patterns.shapes.Shape child : editor.getShapes().getSelected()) {
            child.moveTo(x - startX, y - startY);
        }
    }

    public void stop(int x, int y) {
        endX = x;
        endY = y;
        for (design.patterns.shapes.Shape child : editor.getShapes().getSelected()) {
            child.drop();
        }
    }

    @Override
    public void execute() {
        for (Shape child : editor.getShapes().getSelected()) {
            child.moveBy(endX - startX, endY - startY);
        }
    }
}
