package design.patterns.history;

import design.patterns.editor.Editor;

public record Memento(String backup, Editor editor) {

    public Memento(Editor editor) {
        this(editor.backup(), editor);
    }

    public void restore() {
        editor.restore(backup);
    }
}
