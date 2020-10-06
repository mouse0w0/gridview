/**
 * Copyright (c) 2013, 2015, ControlsFX
 * All rights reserved.
 * <p>
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 * * Neither the name of ControlsFX, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL CONTROLSFX BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.github.mouse0w0.gridview.cell;

import com.github.mouse0w0.gridview.GridView;
import com.github.mouse0w0.gridview.skin.GridCellSkin;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.WeakInvalidationListener;
import javafx.beans.property.SimpleObjectProperty;
import javafx.scene.control.*;

/**
 * A GridCell is created to represent items in the {@link GridView}
 * {@link GridView#getItems() items list}. As with other JavaFX UI controls
 * (like {@link ListView}, {@link TableView}, etc), the {@link GridView} control
 * is virtualised, meaning it is exceedingly memory and CPU efficient. Refer to
 * the {@link GridView} class documentation for more details.
 *
 * @see GridView
 */
public class GridCell<T> extends IndexedCell<T> {

    /**************************************************************************
     *
     * Constructors
     *
     **************************************************************************/

    /**
     * Creates a default GridCell instance.
     */
    public GridCell() {
        getStyleClass().add("grid-cell"); //$NON-NLS-1$

//		itemProperty().addListener(new ChangeListener<T>() {
//            @Override public void changed(ObservableValue<? extends T> arg0, T oldItem, T newItem) {
//                updateItem(newItem, newItem == null);
//            }
//        });

        // TODO listen for index change and update index and item, rather than
        // listen to just item update as above. This requires the GridCell to
        // know about its containing GridRow (and the GridRow to know its
        // containing GridView)
        indexProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                final GridView<T> gridView = getGridView();
                if (gridView == null) return;

                if (getIndex() < 0) {
                    updateItem(null, true);
                    return;
                }
                T item = gridView.getItems().get(getIndex());

//                updateIndex(getIndex());
                updateItem(item, item == null);
            }
        });

        gridView.addListener((observable, oldValue, newValue) -> {
            if (oldValue != null) {
                oldValue.getSelectionModel().getSelectedIndices().removeListener(weakSelectionModelListener);
                oldValue.getFocusModel().focusedIndexProperty().removeListener(weakFocusModelListener);
            }
            if (newValue != null) {
                newValue.getSelectionModel().getSelectedIndices().addListener(weakSelectionModelListener);
                newValue.getFocusModel().focusedIndexProperty().addListener(weakFocusModelListener);
            }
            updateSelection();
            updateFocus();
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected Skin<?> createDefaultSkin() {
        return new GridCellSkin<>(this);
    }


    /**************************************************************************
     *
     * Properties
     *
     **************************************************************************/

    /**
     * The {@link GridView} that this GridCell exists within.
     */
    public SimpleObjectProperty<GridView<T>> gridViewProperty() {
        return gridView;
    }

    private final SimpleObjectProperty<GridView<T>> gridView =
            new SimpleObjectProperty<>(this, "gridView"); //$NON-NLS-1$

    /**
     * Sets the {@link GridView} that this GridCell exists within.
     */
    public final void updateGridView(GridView<T> gridView) {
        this.gridView.set(gridView);
    }

    /**
     * Returns the {@link GridView} that this GridCell exists within.
     */
    public GridView<T> getGridView() {
        return gridView.get();
    }

    /**************************************************************************
     *
     * Modified by Mouse0w0 (https://github.com/Mouse0w0)
     *
     **************************************************************************/

    private final InvalidationListener selectionModelListener = observable -> updateSelection();
    private final WeakInvalidationListener weakSelectionModelListener = new WeakInvalidationListener(selectionModelListener);

    private final InvalidationListener focusModelListener = observable -> updateFocus();
    private final WeakInvalidationListener weakFocusModelListener = new WeakInvalidationListener(focusModelListener);

    private void updateSelection() {
        GridView<T> gridView = getGridView();
        if (gridView == null) return;

        int index = getIndex();
        if (index == -1) return;

        MultipleSelectionModel<T> selectionModel = gridView.getSelectionModel();
        if (selectionModel == null) return;

        boolean selected = selectionModel.isSelected(index);
        if (selected == isSelected()) return;

        updateSelected(selected);
    }

    private void updateFocus() {
        GridView<T> gridView = getGridView();
        if (gridView == null) return;

        int index = getIndex();
        if (index == -1) return;

        FocusModel<T> focusModel = gridView.getFocusModel();
        if (focusModel == null) return;

        setFocused(focusModel.isFocused(index));
    }
}