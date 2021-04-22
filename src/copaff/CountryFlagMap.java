/*
 *To change this license header, choose License Headers in Project Properties.
 *To change this template file, choose Tools | Templates
 *and open the template in the editor.
 */
package copaff;

import java.util.Map;
import javafx.scene.image.Image;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class CountryFlagMap {

    private CountriesFetcher.CountryList countryList;
    private Map<String, Image> flags;

    public CountryFlagMap(
            CountriesFetcher.CountryList countryList,
            Map<String, Image> flags) {
        this.countryList = countryList;
        this.flags = flags;
    }

    /**
     * @return the countryList
     */
    public CountriesFetcher.CountryList getCountryList() {
        return countryList;
    }

    /**
     * @param countryList the countryList to set
     */
    public void setCountryList(CountriesFetcher.CountryList countryList) {
        this.countryList = countryList;
    }

    /**
     * @return the flags
     */
    public Map<String, Image> getFlags() {
        return flags;
    }

    /**
     * @param flags the flags to set
     */
    public void setFlags(Map<String, Image> flags) {
        this.flags = flags;
    }

    
}
