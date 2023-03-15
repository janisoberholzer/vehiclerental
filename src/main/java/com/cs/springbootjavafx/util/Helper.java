package com.cs.springbootjavafx.util;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;


@Service
public class Helper {
    public static FXMLLoader loader;

    public static Stage getStage(URL url, String title, Object controller) {
        Stage stage = new Stage();
        loader = new FXMLLoader(url);
        loader.setController(controller);
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        stage.setTitle(title);
        stage.setResizable(false);
        return stage;
    }

    public static Stage changeStage(URL url, ActionEvent event, Object controller) {
        loader = new FXMLLoader(url);
        loader.setController(controller);
        Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setScene(scene);
        return stage;
    }
}
