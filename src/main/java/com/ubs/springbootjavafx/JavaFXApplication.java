package com.ubs.springbootjavafx;

import com.ubs.springbootjavafx.controller.MainController;
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
        MainController mainController = applicationContext.getBean(MainController.class);
        mainController.showStage();
    }
    @Override
    public void stop() {
        applicationContext.close();
        Platform.exit();
    }
}
