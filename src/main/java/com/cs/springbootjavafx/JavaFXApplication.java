package com.cs.springbootjavafx;

import com.cs.springbootjavafx.controller.LoginController;
import com.cs.springbootjavafx.controller.MainController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Service;

@Service
public class JavaFXApplication extends Application {

    private ConfigurableApplicationContext applicationContext;
    
    @Override
    public void init() {
        applicationContext = new SpringApplicationBuilder(SpringBootJavaFxApplication.class).run();
    }
    @Override
    public void start(Stage stage) {
        LoginController loginController = applicationContext.getBean(LoginController.class);
        loginController.showStage();
    }
    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
