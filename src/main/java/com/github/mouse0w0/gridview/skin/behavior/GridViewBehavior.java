package com.github.mouse0w0.gridview.skin.behavior;

import com.github.mouse0w0.gridview.GridView;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import com.sun.javafx.scene.control.inputmap.InputMap;
import javafx.scene.input.MouseEvent;

public class GridViewBehavior<T> extends BehaviorBase<GridView<T>> {

    private final InputMap<GridView<T>> inputMap;

    public GridViewBehavior(GridView<T> control) {
        super(control);

        inputMap = createInputMap();

        addDefaultMapping(inputMap,
                new InputMap.MouseMapping(MouseEvent.MOUSE_PRESSED, this::mousePressed));
    }

    @Override
    public InputMap<GridView<T>> getInputMap() {
        return inputMap;
    }

    protected void mousePressed(MouseEvent e) {
        if (!getNode().isFocused() && getNode().isFocusTraversable()) {
            getNode().requestFocus();
        }
    }
}
