/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package com.keyword.intlphonenumberinput;

import copaff.Country;
import static com.keyword.intlphonenumberinput.IntlPhoneNumberInputControl.flags;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class CountryButtonView extends ListCell<Country> {

    ImageView flagView;

    public CountryButtonView() {
        super();
        setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
        flagView = new ImageView(new Image(getClass().getResourceAsStream("/regionflags/EARTH.png")));
        flagView.setFitHeight(30);
        flagView.setFitWidth(40);
    }

    @Override
    protected void updateItem(Country country, boolean empty) {
        super.updateItem(country, empty);
        if (country == null || empty) {
//                ImageView flagView = new ImageView("/com/keyword/res/regionflags/EARTH.png");
//                flagView.setFitHeight(28);
//                flagView.setFitWidth(40);
            setGraphic(null);
        } else {
            setItem(country);
            flagView.setImage(flags.get(country.getName()));
            setGraphic(flagView);
        }
    }

}
