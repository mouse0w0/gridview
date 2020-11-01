package com.github.mouse0w0.gridview.skin.behavior;

import com.github.mouse0w0.gridview.GridView;
import com.sun.javafx.scene.control.behavior.BehaviorBase;
import javafx.scene.input.MouseEvent;

import java.util.Collections;

public class GridViewBehavior<T> extends BehaviorBase<GridView<T>> {

    public GridViewBehavior(GridView<T> control) {
        super(control, Collections.emptyList());
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (!getControl().isFocused() && getControl().isFocusTraversable()) {
            getControl().requestFocus();
        }
    }
}
