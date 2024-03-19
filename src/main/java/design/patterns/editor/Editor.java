package design.patterns.editor;

import design.patterns.commands.Command;
import design.patterns.history.History;
import design.patterns.history.Memento;
import design.patterns.shapes.CompoundShape;
import design.patterns.shapes.Shape;

import javax.swing.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;

// Originator
public class Editor extends JComponent {

    private CustomCanvas canvas;

    private CompoundShape allShapes = new CompoundShape();

    private History history;

    public Editor() {
        canvas = new CustomCanvas(this);
        history = new History();
    }

    public void loadShapes(Shape... shapes) {
        allShapes.clear();
        allShapes.add(shapes);
        canvas.refresh();
    }

    public CompoundShape getShapes() {
        return allShapes;
    }

    public void execute(Command command) {
        history.push(command, new Memento(this));
        command.execute();
    }

    public void undo() {
        if (history.undo()) {
            canvas.repaint();
        }
    }

    public void redo() {
        if (history.redo()) {
            canvas.repaint();
        }
    }

    public String backup() {
//        try{
//            ByteArrayOutputStream baos=new ByteArrayOutputStream();
//            ObjectOutputStream oos=new ObjectOutputStream(baos);
//            oos.writeObject(this.allShapes);
//            oos.close();
//            return Base64.getEncoder().encodeToString(baos.toByteArray());
//        }catch (IOException e){
//            return "";
//        }

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream(); ObjectOutputStream oos = new ObjectOutputStream(baos)) {
            oos.writeObject(this.allShapes);
            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch (IOException e) {
            return "";
        }
    }

    public void restore(String state) {
//        try{
//            byte[] data=Base64.getDecoder().decode(state);
//            ObjectInputStream ois=new ObjectInputStream(new ByteArrayInputStream(data));
//            this.allShapes=(CompoundShape) ois.readObject();
//            ois.close();
//        }catch (ClassNotFoundException e){
//            System.out.println("ClassNotFoundException occurred.");
//        }catch (IOException e){
//            System.out.println("IOException occurred.");
//        }

        byte[] data = Base64.getDecoder().decode(state);
        try (ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(data))) {
            this.allShapes = (CompoundShape) ois.readObject();
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException occurred.");
        } catch (IOException e) {
            System.out.println("IOException occurred.");
        }
    }
}
