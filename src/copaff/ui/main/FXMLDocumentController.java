/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package copaff.ui.main;

import copaff.alert.AlertMaker;
import copaff.database.export.DatabaseExporter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.util.Duration;
import static copaff.ui.main.CopaFF.*;
import java.io.File;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.DirectoryChooser;

/**
 *
 * @author tavos
 */
public class FXMLDocumentController implements Initializable {

    private double transitionMilis = 400;
    @FXML
    private BorderPane mainBorderPane;

    @FXML
    private void handleRegisterAction(ActionEvent event) {
        try {
            StackPane registration = FXMLLoader.load(getClass().getResource("/copaff/ui/registration/Registration.fxml"));
            FadeTransition ft = new FadeTransition(Duration.millis(transitionMilis), registration);
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.play();
            mainBorderPane.setCenter(registration);
        } catch (IOException ex) {
            ex.printStackTrace();
            handleError(Alert.AlertType.ERROR, ex, "It was unable to load the Registration Form", ButtonType.OK);
        }

    }

    @FXML
    private void handleMatchAction(ActionEvent event) {
        try {
            ScrollPane table = FXMLLoader.load(getClass().getResource("/copaff/ui/game/match.fxml"));
            FadeTransition ft = new FadeTransition(Duration.millis(transitionMilis), table);
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.play();
            mainBorderPane.setCenter(table);
        } catch (IOException ex) {
            ex.printStackTrace();
            handleError(Alert.AlertType.ERROR, ex, "It was unable to load the Registration Form", ButtonType.OK);
        }

    }

    @FXML
    private void handleLinkAction(ActionEvent event) {
        try {
            StackPane link = FXMLLoader.load(getClass().getResource("/copaff/ui/link/FXMLLink.fxml"));
            FadeTransition ft = new FadeTransition(Duration.millis(transitionMilis), link);
            ft.setFromValue(0.1);
            ft.setToValue(1.0);
            ft.play();
            mainBorderPane.setCenter(link);
        } catch (IOException ex) {
            ex.printStackTrace();
            handleError(Alert.AlertType.ERROR, ex, "It was unable to load the Registration Form", ButtonType.OK);
        }

    }

    @FXML
    private void handleExportDBAction(ActionEvent event) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select Location to Create Backup");
        File selectedDirectory = directoryChooser.showDialog(stage);
        if (selectedDirectory == null) {
            AlertMaker.showErrorMessage("Export cancelled", "No Valid Directory Found");
        } else {
            DatabaseExporter databaseExporter = new DatabaseExporter(selectedDirectory);
            //progressSpinner.visibleProperty().bind(databaseExporter.runningProperty());
            new Thread(databaseExporter).start();
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

    }

    public static void handleError(Alert.AlertType alertType, Throwable ex, String message, ButtonType... button) {
        scene.getRoot().setEffect(new GaussianBlur(10));
        Alert alert = new Alert(alertType, message, button);
        alert.setHeaderText(ex.getClass().getName());
        alert.initOwner(stage);
        alert.getDialogPane().setStyle("-fx-background-color:#B2B2B2;");//-fx-primarytext
        alert.showAndWait();
        scene.getRoot().setEffect(null);
    }

}
