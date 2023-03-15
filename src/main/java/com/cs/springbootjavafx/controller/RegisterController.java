package com.cs.springbootjavafx.controller;

import com.cs.springbootjavafx.entity.User;
import com.cs.springbootjavafx.repository.UserRepository;
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
public class RegisterController {

    @FXML
    TextField firstname;
    @FXML
    TextField lastname;
    @FXML
    TextField address;
    @FXML
    TextField zip;
    @FXML
    TextField city;
    @FXML
    TextField telephone;
    @FXML
    TextField email;
    @FXML
    TextField creditcard;
    @FXML
    TextField password1;
    @FXML
    TextField password2;


    private Stage stage;
    private final Helper helper;
    @Autowired
    UserRepository userRepository;

    public RegisterController(Helper helper
                             ) {
        this.helper = helper;
    }

    @FXML
    private void demoBtn(ActionEvent event) {
        //Load new content to existing window
        this.stage = helper.changeStage(this.getClass().getClassLoader().getResource("views/Test2.fxml"), event, this);
        this.stage.show();
    }

    public void showStage() {
        this.stage = helper.getStage(this.getClass().getClassLoader().getResource("views/Register.fxml"), "WindowName", this);
        this.stage.show();
    }

    @FXML
    private void register(ActionEvent event){
        try {
            if (!validate()) return;
            User user = new User(firstname.getText(), lastname.getText(), address.getText(), "", Integer.parseInt(zip.getText()), city.getText(), telephone.getText(), email.getText(), Long.parseLong(creditcard.getText()), Integer.parseInt(password1.getText()));
            userRepository.save(user);
            this.stage.close();
        } catch (Exception e){
            System.out.println("error");
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Register failed!");
        }
    }
    @FXML
    private void cancel(ActionEvent event){
        this.stage.close();
    }

    private boolean validate(){
        if (email.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your email");
            return false;
        }
        if (password1.getText().isEmpty() || password2.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your password");
            return false;
        }
        if (firstname.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your firstname");
            return false;
        }
        if (lastname.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your lastname");
            return false;
        }
        if (address.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your address");
            return false;
        }
        if (zip.getText().isEmpty() || !isNumeric(zip.getText())) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your zip and be aware it is an number");
            return false;
        }
        if (city.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your city");
            return false;
        }
        if (telephone.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your city");
            return false;
        }
        if (creditcard.getText().isEmpty() || !isNumeric(creditcard.getText())) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please enter your credit card number and be aware it is an number");
            return false;
        }
        if (!password1.getText().equals(password2.getText())) {
            showAlert(Alert.AlertType.ERROR, this.stage.getOwner(), "Form Error!", "Please repeat your password correct");
            return false;
        }
        return true;
    }


    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            double d = Double.parseDouble(strNum);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}
