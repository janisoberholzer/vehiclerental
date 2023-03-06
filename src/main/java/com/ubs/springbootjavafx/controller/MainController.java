package com.ubs.springbootjavafx.controller;


import com.ubs.springbootjavafx.util.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.springframework.stereotype.Component;

@Component
public class MainController {


    //Default variables do not change
    private Stage stage;
    private final Helper helper;

    public MainController(Helper helper) {
        this.helper = helper;
    }

    //Custom variables
    @FXML
    private TextField demoField;


    //Button action methods
    @FXML
    private void demoBtn(ActionEvent event) {
        //Load new content to existing window
        this.stage = helper.changeStage(this.getClass().getClassLoader().getResource("views/Test2.fxml"), event, this);
        this.stage.show();
    }


    //Show new window of this scene
    public void showStage() {
        this.stage = helper.getStage(this.getClass().getClassLoader().getResource("views/Test1.fxml"), "WindowName", this);
        this.stage.show();
    }
}
