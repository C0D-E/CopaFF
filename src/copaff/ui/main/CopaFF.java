/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.main;

import copaff.database.DatabaseHandler;
import copaff.exceptions.ExceptionUtil;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author tavos
 */
public class CopaFF extends Application {

    public static Stage stage;
    public static Scene scene;

    @Override
    public void start(Stage stage) throws Exception {
        CopaFF.stage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLDocument.fxml"));

        Scene scene = new Scene(root);
        CopaFF.scene = scene;

        stage.setScene(scene);
        stage.setTitle("Copa FF v1.0");
        stage.show();
        new Thread(() -> {
            ExceptionUtil.init();
            DatabaseHandler.getInstance();
        }).start();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

}
