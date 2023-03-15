package com.cs.springbootjavafx.controller;

import com.cs.springbootjavafx.entity.User;
import com.cs.springbootjavafx.repository.*;
import com.cs.springbootjavafx.util.Helper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;



@Component
public class LoginController {

    private Stage stage;
    private final Helper helper;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    CategoryRepository categoryRepository;
    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    RentalRepository rentalRepository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    MainController mainController;
    @Autowired
    RegisterController registerController;

    @FXML
    private TextField email;
    @FXML
    private TextField password;

    public LoginController(Helper helper) {
        this.helper = helper;
    }

    @FXML
    private void login(ActionEvent event){
        try {
            if (!validate()) return;
            User user = userRepository.findUserByEmailAndPassword(email.getText(), password.getText());
            if (user == null) throw new Exception();
            mainController.user = user;
            this.stage.close();
            mainController.showStage();
            //this.stage = helper.changeStage(this.getClass().getClassLoader().getResource("views/Main.fxml"), event, mainController);
            //this.stage.show();

        }catch (Exception e){
            System.out.println("error");
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Login failed!");
        }
    }

    @FXML
    private void demoBtn(ActionEvent event) {
        //Load new content to existing window
        this.stage = helper.changeStage(this.getClass().getClassLoader().getResource("views/Test2.fxml"), event, this);
        this.stage.show();
    }

    public void showStage() {
        this.stage = helper.getStage(this.getClass().getClassLoader().getResource("views/Login.fxml"), "WindowName", this);
        this.stage.show();
    }



    @FXML
    private void register(ActionEvent event){
        registerController.showStage();
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }
    private boolean validate(){
        if (email.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your email");
            return false;
        }
        if (password.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your password");
            return false;
        }
        return true;
    }
}
