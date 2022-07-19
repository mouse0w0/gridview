package com.github.mouse0w0.gridview.skin.behavior;

import com.github.mouse0w0.gridview.GridView;
import com.github.mouse0w0.gridview.cell.GridCell;
import com.sun.javafx.scene.control.behavior.CellBehaviorBase;
import javafx.scene.control.FocusModel;
import javafx.scene.control.MultipleSelectionModel;

public class GridCellBehavior<T> extends CellBehaviorBase<GridCell<T>> {
    public GridCellBehavior(GridCell<T> control) {
        super(control);
    }

    @Override
    protected GridView<T> getCellContainer() {
        return getNode().getGridView();
    }

    @Override
    protected MultipleSelectionModel<?> getSelectionModel() {
        return getCellContainer().getSelectionModel();
    }

    @Override
    protected FocusModel<?> getFocusModel() {
        return getCellContainer().getFocusModel();
    }

    @Override
    protected void edit(GridCell<T> cell) {
    }
}
