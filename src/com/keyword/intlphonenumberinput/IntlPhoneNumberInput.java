package com.keyword.intlphonenumberinput;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author TAVOS
 */
public class IntlPhoneNumberInput extends Application {

    @Override
    public void start(Stage stage) throws Exception {

        IntlPhoneNumberInputControl control = new IntlPhoneNumberInputControl();
        stage.setScene(new Scene(control));
        stage.setWidth(400);
        stage.setHeight(100);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
