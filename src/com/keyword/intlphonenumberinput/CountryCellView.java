/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package com.keyword.intlphonenumberinput;

import copaff.Country;
import static com.keyword.intlphonenumberinput.IntlPhoneNumberInputControl.flags;
import javafx.geometry.Pos;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import static javafx.scene.layout.Region.USE_COMPUTED_SIZE;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class CountryCellView extends ListCell<Country> {

    private final Label dialCode;
    private final Label countryName;
    private final ImageView flagView;
    private final HBox hbox;

    public CountryCellView() {
        super();
        setPrefWidth(USE_COMPUTED_SIZE);
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        dialCode = new Label();
        dialCode.setStyle("-fx-text-fill: gray;");
        HBox.setHgrow(dialCode, Priority.ALWAYS);
        dialCode.setMaxWidth(Double.MAX_VALUE);
        dialCode.setAlignment(Pos.CENTER_LEFT);//Possible variable to be changed by dev and also work with the name of the country

        flagView = new ImageView();
        flagView.setFitHeight(30);
        flagView.setFitWidth(40);

        hbox = new HBox();
        hbox.setSpacing(5);
        hbox.setAlignment(Pos.CENTER_LEFT);
        countryName = new Label();
        hbox.getChildren().addAll(flagView, countryName, dialCode);

    }

    @Override
    protected void updateItem(Country country, boolean empty) {
        super.updateItem(country, empty);
        if (country == null || empty) {
            setGraphic(null);
        } else {
            //System.out.println(country.getName());
            countryName.setText(country.getName());
            dialCode.setText("+" + country.getDialCode());
            flagView.setImage(flags.get(country.getName()));
            setGraphic(hbox);
        }
    }
}
