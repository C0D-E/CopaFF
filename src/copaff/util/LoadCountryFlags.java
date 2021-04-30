/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package copaff.util;

import java.util.HashMap;
import java.util.Map;
import javafx.concurrent.Task;
import javafx.scene.image.Image;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class LoadCountryFlags extends Task<CountryFlagMap> {

    @Override
    protected CountryFlagMap call() throws Exception {
        LoadCountries loadCountriesTask = new LoadCountries();
        Thread th = new Thread(loadCountriesTask);
        th.setDaemon(true);
        th.start();
        CountriesFetcher.CountryList countries = loadCountriesTask.get();
        Map<String, Image> countriesFlags = new HashMap<>();
        countries.forEach((country) -> {
            countriesFlags.put(country.getName(), new Image(getClass().getResourceAsStream("/regionflags/" + country.getIso() + ".png")));
        });
        return new CountryFlagMap(countries, countriesFlags);
    }

}
