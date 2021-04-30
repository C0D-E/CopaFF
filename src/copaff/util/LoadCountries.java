package copaff.util;

import copaff.util.CountriesFetcher;
import javafx.concurrent.Task;

/**
 *
 * @author Gustavo A Salazar Lima
 */
public class LoadCountries extends Task<CountriesFetcher.CountryList> {

    @Override
    protected CountriesFetcher.CountryList call() throws Exception {
        return CountriesFetcher.getCountries();
    }
}
