package agh.ics.oop.project.gui;

import agh.ics.oop.project.presenter.SettingsPresenter;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;

public class SettingsApp extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getClassLoader().getResource("settings.fxml"));
        VBox viewRoot = loader.load();
        SettingsPresenter presenter = loader.getController();
        configureStage(primaryStage,viewRoot);
        primaryStage.show();
    }
    private void configureStage(Stage primaryStage, VBox viewRoot) {
        var scene = new Scene(viewRoot);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Settings app");
        primaryStage.maxWidthProperty().bind(viewRoot.maxWidthProperty());
        primaryStage.maxHeightProperty().bind(viewRoot.maxHeightProperty());
    }
}
