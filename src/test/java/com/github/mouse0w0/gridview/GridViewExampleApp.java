package com.github.mouse0w0.gridview;

import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class GridViewExampleApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        GridView<String> gridView = new GridView<>();
        gridView.getItems().addAll("Hello", "World");
        gridView.setPrefSize(300, 300);

        gridView.getSelectionModel().getSelectedItems().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                System.out.println(gridView.getSelectionModel().getSelectedItems().get(0));
            }
        });

        primaryStage.setScene(new Scene(gridView));
        primaryStage.show();
    }
}
