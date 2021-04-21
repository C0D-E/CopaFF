/*
 * IntlPhoneNumberInputControl
 * 
 * v1.0
 *
 * 2019
 * 
 * Copyright (C) 2009 Gustavo A Salazar Lima
 */
package com.keyword.intlphonenumberinput;

import copaff.Country;
import copaff.CountriesFetcher;
import com.google.i18n.phonenumbers.AsYouTypeFormatter;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ReadOnlyObjectProperty;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

/**
 *
 * @author TAVOS
 */
public class IntlPhoneNumberInputControl extends HBox {

    private IntegerProperty countryCodeLabelForRegion;
    private StringProperty rawPhoneNumber;
    private IntegerProperty positionCaret;
    private BooleanProperty showFlagIcon;
    private BooleanProperty showFlagCombo;
    private BooleanProperty showCountryCode;

    public static Map<String, Image> flags;
    public static CountriesFetcher.CountryList countries;

    private PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
    private AsYouTypeFormatter formatter;

    @FXML
    private ImageView flagView;
    @FXML
    private JFXComboBox<Country> regionCode;
    @FXML
    private Label countryCodeLabel;
    @FXML
    private JFXTextField phoneNumber;

    public IntlPhoneNumberInputControl() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(
                "/fxml/IntlPhoneNumberInput.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        fxmlLoader.setClassLoader(getClass().getClassLoader());
        LoadCountryFlags loadCountryFlags = new LoadCountryFlags();
        Thread th = new Thread(loadCountryFlags);
        th.setDaemon(true);
        th.start();
        try {
            fxmlLoader.load();
            flags = loadCountryFlags.get().getFlags();
            countries = loadCountryFlags.get().getCountryList();
            init();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        } catch (ExecutionException ex) {
            ex.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public final void init() {
        countryCodeLabel.textProperty().bind(Bindings.concat("+", countryCodeForRegionProperty()));
        flagView.visibleProperty().bind(showFlagIconProperty());
        flagView.managedProperty().bind(showFlagIconProperty());
        regionCode.visibleProperty().bind(showFlagComboProperty());
        regionCode.managedProperty().bind(showFlagComboProperty());
        countryCodeLabel.visibleProperty().bind(showCountryCodeProperty());
        countryCodeLabel.managedProperty().bind(showCountryCodeProperty());

        countries.forEach((country) -> {
            regionCode.getItems().add(country);
        });
        regionCode.setCellFactory((ListView<Country> p) -> new CountryCellView());
        regionCode.setButtonCell(new CountryButtonView());
        regionCode.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            setFlagIcon(flags.get(newValue.getName()));
            setCountryCodeForRegion(newValue.getDialCode());
            formatter = phoneUtil.getAsYouTypeFormatter(newValue.getIso());
            if (!phoneNumber.getText().isEmpty()) {
                phoneNumber.setText(getFormattedPhoneNumber());
            }
        });

        phoneNumber.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
            String character = event.getText();
            if (event.getCode().equals(KeyCode.BACK_SPACE)) {
                event.consume();
                if (phoneNumber.getCaretPosition() <= 0) {
                    return;
                }
                setPositionCaret(phoneNumber.getCaretPosition() - 1);
                int pos = findPositionOfNumber(getFormattedPhoneNumber(), getPositionCaret());
                StringBuilder temp = new StringBuilder(getFormattedPhoneNumber());
                temp = temp.deleteCharAt(pos);
                //Update public raw Phone Number
                setRawPhoneNumber(extractRawPhoneNumber(temp.toString()));
                setPositionCaret(pos);
                phoneNumber.setText(getFormattedPhoneNumber());//Update formatted phone number
                phoneNumber.positionCaret(getPositionCaret());
            } else if (event.getCode().isDigitKey()) {
                event.consume();
                StringBuilder rawPhoneNum = new StringBuilder(getRawPhoneNumber());
                int tempPositionCaret = extractRawPhoneNumber(
                        getFormattedPhoneNumber().substring(0, phoneNumber.getCaretPosition()))
                        .length();
                rawPhoneNum.insert(tempPositionCaret, character);
                setRawPhoneNumber(rawPhoneNum.toString());//Update public raw Phone Number
                phoneNumber.setText(getFormattedPhoneNumber());//Update formatted phone number
                tempPositionCaret = positionCaretInFormattedPhoneNumber(
                        tempPositionCaret, getFormattedPhoneNumber());
                setPositionCaret(tempPositionCaret);
                phoneNumber.positionCaret(getPositionCaret());
            }
        });

        //This one is needed to avoid a double typed char in this control
        phoneNumber.addEventFilter(KeyEvent.KEY_TYPED, (event) -> {
            event.consume();
        });
        Country defaultCountry = countries.get(countries.indexOfIso(Country.DEFAULT_COUNTRY));
        regionCode.getSelectionModel().select(defaultCountry);
//        setNationalMode();
        requestFocus();
    }

    /**
     * *************************************************************************
     *                                                                         *
     * Properties * *
     * ************************************************************************
     */
    /**
     * showFlagIconProperty is used to show or hide the flag icon
     *
     * @return the property that holds the state
     */
    public BooleanProperty showFlagIconProperty() {
        if (showFlagIcon == null) {
            showFlagIcon = new SimpleBooleanProperty(this, "showFlagIcon", true);
        }
        return showFlagIcon;
    }

    /**
     * showFlagComboProperty is used to show or hide the combo box with flags
     *
     * @return the property that holds the state
     */
    public BooleanProperty showFlagComboProperty() {
        if (showFlagCombo == null) {
            showFlagCombo = new SimpleBooleanProperty(this, "showFlagCombo", true);
        }
        return showFlagCombo;
    }

    /**
     * showCountryCodeProperty is used to show or hide the country code label
     *
     * @return the property that holds the state
     */
    public BooleanProperty showCountryCodeProperty() {
        if (showCountryCode == null) {
            showCountryCode = new SimpleBooleanProperty(this, "showCountryCode", true);
        }
        return showCountryCode;
    }

    private ReadOnlyObjectWrapper<ImageView> flagIcon;

    public ImageView getFlagIcon() {
        return flagIconProperty().get();
    }

    public ReadOnlyObjectProperty<ImageView> flagIconProperty() {
        if (flagIcon == null) {
            flagIcon = new ReadOnlyObjectWrapper<>(this, "flagIcon");
            flagIcon.set(flagView);
        }
        return flagIcon.getReadOnlyProperty();
    }

    private ReadOnlyObjectWrapper<JFXComboBox<Country>> flagCombo;

    public JFXComboBox<Country> getFlagCombo() {
        return flagComboProperty().get();
    }

    public ReadOnlyObjectProperty<JFXComboBox<Country>> flagComboProperty() {
        if (flagCombo == null) {
            flagCombo = new ReadOnlyObjectWrapper<>(this, "flagCombo");
            flagCombo.set(regionCode);
        }
        return flagCombo.getReadOnlyProperty();
    }

    private ReadOnlyObjectWrapper<Label> countryCode;

    public Label getCountryCode() {
        return countryCodeProperty().get();
    }

    public ReadOnlyObjectProperty<Label> countryCodeProperty() {
        if (countryCode == null) {
            countryCode = new ReadOnlyObjectWrapper<>(this, "countryCode");
            countryCode.set(countryCodeLabel);
        }
        return countryCode.getReadOnlyProperty();
    }

    private ReadOnlyObjectWrapper<JFXTextField> editor;

    public JFXTextField getEditor() {
        return editorProperty().get();
    }

    public ReadOnlyObjectProperty<JFXTextField> editorProperty() {
        if (editor == null) {
            editor = new ReadOnlyObjectWrapper<>(this, "editor");
            editor.set(phoneNumber);
        }
        return editor.getReadOnlyProperty();
    }

    /**
     * Indicates whether this toggle button is selected. This can be manipulated
     * programmatically.
     *
     * @return
     */
    public IntegerProperty countryCodeForRegionProperty() {
        if (countryCodeLabelForRegion == null) {
            countryCodeLabelForRegion = new SimpleIntegerProperty(this, "countryCodeLabelForRegion", 0);
        }
        return countryCodeLabelForRegion;
    }

    public StringProperty rawPhoneNumberProperty() {
        if (rawPhoneNumber == null) {
            rawPhoneNumber = new SimpleStringProperty(this, "rawPhoneNumber", "");
        }
        return rawPhoneNumber;
    }

    public IntegerProperty positionCaretProperty() {
        if (positionCaret == null) {
            positionCaret = new SimpleIntegerProperty(this, "positionCaret", 0);
        }
        return positionCaret;
    }

    /**
     * *************************************************************************
     *
     * Methods
     *
     ***************************************************************************
     */
    public void setDefaultFlag() {
        setFlagIcon(new Image("/com/keyword/res/regionflags/EARTH.png"));
        showFlagIconOnly();
    }

    @Override
    public void requestFocus() {
        editorProperty().get().requestFocus();
    }

    public void showFlagIconOnly() {
        showFlagIcon(true);
        showFlagCombo(false);
        showCountryCode(false);
    }

    public void showFlagComboOnly() {
        showFlagIcon(false);
        showFlagCombo(true);
        showCountryCode(false);
    }

    public void showCountryCodeOnly() {
        showFlagIcon(false);
        showFlagCombo(false);
        showCountryCode(true);
    }

    public void setFlagIcon(Image image) {
        flagView.setImage(image);
    }

    public void setNationalMode() {
        showFlagIconOnly();
    }

    /**
     * Show the popup that displays the flag, country name and dial code of the
     * supported countries
     */
    public void show() {
        regionCode.show();
    }

    /**
     * Hide the popup that displays the flag, country name and dial code of the
     * supported countries
     */
    public void hide() {
        regionCode.hide();
    }

    public void setCountryCodeForRegion(int countryCodeForRegion) {
        countryCodeForRegionProperty().set(countryCodeForRegion);
    }

    public int getCountryCodeForRegion() {
        return countryCodeForRegionProperty().get();
    }

    /**
     * Set the phone number
     *
     * @param phoneNumber raw Phone Number (just the number without any format)
     */
    public void setRawPhoneNumber(String phoneNumber) {
        rawPhoneNumberProperty().set(phoneNumber);
    }

    public String getRawPhoneNumber() {
        return rawPhoneNumberProperty().get();
    }

    /**
     * Set the position of the Caret
     *
     * @param positionCaret Position of the caret in textfield
     */
    public void setPositionCaret(int positionCaret) {
        positionCaretProperty().set((positionCaret < 0) ? 0 : positionCaret);
    }

    public int getPositionCaret() {
        return positionCaretProperty().get();
    }

    public String getFormattedPhoneNumber() {//Check for formatter == null due to no prior selection made 
        formatter.clear();
        String formattedPhoneNumber = "";
        String rawPhoneNum = getRawPhoneNumber();
        int length = rawPhoneNum.length();
        for (int i = 0; i < length; i++) {
            formattedPhoneNumber = formatter.inputDigit(rawPhoneNum.charAt(i));
        }
        return formattedPhoneNumber;
    }

    /**
     * Show the Flag icon next to the country code
     *
     * @param showFlagIcon true to show it, false to hide it
     */
    public void showFlagIcon(boolean showFlagIcon) {
        showFlagIconProperty().set(showFlagIcon);
    }

    public boolean isShowFlagIcon() {
        return showFlagIconProperty().get();
    }

    /**
     * Show the Flag next to the country code
     *
     * @param showFlagCombo true to show it, false to hide it
     */
    public void showFlagCombo(boolean showFlagCombo) {
        showFlagComboProperty().set(showFlagCombo);
    }

    public boolean isFlagComboShowing() {
        return showFlagComboProperty().get();
    }

    public void showCountryCode(boolean showCountryCode) {
        showCountryCodeProperty().set(showCountryCode);
    }

    public boolean isCountryCodeShowing() {
        return showCountryCodeProperty().get();
    }

    private String extractRawPhoneNumber(String formattedPhoneNumber) {
        StringBuilder temp = new StringBuilder();
        int length = formattedPhoneNumber.length();
        for (int i = 0; i < length; i++) {//Remove all characters but numbers
            if ("0123456789".contains(String.valueOf(formattedPhoneNumber.charAt(i)))) {
                temp.append(formattedPhoneNumber.charAt(i));
            }
        }
        return temp.toString();
    }

    private int findPositionOfNumber(String formattedPhoneNumber, int currentPosition) {
        if (currentPosition < 0 || formattedPhoneNumber.isEmpty()) {
            return 0;
        }
        if (formattedPhoneNumber.length() <= currentPosition) {
            return findPositionOfNumber(formattedPhoneNumber, currentPosition - 1);
        } else {
            if ("0123456789".contains(String.valueOf(formattedPhoneNumber.charAt(currentPosition)))) {
                return currentPosition;
            } else {
                return findPositionOfNumber(formattedPhoneNumber, currentPosition - 1);
            }
        }
    }

    private int positionCaretInFormattedPhoneNumber(int positionCaret, String formattedPhoneNumber) {
        int i = 0;
        int length = formattedPhoneNumber.length();
        String rawPhoneNum = extractRawPhoneNumber(formattedPhoneNumber);
        for (int j = 0; i < length; i++) {
            char c = formattedPhoneNumber.charAt(i);
            if ("0123456789".contains(String.valueOf(c))) {
                if (rawPhoneNum.charAt(j) == c) {
                    if (positionCaret == j) {
                        break;
                    }
                    j++;
                }
            }
        }
        return i + 1;
    }

}
